package com.member_management.controller;

import com.member_management.service.MemberService;
import com.member_management.modules._Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ChangePassword{
@PostMapping("/changepassword")
public String changePassword(@RequestParam("maTV") String id,
        @RequestParam("currentPassword") String currentPassword,
        @RequestParam("newPassword") String newPassword,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request) {
    HttpSession session = request.getSession();
    _Member loggedInMember = (_Member) session.getAttribute("loggedInMember");
    
    if (loggedInMember != null && loggedInMember.getMaTV().equals(id)) {
        if (loggedInMember.getPassword().equals(currentPassword)) {
            loggedInMember.setPassword(newPassword);
            memberService.updateMember(loggedInMember);
            
            redirectAttributes.addFlashAttribute("successMessage", "Mật khẩu đã được thay đổi thành công!");
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu hiện tại không đúng!");
            return "redirect:/changepassword";
        }
    } else {
        redirectAttributes.addFlashAttribute("errorMessage", "Bạn không được phép thay đổi mật khẩu cho người dùng này!");
        return "redirect:/home";
    }
}
}
