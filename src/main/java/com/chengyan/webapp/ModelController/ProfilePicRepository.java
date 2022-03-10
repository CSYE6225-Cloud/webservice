package com.chengyan.webapp.ModelController;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfilePicRepository extends CrudRepository<ProfilePic, Integer> {
    Optional<ProfilePic> findByUserId(UUID userId);
}
