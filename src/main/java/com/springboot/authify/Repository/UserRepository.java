package com.springboot.authify.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.authify.Entity.UserEntity;

import jakarta.transaction.Transactional;    

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByUserId(String userId);
    Boolean existsByUserId(String userId);
    @Transactional
    void deleteByUserId(String userId);
}
