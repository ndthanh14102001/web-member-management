package com.member_management.service;

import com.member_management.modules._Device;
import com.member_management.modules._Member;
import com.member_management.modules._UsageInformation;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.member_management.repository.UsageInformationRepository;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<Object[]> getAvailableDevicesSortedByMaTB(String tenTB, String tGDatChoISOFormat) {

        List<Object[]> devices = new ArrayList<>();
        if (tGDatChoISOFormat != null) {
            Date tGDatCho = convertISOStringToDate(tGDatChoISOFormat);

            if (tenTB == null || "".equals(tenTB)) {
                devices = usageInformationRepository.getAvailableDevices(tGDatCho);
            } else {
                devices = usageInformationRepository.getAvailableDevicesByTenTB(tenTB, tGDatCho);
            }
        }
        return devices.stream()
                .sorted(Comparator.comparing(device -> (String) device[0]))
                .collect(Collectors.toList());
    }

    private Date convertISOStringToDate(String isoString) {
        LocalDateTime localDateTime = LocalDateTime.parse(isoString);
        Date tGDatCho = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return tGDatCho;
    }

    public void bookDevice(String memberId, String deviceId, String bookingTime) throws Exception {
        LocalDateTime localDateTime = LocalDateTime.parse(bookingTime);
        Date tGDatCho = convertISOStringToDate(bookingTime);
        if (isAvailableDevice(deviceId, tGDatCho)) {
            _Device device = new _Device(deviceId);
            _Member member = new _Member(memberId);

            _UsageInformation usageInformation = new _UsageInformation();
            usageInformation.setMaTV(member);
            usageInformation.setMaTB(device);
            usageInformation.setTGDatCho(Timestamp.valueOf(localDateTime));
            usageInformationRepository.save(usageInformation);
        } else {
            throw new Exception(deviceId + " đã được đặt chỗ hoặc đã được mượn");
        }
    }

    private boolean isAvailableDevice(String deviceId, Date tGDatCho) {
        List<_UsageInformation> busyInformation = usageInformationRepository.getBusyInformationByMaTB(deviceId, tGDatCho);
        return busyInformation.isEmpty();
    }

    public void deleteUnusedRecordsAfterOneHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<_UsageInformation> unusedRecords = usageInformationRepository.findUnusedRecordsBeforeTime(oneHourAgo);
        usageInformationRepository.deleteAll(unusedRecords);
    }

    public List<_UsageInformation> getBorrowingDevices(String maTV) {
        return usageInformationRepository.getNotAvailableDevicesByMaTV(maTV);
    }

    public List<_UsageInformation> getBookedDevicesByMaTV(String maTV) {
        return usageInformationRepository.getBookedDevicesByMaTV(maTV);
    }
    
     public void cancelBookedDevice(int maTT) {
         usageInformationRepository.cancelBookedDevice(maTT);
    }
}
