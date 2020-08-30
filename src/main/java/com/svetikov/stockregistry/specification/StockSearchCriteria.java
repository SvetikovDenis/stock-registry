package com.svetikov.stockregistry.specification;

import lombok.Data;

import java.sql.Date;

@Data
public class StockSearchCriteria {

    private String comment;
    private String edrpou;
    private String status;
    private Date date;
    private String operation;
    private String sort;
    private String order;

}
