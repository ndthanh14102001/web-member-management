package com.member_management.service;

import com.member_management.repository.DeviceRepository;
import com.member_management.repository.MemberRepository;
import com.member_management.repository.ProcessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {

    private final ProcessingRepository processingRepository;

    @Autowired
    public ProcessingService(ProcessingRepository processingRepository) {
        this.processingRepository = processingRepository;
    }
    public List<_Processing> findAllProcessing() {
        List<_Processing> usageInformations = processingRepository.findAllProcessing();
        return usageInformations;
    }

}
