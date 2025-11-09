package com.eaglebank.repository;

import com.eaglebank.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
