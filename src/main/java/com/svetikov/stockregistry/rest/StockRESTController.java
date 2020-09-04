package com.svetikov.stockregistry.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.svetikov.stockregistry.dto.StockDTO;
import com.svetikov.stockregistry.service.StockService;
import com.svetikov.stockregistry.specification.StockSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/stock")
public class StockRESTController {

    @Autowired
    private StockService stockService;

    @GetMapping("")
    public ResponseEntity<List<StockDTO>> getAllStocks(
            StockSearchCriteria request,
            @RequestParam(name = "page", required = false, defaultValue = "0") @Min(0) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(0)  Integer size) {

        List<StockDTO> stocks = stockService.getAllDTO(request, page, size);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        StockDTO stock = stockService.getByIdDTO(id);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @JsonView(StockDTO.StandardView.class)
    @PostMapping("")
    public ResponseEntity<StockDTO> createStock(@Validated({StockDTO.New.class}) @RequestBody StockDTO stockDTO) {
        StockDTO stock = stockService.saveDTO(stockDTO);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @JsonView(StockDTO.StandardView.class)
    @PutMapping("")
    public ResponseEntity<StockDTO> udpateStock(@Validated({StockDTO.Existing.class}) @RequestBody StockDTO stockDTO) {
        StockDTO stock = stockService.updateDTO(stockDTO);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStockById(@PathVariable Long id) {
        stockService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
