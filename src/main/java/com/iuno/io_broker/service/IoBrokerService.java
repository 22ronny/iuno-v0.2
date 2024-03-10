package com.iuno.io_broker.service;

import com.iuno.io_broker.entity.Switch2;
import org.springframework.stereotype.Service;

@Service
public class IoBrokerService {

    private final IoBrokerApiClient apiClient;


    public IoBrokerService(IoBrokerApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void setBrightness(Switch2 switch2) {
        if (switch2.isBothClick()) {
            apiClient.bulb(50);
        }
        if (switch2.isLeftClick()) {
            apiClient.bulb(10);
        }
        if (switch2.isRightClick()) {
            apiClient.bulb(100);
        }
    }
}
