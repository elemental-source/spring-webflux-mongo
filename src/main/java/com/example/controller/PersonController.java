package com.example.controller;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

@RestController
public class PersonController {

    private Scheduler scheduler;

    private final PersonRepository repository;
    private final TaskExecutor taskExecutor;

    @Autowired
    public PersonController(PersonRepository repository, TaskExecutor taskExecutor) {
        this.repository = repository;
        this.taskExecutor = taskExecutor;
    }

    @PostConstruct
    public void init() {
        this.scheduler = Schedulers.elastic();//Schedulers.fromExecutor(taskExecutor);
    }

    @GetMapping("/findAll")
    Flux<Person> findAll() {
        return repository.findAll().subscribeOn(scheduler);
    }

    @GetMapping("/find/{id}")
    Mono<Person> findById(@PathVariable("id") String id) {
        return repository.findOne(id).subscribeOn(scheduler);
    }

}
