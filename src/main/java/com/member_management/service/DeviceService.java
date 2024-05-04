package com.member_management.service;

import com.member_management.modules._Device;
import com.member_management.repository.DeviceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    public _Device findById(String id) {
        return deviceRepository.findByMaTB(id);
    }
    public List<_Device> findAllDevices() {
        List<_Device> devices = deviceRepository.getAllDevices();
        return devices;
    }
}
