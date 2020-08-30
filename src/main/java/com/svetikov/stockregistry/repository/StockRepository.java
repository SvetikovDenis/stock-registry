package com.svetikov.stockregistry.repository;

import com.svetikov.stockregistry.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long>, JpaSpecificationExecutor<Stock> {

    Stock getById(Long id);


    @Query("select count(s.id) from Stock as s")
    Long countAllById();

}
