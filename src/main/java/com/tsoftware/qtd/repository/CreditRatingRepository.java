package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.CreditRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRatingRepository extends JpaRepository<CreditRating, Long> {}
