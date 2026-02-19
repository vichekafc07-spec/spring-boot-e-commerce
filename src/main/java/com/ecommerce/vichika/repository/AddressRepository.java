package com.ecommerce.vichika.repository;

import com.ecommerce.vichika.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}