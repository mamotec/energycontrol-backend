package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
