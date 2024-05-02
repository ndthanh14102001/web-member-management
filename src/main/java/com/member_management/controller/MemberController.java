package com.member_management.controller;

import com.member_management.service.DeviceService;
import com.member_management.service.UsageInformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signin")
    public String Signin(@RequestParam("Id") String id,
                             @RequestParam("Password") String password,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        if(memberService.Login(id, password) != null){
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công");
            return "redirect:/home";
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "Tài khoản không đúng");
            return "redirect:/signin";
        }
    }

    @PostMapping("/signup")
    public String Signup(@RequestParam("Id") String id,
                             @RequestParam("Name") String name,
                             @RequestParam("Email") String email,
                             @RequestParam("Department") String department,
                             @RequestParam("Branch") String branch,
                             @RequestParam("Phone") String phone,
                             @RequestParam("Password") String password,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        try {
            _Member m = new _Menber();
            m.setMaTV(id);
            m.setHoTen(name);
            m.setEmail(email);
            m.setKhoa(department);
            m.setNganh(branch);
            m.setSdt(phone);
            m.setPassword(password)
            memberService.Register(m);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công");
            return "redirect:/signin";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/signup";
        }
    }
}