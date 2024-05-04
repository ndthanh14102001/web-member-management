package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.modules._Processing;
import com.member_management.service.MemberService;
import com.member_management.service.ProcessingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProcessingController {

    private final ProcessingService processingRepository;
    @Autowired
    private MemberService memberService;

    @Autowired
    public ProcessingController(ProcessingService usageInformationService) {
        this.processingRepository = usageInformationService;
    }

    @GetMapping("/processing-status")
    public String getAllUsageInformation(Model model) {
        List<_Processing> processingList = processingRepository.findAllProcessing();


        model.addAttribute("processingList", processingList);

        return "processing-status";
    }
}
