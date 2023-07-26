package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DeviceControllerTest extends BaseControllerTest {

    @MockBean
    private DeviceService deviceService;

    @Test
    public void shouldCreateDevice() throws Exception {
        // given
        final DeviceCreateRequest request = DeviceCreateRequest.builder()
                .unitId(1)
                .build();

        final DeviceCreateResponse response = DeviceCreateResponse.builder()
                .unitId(1)
                .build();

        when(deviceService.create(any(Device.class))).thenReturn(response);

        // then
        mockMvc.perform(post("/device").content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

}