package com.member_management.controller;

import com.member_management.service.DeviceService;
import com.member_management.service.UsageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsageInformationController {

    private final UsageInformationService usageInformationService;
    private final DeviceService deviceService; // Add DeviceService

    @Autowired
    public UsageInformationController(UsageInformationService usageInformationService, DeviceService deviceService) {
        this.usageInformationService = usageInformationService;
        this.deviceService = deviceService;
    }

    @GetMapping("/booking-device")
    public String getAllUsageInformation(Model model) {
        List<Object[]> devices = usageInformationService.getAvailableDevicesSortedByMaTB();
        model.addAttribute("devices", devices);
        return "booking-device";
    }

    @PostMapping("/book/{deviceId}")
    public String bookDevice(@PathVariable String deviceId, RedirectAttributes redirectAttributes) {
        try {
            usageInformationService.bookDevice(deviceId);
            redirectAttributes.addFlashAttribute("successMessage", "Đặt chỗ thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/booking-device";
    }

}
