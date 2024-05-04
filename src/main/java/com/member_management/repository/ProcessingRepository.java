package com.member_management.repository;

import com.member_management.modules._Processing;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessingRepository extends JpaRepository<_Processing, Integer> {

    @Query("SELECT p FROM _Processing p JOIN p.maTV m ORDER BY p.ngayXL DESC")
    List<_Processing> findAllProcessing();

    @Query("SELECT COALESCE(SUM(p.soTien), 0) FROM _Processing p")
    Double getTotalAmount();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO _Processing (HinhThucXL, SoTien, NgayXL, TrangThaiXL, MaTV) "
            + "VALUES (:hinhThucXL, :soTien, :ngayXL, :trangThaiXL, :maTV)",
            nativeQuery = true)
    void addProcessing(@Param("hinhThucXL") String hinhThucXL,
                       @Param("soTien") Integer soTien,
                       @Param("ngayXL") Date ngayXL,
                       @Param("trangThaiXL") Integer trangThaiXL,
                       @Param("maTV") String maTV);
    @Modifying
    @Query("UPDATE _Processing SET soTien = :soTien, trangThaiXL = :trangThaiXL WHERE maXL = :maXL")
    void updateProcessing(@Param("soTien") double soTien, @Param("trangThaiXL") int trangThaiXL, @Param("maXL") String maXL);

    @Modifying
    @Query("DELETE FROM _Processing WHERE maXL = :maXL")
    void deleteByMaXL(@Param("maXL") String maXL);
    
}
