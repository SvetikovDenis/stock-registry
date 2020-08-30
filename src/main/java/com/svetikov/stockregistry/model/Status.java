package com.svetikov.stockregistry.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "stock_status")
public class Status extends AbstractEntity {

    @Column(name = "name")
    private String name;

    public Status() {
    }

    @Builder
    public Status(Long id, String name) {
        setId(id);
        this.name = name;
    }
}
