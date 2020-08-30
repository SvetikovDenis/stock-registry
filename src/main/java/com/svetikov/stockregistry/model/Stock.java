package com.svetikov.stockregistry.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "stock_detail")
public class Stock extends AbstractEntity{

    @NotNull
    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "edrpou")
    private String edrpou;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "nominal_value")
    private Integer nominalValue;

    @Column(name = "total_value")
    private Integer totalValue;

    @NotNull
    @Column(name = "issue_date")
    private Date issueDate;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @PrePersist
    public void toCreate() {
        setTotalValue(getNominalValue() * getQuantity());
    }

}
