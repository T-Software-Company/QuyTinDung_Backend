package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.RatingCriterionSetting;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingCriterionSettingRepository
    extends JpaRepository<RatingCriterionSetting, UUID>,
        JpaSpecificationExecutor<RatingCriterionSetting> {}
