package com.cearsolutions.repository;

import com.cearsolutions.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link UserRepository}
 *
 * @author Dmytro Trotsenko on 4/26/24
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  List<User> findAllByBirthDateBetween(LocalDate from, LocalDate to);
}
