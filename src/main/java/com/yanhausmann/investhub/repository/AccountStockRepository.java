package com.yanhausmann.investhub.repository;

import com.yanhausmann.investhub.entity.AccountStock;
import com.yanhausmann.investhub.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
