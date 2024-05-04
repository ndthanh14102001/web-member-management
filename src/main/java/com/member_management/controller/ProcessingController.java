package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.modules._Processing;
import com.member_management.service.MemberService;
import com.member_management.service.ProcessingService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ProcessingController {

    private final ProcessingService processingRepository;
    @Autowired
    private MemberService memberService;

    @Autowired
    public ProcessingController(ProcessingService usageInformationService) {
        this.processingRepository = usageInformationService;
    }

    @GetMapping("/processing")
    public String getAllUsageInformation(Model model) {
        List<_Processing> processingList = processingRepository.findAllProcessing();
        List<_Member> maTVWithProcessingList = memberService.findAllUsageInformation();

        model.addAttribute("processingList", processingList);
        model.addAttribute("maTVWithProcessingList", maTVWithProcessingList);
        Double totalAmount = processingRepository.calculateTotalAmount();
        model.addAttribute("totalAmount", totalAmount != null ? totalAmount : 0.0);
        return "processing";
    }
}
