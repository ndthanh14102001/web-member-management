package com.member_management.task;

import com.member_management.service.UsageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final UsageInformationService usageInformationService;

    @Autowired
    public ScheduledTasks(UsageInformationService usageInformationService) {
        this.usageInformationService = usageInformationService;
    }

    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    public void deleteExpiredRecords() {
        usageInformationService.deleteUnusedRecordsAfterOneHour();
    }
}
