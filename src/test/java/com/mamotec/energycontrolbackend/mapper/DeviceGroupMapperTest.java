package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupCreate;
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
            PlantDeviceGroupCreate create = new PlantDeviceGroupCreate();
            create.setName("PV - Rechte Seiete");
            create.setDirectMarketing(true);

            // when
            com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup group = (com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup) mapper.map(create);

            // then
            assertEquals("PV - Rechte Seiete", group.getName());
            assertTrue(group.isDirectMarketing());
        }
    }

}