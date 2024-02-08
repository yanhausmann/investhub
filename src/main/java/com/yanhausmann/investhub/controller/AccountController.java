package com.yanhausmann.investhub.controller;

import com.yanhausmann.investhub.dto.AccountStockReponseDTO;
import com.yanhausmann.investhub.dto.AssociateAccountStockDTO;
import com.yanhausmann.investhub.dto.CreateAccountDTO;
import com.yanhausmann.investhub.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                           @RequestBody AssociateAccountStockDTO associateAccountStockDTO){
        accountService.associateStock(accountId, associateAccountStockDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockReponseDTO>> associateStock(@PathVariable("accountId") String accountId) {

        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
