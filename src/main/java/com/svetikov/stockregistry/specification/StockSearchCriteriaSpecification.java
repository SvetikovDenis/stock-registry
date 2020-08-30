package com.svetikov.stockregistry.specification;

import com.svetikov.stockregistry.model.Stock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;


@Component
public class StockSearchCriteriaSpecification extends BaseSpecification<Stock, StockSearchCriteria> {

    @Override
    public Specification<Stock> getFilter(StockSearchCriteria request) {
        return Specifications.where(stockEdrpouEqual(request.getEdrpou()))
                .and(Specifications.where(stockCommentContains(request.getComment()))
                        .and(Specifications.where(stockStatusEqual(request.getStatus()))));
    }


    private Specification<Stock> stockAttributeContains(final String attribute, final String value) {
        if (value == null) {
            return null;
        }
        return new Specification<Stock>() {
            public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.like(builder.lower(root.<String>get(attribute)), containsLowerCase(value));
                return builder.and(predicate);
            }
        };
    }

    private Specification<Stock> stockAttributeIsEqual(final String attribute, final String value) {
        if (value == null) {
            return null;
        }
        return new Specification<Stock>() {
            public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.equal(builder.lower(root.<String>get(attribute)), value.toLowerCase());
                return builder.and(predicate);
            }
        };
    }

    private Specification<Stock> stockStatusIsEqual(final String status, final String value) {
        if (value == null) {
            return null;
        }
        return new Specification<Stock>() {
            public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.equal(builder.lower(root.<String>get(status).<String>get("name")), value.toLowerCase());
                return builder.and(predicate);
            }
        };
    }

    private Specification<Stock> stockCommentContains(String comment) {
        return stockAttributeContains("comment", comment);
    }

    private Specification<Stock> stockEdrpouEqual(String edrpou) {
        return stockAttributeIsEqual("edrpou", edrpou);
    }

    private Specification<Stock> stockStatusEqual(String status) {
        return stockStatusIsEqual("status", status);
    }

}
