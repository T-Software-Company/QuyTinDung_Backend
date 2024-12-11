package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.Apartment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, UUID> {}
