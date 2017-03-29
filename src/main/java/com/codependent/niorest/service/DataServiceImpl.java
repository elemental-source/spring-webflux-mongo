package com.codependent.niorest.service;

import com.codependent.niorest.dto.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Override
    public Mono<List<Data>> loadDataMono() {
        return Mono.fromCallable(() -> generateData(false));
    }

    private List<Data> generateData(boolean fail) {
        if (fail) {
            throw new RuntimeException("Counldn't generate data");
        }
        List<Data> dataList = new ArrayList<Data>();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++) {
            Data data = new Data("key" + i, "value" + i);
            dataList.add(data);
        }
        return dataList;
    }

}
