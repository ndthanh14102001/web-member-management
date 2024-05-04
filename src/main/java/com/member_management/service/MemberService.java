package com.member_management.service;

import com.member_management.modules._Member;
import com.member_management.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public void Register(_Member m) {
        memberRepository.insertMember(m.getMaTV(), m.getHoTen(), m.getEmail(), m.getKhoa(), m.getNganh(), m.getSdt(), m.getPassword());
    }

    public _Member Login(String id, String password){
        return memberRepository.Signin(id, password);
    }
    public void changePassword(String id, String newPassword) {
        _Member member = memberRepository.findById(id).orElse(null); 

        if (member != null) {
            member.setPassword(newPassword);
            memberRepository.save(member);
        } else {
            throw new RuntimeException("Không tìm thấy thành viên với mã " + id);
        }
    
    }

}
