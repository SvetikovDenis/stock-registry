package com.svetikov.stockregistry.repository;

import com.svetikov.stockregistry.model.StockChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockChangeLogRepository extends JpaRepository<StockChangeLog,Long> {

    StockChangeLog getById(Long id);

    List<StockChangeLog> getAllByEdrpou(String edrpou);

}
