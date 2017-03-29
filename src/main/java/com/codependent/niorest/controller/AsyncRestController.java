package com.codependent.niorest.controller;

import com.codependent.niorest.dto.Data;
import com.codependent.niorest.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Asynchronous data controller
 *
 * @author JINGA4X
 */
@RestController
public class AsyncRestController {

    @Autowired
    private DataService dataService;


    private reactor.core.scheduler.Scheduler reactorScheduler;

    @Autowired
    private TaskExecutor executor;

    @PostConstruct
    protected void initializeScheduler() {
        reactorScheduler = reactor.core.scheduler.Schedulers.fromExecutor(executor);
    }

    @RequestMapping(value = "/mono/data", method = RequestMethod.GET, produces = "application/json")
    public Mono<List<Data>> getDataFromMono() {
        Mono<List<Data>> monoData = dataService.loadDataMono();
        return monoData.subscribeOn(reactorScheduler);
    }

}
