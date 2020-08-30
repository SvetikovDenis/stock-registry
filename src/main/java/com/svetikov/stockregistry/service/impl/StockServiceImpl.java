package com.svetikov.stockregistry.service.impl;

import com.svetikov.stockregistry.dto.StockDTO;
import com.svetikov.stockregistry.exception.EntityNotFoundException;
import com.svetikov.stockregistry.mapper.StockDTOMapper;
import com.svetikov.stockregistry.model.Status;
import com.svetikov.stockregistry.model.Stock;
import com.svetikov.stockregistry.repository.StockRepository;
import com.svetikov.stockregistry.service.StockService;
import com.svetikov.stockregistry.service.StockStatusService;
import com.svetikov.stockregistry.specification.StockSearchCriteria;
import com.svetikov.stockregistry.specification.StockSearchCriteriaSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockSearchCriteriaSpecification stockSpecification;

    @Autowired
    private StockDTOMapper stockDTOMapper;

    @Autowired
    private StockStatusService stockStatusService;

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);


    @Override
    public List<Stock> getAll() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) {
            log.warn("In getAll - no stocks was found");
            return null;
        }
        log.info("In getAll - {} stocks was found", stocks.size());
        return stocks;
    }

    @Override
    public List<StockDTO> getAllDTO(StockSearchCriteria request, Pageable pageable) {
        Page<Stock> stocks = stockRepository.findAll(
                stockSpecification.getFilter(request), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        if (stocks.getTotalElements() == 0) {
            log.warn("In getAllDTO - no stocks was found for request parameters : " + request.toString());
            throw new EntityNotFoundException(Stock.class, "search request", request.toString());
        }
        log.info("In getAllDTO - {} stocks was found", stocks.getTotalElements());
        List<StockDTO> stockDTOS = new ArrayList<>();
        for (Stock stock : stocks.getContent()) {
            stockDTOS.add(stockDTOMapper.toDto(stock));
        }
        return stockDTOS;
    }

    @Override
    public Stock getById(Long id) {
        Stock stock = stockRepository.getById(id);
        if (stock == null) {
            log.warn("In getById - stock with id : {} was not found", id);
            throw new EntityNotFoundException(Stock.class, "id", id.toString());
        }
        log.info("In getById - stock with id : {} was found", id);
        return stock;
    }


    @Override
    public StockDTO getByIdDTO(Long id) {
        Stock stock = stockRepository.getById(id);
        if (stock == null) {
            log.warn("In getById - stock with id : {} was not found", id);
            throw new EntityNotFoundException(Stock.class, "id", id.toString());
        }
        log.info("In getById - stock with id : {} was found", id);
        return stockDTOMapper.toDto(stock);
    }


    @Override
    public Stock save(Stock stock) {
        Status status = stockStatusService.getByName("ACTIVE");
        stock.setStatus(status);
        Stock newStock = stockRepository.save(stock);
        log.info("In save - stock : {} was saved", stock);
        return newStock;
    }

    @Override
    public StockDTO saveDTO(StockDTO stock) {
        Stock stockCandidate = stockDTOMapper.toEntity(stock);
        Status status = stockStatusService.getByName("ACTIVE");
        stockCandidate.setStatus(status);
        stockCandidate = stockRepository.save(stockCandidate);
        return stockDTOMapper.toDto(stockCandidate);
    }

    @Override
    public Stock update(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public StockDTO updateDTO(StockDTO stockDTO) {
        Long stockId = stockDTO.getId();
        Stock stockCandidate = stockRepository.getById(stockId);

        if (stockCandidate == null) {
            log.warn("In updateDTO - stock with id : {} was not found", stockId);
            throw new EntityNotFoundException(Stock.class, "id", stockId.toString());
        }
        stockDTOMapper.updateEntity(stockDTO, stockCandidate);
        stockCandidate = stockRepository.save(stockCandidate);
        return stockDTOMapper.toDto(stockCandidate);
    }

    @Override
    public void delete(Stock stock) {
        Stock stockCandidate = stockRepository.getOne(stock.getId());
        if (stockCandidate == null) {
            log.warn("In delete - stock : {} was not found", stock);
            throw new EntityNotFoundException(Stock.class, "id", stock.getId().toString());
        }
        Status deleted = Status.builder().id(2l).name("DELETED").build();
        stockCandidate.setStatus(deleted);
        stockRepository.save(stockCandidate);
        log.info("In delete - stock : {} was deleted", stock);

    }

    @Override
    public void deleteById(Long id) {
        Stock stockCandidate = stockRepository.getOne(id);
        if (stockCandidate == null) {
            log.warn("In deleteById - stock with id : {} was not found", id);
            throw new EntityNotFoundException(Stock.class, "id", id.toString());
        }
        Status deleted = Status.builder().id(2l).name("DELETED").build();
        stockCandidate.setStatus(deleted);
        stockRepository.save(stockCandidate);
        log.info("In deleteById - stock with id : {} was deleted", id);
    }

    @Override
    public void saveAll(List<Stock> stocks) {
        Status status = stockStatusService.getByName("ACTIVE");
        for (Stock stock : stocks) {
            stock.setStatus(status);
        }
        stockRepository.saveAll(stocks);
        log.info("In saveAll - {} stocks was saved", stocks.size());
    }

    @Override
    public Long getStockNumber() {
        return stockRepository.countAllById();
    }
}
