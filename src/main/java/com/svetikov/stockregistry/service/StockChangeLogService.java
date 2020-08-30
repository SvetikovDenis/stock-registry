package com.svetikov.stockregistry.service;

import com.svetikov.stockregistry.model.StockChangeLog;

import java.util.List;

public interface StockChangeLogService {

    StockChangeLog getById(Long id);

    List<StockChangeLog> getAll();

    List<StockChangeLog> getAllByEdrpou(String edrpou);

    void save(StockChangeLog stockChangeLog);

    void saveAll(List<StockChangeLog> stockChangeLogs);

}
