package com.yanhausmann.investhub.service;

import com.yanhausmann.investhub.dto.CreateStockDTO;
import com.yanhausmann.investhub.entity.Stock;
import com.yanhausmann.investhub.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {
        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
