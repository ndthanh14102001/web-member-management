package com.member_management.controller;

import com.member_management.service.DeviceService;
import com.member_management.service.UsageInformationService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getAllUsageInformation(Model model, @RequestParam(value = "search", required = false) String search) {
        List<Object[]> devices = usageInformationService.getAvailableDevicesSortedByMaTB(search);
        model.addAttribute("devices", devices);
        return "booking-device";
    }

    @PostMapping("/book/{deviceId}")
    public String bookDevice(@PathVariable String deviceId, @RequestParam("bookingTime") String bookingTime, RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(bookingTime);
            usageInformationService.bookDevice(deviceId, localDateTime);
            redirectAttributes.addFlashAttribute("successMessage", "Đặt chỗ thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/booking-device";
    }

}
