package com.svetikov.stockregistry.interceptor;

import com.svetikov.stockregistry.model.Stock;
import com.svetikov.stockregistry.model.StockChangeLog;
import com.svetikov.stockregistry.service.StockChangeLogService;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockInterceptorAudit extends EmptyInterceptor {

    @Autowired
    private StockChangeLogService stockChangeLogService;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {

        if (entity != null && entity instanceof Stock) {
            List<StockChangeLog> changeLogs = new ArrayList<>();
            String edrpou = ((Stock) entity).getEdrpou();
            String columnName;
            StockChangeLog stockChangeLog;
            for (int i = 0; i < currentState.length; i++) {

                if (ObjectUtils.notEqual(currentState[i], previousState[i])) {
                    columnName = propertyNames[i];
                    stockChangeLog = StockChangeLog
                            .builder()
                            .edrpou(edrpou)
                            .columnName(columnName)
                            .currentValue(currentState[i].toString())
                            .previousValue(previousState[i].toString())
                            .build();
                    changeLogs.add(stockChangeLog);
                }
            }
            if (!changeLogs.isEmpty()) {
                stockChangeLogService.saveAll(changeLogs);
            }
        }

        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }


}
