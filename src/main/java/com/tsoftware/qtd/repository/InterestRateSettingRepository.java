package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.InterestRateSetting;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRateSettingRepository
    extends JpaRepository<InterestRateSetting, UUID>,
        JpaSpecificationExecutor<InterestRateSetting> {}
