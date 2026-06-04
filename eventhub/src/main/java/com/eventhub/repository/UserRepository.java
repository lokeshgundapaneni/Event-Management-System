package com.eventhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
