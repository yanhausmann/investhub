package com.yanhausmann.investhub.service;

import com.yanhausmann.investhub.controller.dto.CreateStockDTO;
import com.yanhausmann.investhub.entity.Stock;
import com.yanhausmann.investhub.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        // DTO -> ENTITY
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}