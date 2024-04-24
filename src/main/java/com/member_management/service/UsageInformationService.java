package com.member_management.service;

import com.member_management.modules._Device;
import com.member_management.modules._Member;
import com.member_management.modules._UsageInformation;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.member_management.repository.UsageInformationRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageInformationService {

    private final UsageInformationRepository usageInformationRepository;

    @Autowired
    public UsageInformationService(UsageInformationRepository usageInformationRepository) {
        this.usageInformationRepository = usageInformationRepository;
    }

    public List<Object[]> getAvailableDevicesSortedByMaTB() {

        List<Object[]> devices = usageInformationRepository.getAvailableDevices();
        return devices.stream()
                .sorted(Comparator.comparing(device -> (String) device[0]))
                .collect(Collectors.toList());
    }

    public void bookDevice(String deviceId) throws Exception {
        if (isAvailableDevice(deviceId)) {
            _Device device = new _Device(deviceId);
            _Member member = new _Member("1120480015");

            _UsageInformation usageInformation = new _UsageInformation();
            usageInformation.setMaTV(member);
            usageInformation.setMaTB(device);
            usageInformation.setTGDatCho(new Date());
            usageInformationRepository.save(usageInformation);
        } else {
            throw new Exception(deviceId + " đã được đặt chỗ hoặc đã được mượn");
        }
    }

    private boolean isAvailableDevice(String deviceId) {
        List<_UsageInformation> busyInformation = usageInformationRepository.getBusyInformationByMaTB(deviceId);
        return busyInformation.isEmpty();
    }
    
    public void deleteUnusedRecordsAfterOneHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<_UsageInformation> unusedRecords = usageInformationRepository.findUnusedRecordsBeforeTime(oneHourAgo);
        usageInformationRepository.deleteAll(unusedRecords);
    }
}
