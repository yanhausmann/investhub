package com.yanhausmann.investhub.controller;

import com.yanhausmann.investhub.controller.dto.CreateStockDTO;
import com.yanhausmann.investhub.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO){

        stockService.createStock(createStockDTO);

        return ResponseEntity.ok().build();
    }
}
