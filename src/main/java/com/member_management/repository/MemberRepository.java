package com.member_management.repository;

import com.member_management.modules._Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<_Member, Integer> {

    @Modifying
    @Transactional
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
    public _Member Signin(@Param("maTV") String id, @Param("password") String password);

    @Query("SELECT m FROM _Member m WHERE m.maTV = :maTV")
    public _Member findByMaTV(@Param("maTV") String id);

    @Query("SELECT m FROM _Member m WHERE m.maTV = :maTV and m.email = :email")
    public _Member findByMaTVAndEmail(@Param("maTV") String maTV, @Param("email") String email);
}
