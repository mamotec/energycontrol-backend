package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceRepository extends JpaRepository<User, Integer> {
}
