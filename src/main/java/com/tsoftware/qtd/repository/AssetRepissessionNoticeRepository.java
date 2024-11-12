package com.tsoftware.qtd.repository;

import com.tsoftware.qtd.entity.AssetRepissessionNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepissessionNoticeRepository
    extends JpaRepository<AssetRepissessionNotice, Long> {}
