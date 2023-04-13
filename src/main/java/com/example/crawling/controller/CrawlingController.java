package com.example.crawling.controller;


import com.example.crawling.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawl")
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingService crawlingService;

    @GetMapping("/hana")
    public void getHanaDeposit(){

        crawlingService.crawling("hana");
    }
}
