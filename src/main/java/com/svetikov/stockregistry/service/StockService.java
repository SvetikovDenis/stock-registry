package com.svetikov.stockregistry.service;

import com.svetikov.stockregistry.dto.StockDTO;
import com.svetikov.stockregistry.model.Stock;
import com.svetikov.stockregistry.specification.StockSearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {

    List<Stock> getAll();

    List<StockDTO> getAllDTO(StockSearchCriteria request, Integer page, Integer size);

    Stock getById(Long id);

    StockDTO getByIdDTO(Long id);

    Stock save(Stock stock);

    StockDTO saveDTO(StockDTO stock);

    Stock update(Stock stock);

    StockDTO updateDTO(StockDTO stock);

    void saveAll(List<Stock> stocks);

    void delete(Stock stock);

    void deleteById(Long id);

    Long getStockNumber();

}
