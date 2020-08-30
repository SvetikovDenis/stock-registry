package com.svetikov.stockregistry.service.impl;

import com.svetikov.stockregistry.exception.EntityNotFoundException;
import com.svetikov.stockregistry.model.Status;
import com.svetikov.stockregistry.repository.StockStatusRepository;
import com.svetikov.stockregistry.service.StockStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockStatusServiceImpl implements StockStatusService {

    @Autowired
    private StockStatusRepository stockStatusRepository;

    private final Logger log = LoggerFactory.getLogger(StockStatusServiceImpl.class);

    @Override
    public List<Status> getAll() {
        List<Status> statuses = stockStatusRepository.findAll();
        if (statuses.isEmpty()) {
            log.warn("In getAll - not statuses was found");
            return null;
        }
        log.info("In getAll - {} statuses was found",statuses.size());
        return statuses;
    }

    @Override
    public Status getById(Long id) {
        Status status = stockStatusRepository.getById(id);
        if (status == null) {
            log.warn("In getById - status with id : {} was not found", id);
            throw new EntityNotFoundException(Status.class, "id", id.toString());
        }
        log.info("In getById - status with id : {} was not found");
        return status;
    }

    @Override
    public Status getByName(String name) {
        Status status = stockStatusRepository.findByName(name);
        if (status == null) {
            log.warn("In getByName - status with name : {} was not found", name);
            throw new EntityNotFoundException(Status.class, "name", name);
        }
        log.info("In getByName - status with name : {} was found", name);
        return status;
    }

    @Override
    public void save(Status status) {
        Status newStatus = stockStatusRepository.save(status);
        log.info("In save - status : {} was saved", newStatus);
    }

    @Override
    public void saveAll(List<Status> statuses) {
        List<Status> newStatuses = stockStatusRepository.saveAll(statuses);
        log.info("In saveAll - {} statuses was saved", statuses);
    }

    @Override
    public Long getStatusNumber() {
        return stockStatusRepository.countAllById();
    }
}
