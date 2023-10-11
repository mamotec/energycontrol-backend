package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DeviceGroupMapperImpl.class})
class DeviceGroupMapperTest {

    @Autowired
    private DeviceGroupMapper mapper;

    @Nested
    class HomeDeviceGroup {

        @Test
        void createToGroup() {
            // given
            HomeDeviceGroupCreate create = new HomeDeviceGroupCreate();
            create.setName("PV - Rechte Seiete");
            create.setPeakKilowatt(1);

            // when
            com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup group = (com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup) mapper.map(create);

            // then
            Assertions.assertEquals("PV - Rechte Seiete", group.getName());
            Assertions.assertEquals(1, group.getPeakKilowatt());
        }
    }

    @Nested
    class PlantDeviceGroup {

        @Nested
        class Create {
            @Test
            void createToGroup() {
                // given
                com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupCreate create = new PlantDeviceGroupCreate();
                create.setName("PV - Rechte Seiete");
                create.setPeakKilowatt(1);

                // when
                com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup group = (com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup) mapper.map(create);

                // then
                Assertions.assertEquals("PV - Rechte Seiete", group.getName());
                Assertions.assertEquals(1, group.getPeakKilowatt());
            }
        }

        @Nested
        class Update {
            @Test
            void updateToGroup() {
                // given
                com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupUpdate update = new com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupUpdate();
                update.setName("PV - Rechte Seiete Updated");
                update.setDirectMarketing(true);
                update.setId(1L);

                // when
                com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup group = (com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup) mapper.map(update);

                // then
                Assertions.assertEquals("PV - Rechte Seiete Updated", group.getName());
                Assertions.assertTrue(group.isDirectMarketing());
            }
        }



    }


}