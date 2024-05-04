package com.member_management.repository;

import com.member_management.modules._UsageInformation;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UsageInformationRepository extends JpaRepository<_UsageInformation, Integer> {

    @Query("""
            SELECT u.maTB.maTB,u.maTB.tenTB FROM _UsageInformation u
            WHERE u.maTB NOT IN (
                SELECT u.maTB FROM _UsageInformation u
                WHERE u.tGVao IS NULL AND u.tGMuon IS NOT NULL AND u.tGTra IS NULL
            )
            AND u.maTB NOT IN (
                SELECT u.maTB FROM _UsageInformation u
                WHERE u.tGDatCho IS NOT NULL 
                AND YEAR(u.tGDatCho) = YEAR(:tGDatCho) 
                AND MONTH(u.tGDatCho) = MONTH(:tGDatCho) 
                AND DAY(u.tGDatCho) = DAY(:tGDatCho)
            )
            GROUP BY u.maTB
            UNION
            SELECT t.maTB, t.tenTB
            FROM _Device t
            WHERE t.maTB NOT IN (
                SELECT u.maTB.maTB FROM _UsageInformation u 
                WHERE u.maTB.maTB IS NOT NULL
            )""")
    List<Object[]> getAvailableDevices(@Param("tGDatCho") Date tGDatCho);

    @Query("""
            SELECT u.maTB.maTB,u.maTB.tenTB FROM _UsageInformation u
            WHERE u.maTB NOT IN (
                SELECT u.maTB FROM _UsageInformation u
                WHERE u.tGVao IS NULL AND u.tGMuon IS NOT NULL AND u.tGTra IS NULL
            )
            AND u.maTB NOT IN (
                SELECT u.maTB FROM _UsageInformation u
                WHERE u.tGDatCho IS NOT NULL
                AND YEAR(u.tGDatCho) = YEAR(:tGDatCho) 
                AND MONTH(u.tGDatCho) = MONTH(:tGDatCho) 
                AND DAY(u.tGDatCho) = DAY(:tGDatCho)
            )
            AND u.maTB.tenTB like %:tenTB%
            GROUP BY u.maTB, u.maTB.tenTB
            UNION
            SELECT d.maTB, d.tenTB
            FROM _Device d
            WHERE d.maTB NOT IN (
                SELECT u.maTB.maTB FROM _UsageInformation u 
                WHERE u.maTB.maTB IS NOT NULL
            )
            AND d.tenTB like %:tenTB%
           """)
    List<Object[]> getAvailableDevicesByTenTB(@Param("tenTB") String tenTB, @Param("tGDatCho") Date tGDatCho);

    @Query("""
           SELECT u FROM _UsageInformation u
           WHERE u.tGDatCho IS NOT NULL 
           AND YEAR(u.tGDatCho) = YEAR(:tGDatCho) 
           AND MONTH(u.tGDatCho) = MONTH(:tGDatCho) 
           AND DAY(u.tGDatCho) = DAY(:tGDatCho)
           AND u.tGMuon IS NULL 
           AND u.tGTra IS NULL
           AND u.maTB.maTB = :maTB
           UNION
           SELECT u FROM _UsageInformation u
           WHERE u.tGMuon IS NOT NULL 
           AND u.tGMuon IS NOT NULL
           AND u.tGTra IS NULL
           AND u.maTB.maTB = :maTB
           """)
    List<_UsageInformation> getBusyInformationByMaTB(@Param("maTB") String maTB, @Param("tGDatCho") Date tGDatCho);
    
    @Query("""
    SELECT u
    FROM _UsageInformation u
    WHERE u.tGVao IS NULL 
           AND u.tGMuon IS NOT NULL 
           AND u.tGTra IS NULL 
           AND u.maTV.maTV = :maTV
    """)
    List<_UsageInformation> getNotAvailableDevicesByMaTV(@Param("maTV") String maTV);
    
    @Query("""
    SELECT u
    FROM _UsageInformation u
    WHERE u.tGVao IS NULL 
           AND u.tGMuon IS NULL 
           AND u.tGTra IS NULL
           AND u.tGDatCho IS NOT NULL
           AND u.maTV.maTV = :maTV
    ORDER BY u.tGDatCho DESC
    """)
    List<_UsageInformation> getBookedDevicesByMaTV(@Param("maTV") String maTV);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM _UsageInformation u WHERE u.maTT = :maTT")
    void cancelBookedDevice(@Param("maTT") int maTT);

    @Query("SELECT u FROM _UsageInformation u WHERE u.tGDatCho <= :time AND u.tGMuon IS NULL")
    List<_UsageInformation> findUnusedRecordsBeforeTime(@Param("time") LocalDateTime time);
}
