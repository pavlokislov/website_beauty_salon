package com.website.spring.service.repositories;

import com.website.spring.service.models.Record;
import com.website.spring.service.models.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO record(local_date_time) VALUES(?1)")
    void insertStartIntoRecordTime(String time);


    @Query(nativeQuery = true, value = "SELECT id FROM record ORDER BY id DESC LIMIT 1")
    Long getLastID();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO record(local_date_time, master_id, service_id, user_id) VALUES (?1, ?2, ?3, ?4)")
    void insertNewRecord(String time, Long masterId, Long serviceId, Long customerUSerId);


    @Query(nativeQuery = true, value = "UPDATE master_schedule t1 JOIN record t2\n" +
            "    ON t1.user_id = ?1 AND t2.master_id = ?1\n" +
            "SET t1.service_status = ?2,\n" +
            "    t2.service_status = ?2\n" +
            "WHERE t1.local_date_time=?3\n" +
            "AND t2.local_date_time=?3")
    void updateBothStatusByMasterId(Long masterId, ServiceStatus status, String newTime);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "\n" +
            "UPDATE record r\n" +
            "SET service_status=?1\n" +
            "WHERE local_date_time = ?2\n" +
            "AND r.master_id = ?3")
    void updateRecordStatusMasterId(String newStatus, String time, Long masterId);


    @Query(nativeQuery = true, value = "SELECT  * FROM record")
    List<Record> findAll();

    Record getRecordById(Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE record SET local_date_time = ?1 WHERE id =?2")
    void updateTimeByIdRecord(String newTime, Long id);

}
