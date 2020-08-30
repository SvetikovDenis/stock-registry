package com.svetikov.stockregistry.mapper;

import com.svetikov.stockregistry.dto.StockDTO;
import com.svetikov.stockregistry.model.Status;
import com.svetikov.stockregistry.model.Stock;
import com.svetikov.stockregistry.service.StockStatusService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StockDTOMapper extends AbstractMapper<Stock, StockDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StockStatusService stockStatusService;

    StockDTOMapper() {
        super(Stock.class, StockDTO.class);
    }

    @PostConstruct
    public void setupMapper() {

        mapper.createTypeMap(Stock.class, StockDTO.class)
                .addMappings(new StockMap()).setPostConverter(toDtoConverter());

        mapper.createTypeMap(StockDTO.class, Stock.class)
                .addMappings(new StockDTOMap()).setPostConverter(toEntityConverter());
    }


    private class StockMap extends PropertyMap<Stock, StockDTO> {
        @Override
        protected void configure() {
            skip().setStatus(null);
        }
    }

    private class StockDTOMap extends PropertyMap<StockDTO, Stock> {
        @Override
        protected void configure() {
            skip().setStatus(null);
        }
    }

    @Override
    void mapSpecificFields(Stock source, StockDTO destination) {
        destination.setStatus(source.getStatus().getName());
    }

    @Override
    void mapSpecificFields(StockDTO source, Stock destination) {
        if (source.getStatus() != null) {
            String status = source.getStatus();
            Status statusCandidate = stockStatusService.getByName(status);
            destination.setStatus(statusCandidate);
        }
    }

}
