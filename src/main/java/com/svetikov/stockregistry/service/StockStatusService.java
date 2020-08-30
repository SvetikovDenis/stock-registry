package com.svetikov.stockregistry.service;

import com.svetikov.stockregistry.model.Status;

import java.util.List;

public interface StockStatusService {

    List<Status> getAll();

    Status getById(Long id);

    Status getByName(String name);

    void save(Status status);

    void saveAll(List<Status> statuses);

    Long getStatusNumber();

}
