package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.service.UtilsService;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationController {
    private final UtilsService utilsService;

    public RecommendationController(UtilsService utilsService) {
        this.utilsService = utilsService;
    }


}
