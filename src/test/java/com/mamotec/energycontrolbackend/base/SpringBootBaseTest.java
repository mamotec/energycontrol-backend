package com.mamotec.energycontrolbackend.base;

import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SpringBootBaseTest {

    @MockBean
    private InfluxClient influxClient;

    @MockBean
    private NodeRedClient nodeRedClient;

    @Autowired
    public DeviceGroupRepository deviceGroupRepository;
    @Autowired
    public InterfaceConfigRepository interfaceConfigRepository;
    @Autowired
    public DeviceRepository deviceRepository;

    @BeforeEach
    void setup() {
        delete();
    }

    void delete() {
        deviceRepository.deleteAllInBatch();
        deviceGroupRepository.deleteAllInBatch();
        interfaceConfigRepository.deleteAllInBatch();
    }
}
