package com.mamotec.energycontrolbackend.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrudOperations<T> {

    default Optional<JpaRepository<T, Integer>> getRepository() {
        return Optional.empty();
    }

    default T save(T record) {
        if (this.getRepository().isPresent()) {
            JpaRepository<T, Integer> repo = this.getRepository()
                    .get();

            return repo.save(record);
        }

        throw new NotImplementedException("Create not implemented!");

    }
}
