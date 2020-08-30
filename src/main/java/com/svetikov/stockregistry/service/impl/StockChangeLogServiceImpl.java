package com.svetikov.stockregistry.service.impl;

import com.svetikov.stockregistry.exception.EntityNotFoundException;
import com.svetikov.stockregistry.model.StockChangeLog;
import com.svetikov.stockregistry.repository.StockChangeLogRepository;
import com.svetikov.stockregistry.service.StockChangeLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockChangeLogServiceImpl implements StockChangeLogService {

    @Autowired
    private StockChangeLogRepository stockChangeLogRepository;

    private final Logger log = LoggerFactory.getLogger(StockChangeLogServiceImpl.class);

    @Override
    public StockChangeLog getById(Long id) {
        StockChangeLog stockChangeLog = stockChangeLogRepository.getById(id);
        if (stockChangeLog == null) {
            log.warn("In getById - stock change log with id : {} was not found",id);
            throw new EntityNotFoundException(StockChangeLog.class, "id", id.toString());
        }
        log.info("In getById - stock change log with id : {} was found",id);
        return stockChangeLog;
    }

    @Override
    public List<StockChangeLog> getAll() {
        List<StockChangeLog> changeLogs = stockChangeLogRepository.findAll();
        if (changeLogs.isEmpty()) {
            log.warn("In getAll - no change logs was found");
            return null;
        }
        log.info("In getAll - {} logs was found", changeLogs.size());
        return changeLogs;
    }

    @Override
    public List<StockChangeLog> getAllByEdrpou(String edrpou) {
        List<StockChangeLog> changeLogs = stockChangeLogRepository.getAllByEdrpou(edrpou);
        if (changeLogs.isEmpty()) {
            log.warn("In getAllByEdrpou - no change logs was found for edrpou : {}",edrpou);
            throw new EntityNotFoundException(StockChangeLog.class, "edrpou", edrpou);
        }
        log.info("In getAllByEdrpou - {} logs was found", changeLogs.size());
        return changeLogs;
    }

    @Override
    public void save(StockChangeLog stockChangeLog) {
        stockChangeLogRepository.save(stockChangeLog);
        log.info("In save - {} log was saved",stockChangeLog);
    }

    @Override
    public void saveAll(List<StockChangeLog> stockChangeLogs) {
        stockChangeLogRepository.saveAll(stockChangeLogs);
        log.info("In save - {} logs was saved",stockChangeLogs.size());
    }
}
