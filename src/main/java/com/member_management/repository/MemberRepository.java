package com.member_management.repository;

import com.member_management.modules._Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<_Member, Integer> {
    @Query("INSERT INTO _Member (maTV, hoTen, email, khoa, nganh, sdt, password) VALUES (:id, :name, :email, :department, :branch, :phone, :password)")
    void insertMember(@Param("id") String id,
                      @Param("name") String name,
                      @Param("email") String email,
                      @Param("department") String depart,
                      @Param("branch") String branch,
                      @Param("phone") String phone,
                      @Param("password") String password
                      );
    @Query("SELECT m FROM _Member m WHERE m.maTV = :maTV AND m.password = :password")
    public _Memeber Signin(@Param("maTV") String id, @Param("password") String password);
}
