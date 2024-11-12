package com.tsoftware.qtd.service.impl;

import com.tsoftware.qtd.dto.AssetRepissessionNoticeDto;
import com.tsoftware.qtd.entity.AssetRepissessionNotice;
import com.tsoftware.qtd.mapper.AssetRepissessionNoticeMapper;
import com.tsoftware.qtd.repository.AssetRepissessionNoticeRepository;
import com.tsoftware.qtd.service.AssetRepissessionNoticeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetRepissessionNoticeServiceImpl implements AssetRepissessionNoticeService {

  @Autowired private AssetRepissessionNoticeRepository assetrepissessionnoticeRepository;

  @Autowired private AssetRepissessionNoticeMapper assetrepissessionnoticeMapper;

  @Override
  public AssetRepissessionNoticeDto create(AssetRepissessionNoticeDto assetrepissessionnoticeDto) {
    AssetRepissessionNotice assetrepissessionnotice =
        assetrepissessionnoticeMapper.toEntity(assetrepissessionnoticeDto);
    return assetrepissessionnoticeMapper.toDto(
        assetrepissessionnoticeRepository.save(assetrepissessionnotice));
  }

  @Override
  public AssetRepissessionNoticeDto update(
      Long id, AssetRepissessionNoticeDto assetrepissessionnoticeDto) {
    AssetRepissessionNotice assetrepissessionnotice =
        assetrepissessionnoticeRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("AssetRepissessionNotice not found"));
    assetrepissessionnoticeMapper.updateEntity(assetrepissessionnoticeDto, assetrepissessionnotice);
    return assetrepissessionnoticeMapper.toDto(
        assetrepissessionnoticeRepository.save(assetrepissessionnotice));
  }

  @Override
  public void delete(Long id) {
    assetrepissessionnoticeRepository.deleteById(id);
  }

  @Override
  public AssetRepissessionNoticeDto getById(Long id) {
    AssetRepissessionNotice assetrepissessionnotice =
        assetrepissessionnoticeRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("AssetRepissessionNotice not found"));
    return assetrepissessionnoticeMapper.toDto(assetrepissessionnotice);
  }

  @Override
  public List<AssetRepissessionNoticeDto> getAll() {
    return assetrepissessionnoticeRepository.findAll().stream()
        .map(assetrepissessionnoticeMapper::toDto)
        .collect(Collectors.toList());
  }
}
