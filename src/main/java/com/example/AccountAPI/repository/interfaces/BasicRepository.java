package com.example.AccountAPI.repository.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasicRepository<T> {
    Optional<UUID> create(T item);

    void delete(T item);

    Optional<T> findOne(UUID id);

    List<T> getAll();

    boolean exists(UUID id);
}
