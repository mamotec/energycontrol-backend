package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDeviceGroupCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = {DeviceGroupMapperImpl.class})
class DeviceGroupMapperTest {

    @Autowired
    private DeviceGroupMapper mapper;

    @Nested
    class PlantDeviceGroup {

        @Test
        void createToDeviceGroup() {
            // given
            EnergyDeviceGroupCreate create = new EnergyDeviceGroupCreate();
            create.setName("PV - Rechte Seiete");
            create.setPeakKilowatt(1);
            create.setType(DeviceGroupType.PLANT);

            // when
            com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup group = (com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup) mapper.map(create);

            // then
            Assertions.assertEquals("PV - Rechte Seiete", group.getName());
            Assertions.assertEquals(1, group.getPeakKilowatt());
        }
    }

}