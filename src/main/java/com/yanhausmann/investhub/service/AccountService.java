package com.yanhausmann.investhub.service;

import com.yanhausmann.investhub.client.BrapiClient;
import com.yanhausmann.investhub.dto.AccountStockReponseDTO;
import com.yanhausmann.investhub.dto.AssociateAccountStockDTO;
import com.yanhausmann.investhub.entity.AccountStock;
import com.yanhausmann.investhub.entity.AccountStockId;
import com.yanhausmann.investhub.repository.AccountRepository;
import com.yanhausmann.investhub.repository.AccountStockRepository;
import com.yanhausmann.investhub.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository accountStockRepository;

    private BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    public void associateStock(String accountId, AssociateAccountStockDTO associateAccountStockDTO) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateAccountStockDTO.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                associateAccountStockDTO.quantity()
        );
        accountStockRepository.save(entity);

    }

    public List<AccountStockReponseDTO> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStockReponseDTO(as.getStock().getStockId(),as.getQuantity(), 0.0))
                .toList();

    }

    private double getTotal(Integer quantity, String stockId) {

        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().get(0).regularMarketPrice();

        return quantity * price;
    }
}
