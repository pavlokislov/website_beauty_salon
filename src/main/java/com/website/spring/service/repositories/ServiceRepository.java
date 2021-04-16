package com.website.spring.service.repositories;

import com.website.spring.service.models.Service;
import com.website.spring.service.models.User;
import com.website.spring.service.time.LocalDateTimeAttributeConverter;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAll();

    @Query(nativeQuery = true, value = "SELECT name FROM service")
    List<String> findAllName();

    @Query(nativeQuery = true, value = "SELECT name FROM service WHERE id = ?1")
    String findNameByID(Long id);

    @Query(nativeQuery = true, value = "SELECT price FROM service WHERE id =?1")
    Integer findPriceByServiceId(Long id);


//    @Query(nativeQuery = true, value = "DELIMITER //\n" +
//            "CREATE PROCEDURE insert_into_twoTables(recordId BIGINT, time TIMESTAMP\n" +
//            ",masterId BIGINT\n" +
//            ",serviceId BIGINT\n" +
//            ")\n" +
//            "    BEGIN\n" +
//            "INSERT INTO record(local_date_time) VALUES(time);\n" +
//            "INSERT INTO records_masters(record_id, master_id) VALUES(recordId,masterId);\n" +
//            "INSERT INTO records_servises(record_id,service_id) VALUES(recordid,serviceId);\n" +
//            "\n" +
//            "END\n" +
//            "      //\n" +
//            "DELIMITER ;")
    //  void insertNewRecord(Long serviceId, Long masterId, Long userId, LocalDateTime time);

    @Query(nativeQuery = true, value = "SELECT duration_in_minutes\n" +
            "FROM service\n" +
            "         INNER JOIN record r on service.id = r.service_id\n" +
            "WHERE r.local_date_time = ?1 \n" +
            "  AND r.master_id = ?2")
    Integer findDurationServiceByMasterIdAndTime(String time, Long maserID);


}
