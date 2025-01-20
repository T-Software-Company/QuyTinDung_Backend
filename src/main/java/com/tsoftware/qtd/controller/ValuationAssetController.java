package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.service.ValuationAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valuation-assets")
@RequiredArgsConstructor
public class ValuationAssetController {

  private final ValuationAssetService valuationassetService;
}
