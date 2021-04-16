package com.website.spring.service.repositories;

import com.website.spring.service.models.MasterSchedule;
import com.website.spring.service.models.Record;
import com.website.spring.service.models.ServiceStatus;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface MasterScheduleRepository extends JpaRepository<MasterSchedule, Long> {

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM master_schedule\n" +
            "WHERE user_id = ?1 \n" +
            "  AND local_date_time > now()\n" +
            "ORDER BY local_date_time ASC \n" +
            "LIMIT 22")
    List<MasterSchedule> findAllByMasterId(Long masterId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE master_schedule SET service_status='COMPLETED' WHERE id = ?1 ")
    void updateServiceStatusByID(Long id);


    @Query(nativeQuery = true, value = "\n" +
            "SELECT local_date_time FROM master_schedule WHERE user_id=?1 AND service_status='NONE' AND local_date_time>NOW()\n" +
            "\n" +
            "ORDER BY local_date_time ASC LIMIT 10")
    List<LocalDateTime> findAvailableTime(Long idMaster);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE master_schedule SET service_status='OPEN' where local_date_time =?1 AND user_id=?2")
    void updateMasterSchedule(String time, Long masterId);

    @Query(nativeQuery = true, value = "SELECT service_status FROM master_schedule WHERE id =?1")
    ServiceStatus findServiceStatusById(Long masterId);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE master_schedule t1 JOIN record t2\n" +
            "    ON t1.user_id = ?1 AND t2.master_id = ?1\n" +
            "SET t1.service_status = ?2,\n" +
            "   t2.service_status = ?2\n" +
            "WHERE t1.local_date_time=?3\n" +
            "AND t2.local_date_time=?3")
    void updateBothStatusByMasterId(Long masterId, ServiceStatus status, String time);


    @Query(nativeQuery = true, value = "SELECT user_id FROM master_schedule WHERE id=?1")
    Long getMasterId(Long id);

    @Query(nativeQuery = true, value = "SELECT  local_date_time FROM  master_schedule WHERE id=?1")
    String getTime(Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE master_schedule m \n" +
            "SET service_status=?1 " +
            "WHERE local_date_time = ?2 \n" +
            "AND m.user_id = ?3")
    void updateStatusByMasterId(String newStatus, String time, Long masterId);


    @Query(nativeQuery = true, value = "SELECT * FROM  master_schedule WHERE user_id=?1 \n" +
            "AND service_status=?2 AND local_date_time>NOW()\n" +
            "ORDER BY local_date_time ASC LIMIT 10")
    List<MasterSchedule> getMasterScheduleBySMasterId(Long masterId, String status);

    @Query(nativeQuery = true, value = "SELECT * from master_schedule")
    ArrayList<MasterSchedule> findAll();

    @Query(nativeQuery = true, value = "SELECT service_status FROM master_schedule\n" +
            "WHERE local_date_time=?1\n" +
            "AND user_id=?2")
    ServiceStatus findStatusByTimeAndMasterId(LocalDateTime time, Long masterId);




}
