package com.stevenkolamkuzhiyil.SpringCrud.repository;

import com.stevenkolamkuzhiyil.SpringCrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface UserBaseRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(@Email String email);
}
