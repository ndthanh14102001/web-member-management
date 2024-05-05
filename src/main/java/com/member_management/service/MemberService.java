package com.member_management.service;

import com.member_management.modules._Member;
import com.member_management.repository.MemberRepository;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private EmailService emailService;

    @Autowired
    public MemberService(MemberRepository memberRepository, EmailService emailService) {
        this.memberRepository = memberRepository;
        this.emailService = emailService;
    }

    public void Register(_Member m) throws Exception {
        m.checkMaTVFormat();
        if (memberRepository.findByMaTV(m.getMaTV()) != null) {
            throw new Exception("Mã thành viên đã tồn tại");
        }
        memberRepository.insertMember(m.getMaTV(), m.getHoTen(), m.getEmail(), m.getKhoa(), m.getNganh(), m.getSdt(), m.getPassword());
    }

    public _Member Login(String id, String password) {
        return memberRepository.Signin(id, password);
    }

    public void changePassword(String id, String newPassword) {
        _Member member = memberRepository.findByMaTV(id);

        if (member != null) {
            member.setPassword(newPassword);
            memberRepository.save(member);
        } else {
            throw new RuntimeException("Không tìm thấy thành viên với mã " + id);
        }

    }

    public List<_Member> findAllUsageInformation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void updateMember(_Member loggedInMember) {
        memberRepository.save(loggedInMember);
    }

    public int sendOTP(String maTV, String email) throws Exception {
        _Member member = memberRepository.findByMaTVAndEmail(maTV, email);
        if (member != null) {
            int otp = randomFiveDigitNumber();
            emailService.sendSimpleMessage(email, "Reset Password", String.valueOf(otp));
            return otp;
        }
        throw new Exception("Mã thành viên hoặc email không đúng");
    }

    private int randomFiveDigitNumber() {
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }
}
