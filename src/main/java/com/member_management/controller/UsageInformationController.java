package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.service.DeviceService;
import com.member_management.service.UsageInformationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
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
    public String getAllUsageInformation(Model model, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "bookingTime", required = false) String bookingTime) {
        List<Object[]> devices = null;
        if (bookingTime != null) {
            devices = usageInformationService.getAvailableDevicesSortedByMaTB(search, bookingTime);
        }
        model.addAttribute("devices", devices);
        model.addAttribute("bookingTime", bookingTime);
        return "booking-device";
    }

    @PostMapping("/book")
    public String bookDevice(@RequestParam("deviceId") String deviceId,
            @RequestParam("bookingTime") String bookingTime,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            // Lấy session từ request
            HttpSession session = request.getSession();

            // Lấy thông tin member từ session
            _Member loggedInMember = (_Member) session.getAttribute("loggedInMember");
            usageInformationService.bookDevice(loggedInMember.getMaTV(), deviceId, bookingTime);
            redirectAttributes.addFlashAttribute("successMessage", "Đặt chỗ thành công!");
            return "redirect:/booking-device";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/booking-device?bookingTime=" + bookingTime;
        }
    }

}
