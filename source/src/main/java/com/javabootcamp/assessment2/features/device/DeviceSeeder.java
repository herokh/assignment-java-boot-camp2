package com.javabootcamp.assessment2.features.device;

import com.javabootcamp.assessment2.entities.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceSeeder {

    public static final String NAME = "D1";

    @Autowired
    private DeviceRepository deviceRepository;

    public void createMocks() {
        var device = new Device();
        device.setName(NAME);
        deviceRepository.save(device);
    }
}
