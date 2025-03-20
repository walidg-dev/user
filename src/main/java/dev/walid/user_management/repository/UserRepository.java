package dev.walid.user_management.repository;

import dev.walid.user_management.repository.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<UserEntity, Integer> {
}
