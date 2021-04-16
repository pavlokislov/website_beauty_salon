package com.website.spring.service.repositories;

import com.website.spring.service.models.Role;
import com.website.spring.service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByFirstName(String firstName);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByEmail(String email);


    @Query(nativeQuery = true, value = "SELECT first_name FROM user WHERE role = ?1; ")
    Optional<List<String>> findAllFirstNameByRole(String role);

    Optional<List<User>> findAllByRole(Role role);

    @Query(nativeQuery = true, value = "SELECT u.first_name , m.rating, s.name, s.price\n" +
            "FROM user u\n" +
            "INNER JOIN  master m on u.id = m.user_id\n" +
            "INNER JOIN service s on m.service_id = s.id")
    List<Object[]> getMastersAndTheirService();


    @Query(nativeQuery = true, value = "SELECT m.id,  u.first_name FROM user u\n" +
            "INNER JOIN master m on u.id = m.user_id\n" +
            "INNER JOIN service s on m.service_id = s.id\n" +
            "WHERE s.id= ?1")
    List<Object[]> findMasterIdByServiceId(Long id);


    @Query(nativeQuery = true, value = "SELECT first_name FROM user\n" +
            "INNER JOIN master m on user.id = m.user_id\n" +
            "WHERE  m.id = ?1")
    String findFirstNameByMasterId(Long masterId);

    @Query(nativeQuery = true, value = "SELECT m.id FROM master m\n" +
            "INNER JOIN user u on m.user_id = u.id\n" +
            "WHERE u.first_name = ?1 \n" +
            "AND  u.last_name = ?2 \n")
    Long findMasterIdByFirstNamAndFirstName(String firstName, String lastName);

    @Query(nativeQuery = true, value = "\n" +
            "SELECT id from user\n" +
            "WHERE first_name = ?1 \n" +
            "      AND last_name= ?2")
    Long findUserIdByFirstNameAndLastName(String firstNAme, String LastName);


    @Query(nativeQuery = true, value = "SELECT user_id FROM master WHERE id=?1")
    Long findUserIdByMasterId(Long masterId);

}
