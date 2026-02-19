package com.ecommerce.vichika.repository;

import com.ecommerce.vichika.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}