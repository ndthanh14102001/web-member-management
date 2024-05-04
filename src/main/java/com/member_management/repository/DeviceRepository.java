package com.member_management.repository;

import com.member_management.modules._Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeviceRepository extends JpaRepository<_Device, Integer> {

    /**
     *
     * @param maTB
     * @return
     */
     @Query("FROM _Device d")
     List<_Device> getAllDevices();
     @Query("SELECT d FROM _Device d WHERE d.maTB = :maTB")
    _Device findByMaTB(@Param("maTB") String maTB);
}
