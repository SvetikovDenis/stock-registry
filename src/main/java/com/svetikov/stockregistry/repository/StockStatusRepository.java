package com.svetikov.stockregistry.repository;

import com.svetikov.stockregistry.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockStatusRepository extends JpaRepository<Status,Long> {

    Status getById(Long id);

    Status findByName(String name);

    @Query("select count(s.id) from Status as s")
    Long countAllById();

}
