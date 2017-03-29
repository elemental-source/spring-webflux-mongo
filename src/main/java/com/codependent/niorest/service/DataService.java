package com.codependent.niorest.service;

import com.codependent.niorest.dto.Data;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DataService {
    Mono<List<Data>> loadDataMono();
}