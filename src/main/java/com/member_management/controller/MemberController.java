package com.member_management.controller;

import com.member_management.service.MemberService;
import com.member_management.modules._Member;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/signin")
    public String Signin() {
        return "/signin";
    }

    @PostMapping("/signin")
    public String Signin(@RequestParam("maTV") String id,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        if (memberService.Login(id, password) != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công");
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Tài khoản không đúng");
            return "redirect:/signin";
        }
    }

    @GetMapping("/signup")
    public String Signup() {
        return "/signup";
    }

    @PostMapping("/signup")
    public String Signup(@RequestParam("maTV") String id,
            @RequestParam("hoten") String name,
            @RequestParam("email") String email,
            @RequestParam("khoa") String department,
            @RequestParam("nganh") String branch,
            @RequestParam("sdt") String phone,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            _Member m = new _Member();
            m.setMaTV(id);
            m.setHoTen(name);
            m.setEmail(email);
            m.setKhoa(department);
            m.setNganh(branch);
            m.setSdt(phone);
            m.setPassword(password);
            memberService.Register(m);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công");
            return "redirect:/signin";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("maTV", id);
            redirectAttributes.addFlashAttribute("hoten", name);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("khoa", department);
            redirectAttributes.addFlashAttribute("nganh", branch);
            redirectAttributes.addFlashAttribute("sdt", phone);
            redirectAttributes.addFlashAttribute("password", password);
            return "redirect:/signup";
        }
    }
}
