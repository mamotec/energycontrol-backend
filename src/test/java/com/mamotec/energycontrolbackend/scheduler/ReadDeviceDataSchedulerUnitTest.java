package com.mamotec.energycontrolbackend.scheduler;

import com.mamotec.energycontrolbackend.cron.ReadDeviceDataScheduler;
import com.mamotec.energycontrolbackend.service.device.DeviceDataReadService;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
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
    private PlantDeviceService deviceService;
    @Mock
    private DeviceDataReadService deviceDataReadService;

    @Test
    void shouldVerifyNoInteractionWhenNoConfigIsFound() {
        // given
        when(interfaceConfigService.findAll()).thenReturn(List.of());

        // when
        sut.fetchDeviceData();

        // then
        verifyNoInteractions(interfaceService);
        verifyNoInteractions(deviceService);
        verifyNoInteractions(deviceDataReadService);
    }

}
