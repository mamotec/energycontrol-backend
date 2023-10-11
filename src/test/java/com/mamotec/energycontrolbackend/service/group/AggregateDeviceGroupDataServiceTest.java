package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.factory.DeviceGroupFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AggregateDeviceGroupDataServiceTest extends SpringBootBaseTest {

    @Autowired
    private AggregateDeviceGroupDataService aggregateDeviceGroupDataService;

    @Nested
    class Home {
        @Test
        void shouldNotThrowExceptionWhenHomeDeviceGroupHasNoDevices() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aHomeDeviceGroup(deviceGroupRepository);

            // when
            HomeDataRepresentation aggregate = (HomeDataRepresentation) aggregateDeviceGroupDataService.aggregate(deviceGroup.getId());

            // then
            assertNotNull(aggregate);
            assertEquals(1, aggregate.getPeakKilowatt());
        }
    }


}