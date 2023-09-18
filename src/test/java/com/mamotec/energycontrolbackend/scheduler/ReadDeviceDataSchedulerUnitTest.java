package com.mamotec.energycontrolbackend.scheduler;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.cron.ReadDeviceDataScheduler;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadDeviceDataSchedulerUnitTest {

    @InjectMocks
    private ReadDeviceDataScheduler sut;

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
    void shouldVerifyNoInteractionWhenNoConfigIsFound() {
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
