package com.svetikov.stockregistry.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "stock_change_log")
public class StockChangeLog extends AbstractEntity {

    @NotNull
    @Column(name = "edrpou")
    private String edrpou;

    @NotNull
    @Column(name = "column_name")
    private String columnName;

    @NotNull
    @Column(name = "previous_value")
    private String previousValue;

    @NotNull
    @Column(name = "current_value")
    private String currentValue;


    public StockChangeLog() {
    }

    @Builder
    public StockChangeLog( Long id, String edrpou,  String columnName,  String previousValue,  String currentValue) {
        this.setId(id);
        this.edrpou = edrpou;
        this.columnName = columnName;
        this.previousValue = previousValue;
        this.currentValue = currentValue;
    }
}
