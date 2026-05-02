package com.rudra.caption.repository;

import com.rudra.caption.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}