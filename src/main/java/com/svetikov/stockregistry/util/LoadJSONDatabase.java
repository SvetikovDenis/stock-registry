package com.svetikov.stockregistry.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svetikov.stockregistry.dto.StockDTO;
import com.svetikov.stockregistry.mapper.StockDTOMapper;
import com.svetikov.stockregistry.model.Status;
import com.svetikov.stockregistry.model.Stock;
import com.svetikov.stockregistry.service.StockService;
import com.svetikov.stockregistry.service.StockStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoadJSONDatabase implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockStatusService stockStatusService;

    @Autowired
    private StockDTOMapper DTOmapper;

    private final String STOCK_JSON_FILE = "/data/stock.json";

    private final String STATUS_JSON_FILE = "/data/status.json";


    private void loadStatusData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Status>> typeReference = new TypeReference<List<Status>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream(STATUS_JSON_FILE);
        try {
            List<Status> statuses = objectMapper.readValue(inputStream, typeReference);
            stockStatusService.saveAll(statuses);
        } catch (IOException e) {
            System.out.println("Unable to save statuses: " + e.getMessage());
        }
    }

    private void loadStockData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<StockDTO>> typeReference = new TypeReference<List<StockDTO>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream(STOCK_JSON_FILE);
        try {
            List<StockDTO> stockDTOS = objectMapper.readValue(inputStream, typeReference);
            List<Stock> stocks = new ArrayList<>();
            for (StockDTO stockDTO : stockDTOS) {
                stocks.add(DTOmapper.toEntity(stockDTO));
            }
            stockService.saveAll(stocks);
        } catch (IOException e) {
            System.out.println("Unable to save stocks: " + e.getMessage());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            Long statuses = stockStatusService.getStatusNumber();
            Long stocks = stockService.getStockNumber();
            if (statuses == null || statuses == 0) {
                loadStatusData();
            }
            if (stocks == null | stocks == 0) {
                loadStockData();
            }
        }
    }
}
