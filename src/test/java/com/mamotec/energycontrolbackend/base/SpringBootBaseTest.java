package com.mamotec.energycontrolbackend.base;

import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.client.NodeRedClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SpringBootBaseTest {

    @MockBean
    private InfluxClient influxClient;

    @MockBean
    private NodeRedClient nodeRedClient;
}
