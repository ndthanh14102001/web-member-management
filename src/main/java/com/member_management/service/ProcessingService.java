package com.member_management.service;

import com.member_management.modules._Processing;
import com.member_management.repository.ProcessingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {

    private final ProcessingRepository processingRepository;

    @Autowired
    public ProcessingService(ProcessingRepository processingRepository) {
        this.processingRepository = processingRepository;
    }
    public List<_Processing> findAllProcessing(String maTV) {
        List<_Processing> usageInformations = processingRepository.findProcessingByMaTV(maTV);
        return usageInformations;
    }
}
