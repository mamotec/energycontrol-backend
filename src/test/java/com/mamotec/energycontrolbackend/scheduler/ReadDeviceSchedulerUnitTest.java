package com.mamotec.energycontrolbackend.scheduler;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.cron.ReadDeviceScheduler;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.*;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadDeviceSchedulerUnitTest {

    @InjectMocks
    private ReadDeviceScheduler sut;

    @Mock
    private InterfaceConfigService interfaceConfigService;
    @Mock
    private InterfaceService interfaceService;
    @Mock
    private DeviceService deviceService;
    @Mock
    private NodeRedClient nodeRedClient;
    @Mock
    private DeviceDataService deviceDataService;

    @Test
    void shouldVerifyInteractionWhenEveryThingIsGiven() throws IOException, InterruptedException {
        // given
        InterfaceConfig config = new InterfaceConfig();
        config.setProtocolID(1L);
        config.setId(1L);
        RegisterMapping mapping = RegisterMapping.builder()
                .register(List.of(672, 673))
                .unit(Unit.KW)
                .fc(FunctionCode.READ_COILS)
                .type("power")
                .build();

        Interface i = new Interface();
        i.setMapping(new InterfaceMapping());
        i.getMapping()
                .setPower(mapping);
        Device device = new Device();

        when(interfaceConfigService.findAll()).thenReturn(List.of(config));
        when(interfaceService.getInterfaceByProtocolId(1)).thenReturn(i);
        when(deviceService.getDevicesForInterfaceConfig(1)).thenReturn(List.of(device));
        when(nodeRedClient.fetchDeviceData(i, config, device, mapping)).thenReturn("[343, 3434]");

        // when
        sut.fetchDeviceData();

        // then
        verify(interfaceService).getInterfaceByProtocolId(1L);
        verify(deviceService).getDevicesForInterfaceConfig(1L);
        verify(nodeRedClient).fetchDeviceData(i, config, device, mapping);
        verify(deviceDataService).writeDeviceData(device, "[343, 3434]", mapping);
    }

    @Test
    void shouldVerifyNoInteractionWhenNoConfigIsFound() throws IOException, InterruptedException {
        // given
        when(interfaceConfigService.findAll()).thenReturn(List.of());

        // when
        sut.fetchDeviceData();

        // then
        verifyNoInteractions(interfaceService);
        verifyNoInteractions(deviceService);
        verifyNoInteractions(nodeRedClient);
        verifyNoInteractions(deviceDataService);
    }
}
