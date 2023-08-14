package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.group.Group;
import com.mamotec.energycontrolbackend.repository.GroupRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService implements CrudOperations<Group> {

    private final GroupRepository repository;

    @Override
    public Optional<JpaRepository<Group, Integer>> getRepository() {
        return Optional.of(repository);
    }


}
