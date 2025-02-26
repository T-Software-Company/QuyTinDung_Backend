package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.CreditRating;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRatingRepository
    extends JpaRepository<CreditRating, UUID>, JpaSpecificationExecutor<CreditRating> {
  Optional<CreditRating> findByApplicationId(UUID applicationId);
}
