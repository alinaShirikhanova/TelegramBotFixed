package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.configuration.BotConfiguration;
import com.shelterTelegramBot.demo.entity.PetEntity;
import com.shelterTelegramBot.demo.repository.PetRepository;
import com.shelterTelegramBot.demo.repository.RuleRepository;
import com.shelterTelegramBot.demo.repository.ShelterRepository;
import com.shelterTelegramBot.demo.repository.UserRepository;
import com.shelterTelegramBot.demo.service.GetPetService;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shelterTelegramBot.demo.controller.TelegramBotController.*;
@Component
public class PetController{
    private final GetPetService getPetService;

    public PetController(GetPetService getPetService) {
        this.getPetService = getPetService;
    }

//    public void executePets(String callBackData, Long chatId) {
//        if (callBackData.equals(ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_DATA)) {
//            getPetService.getPetFromShelterMenu(chatId, "Выберите пункт: ");
//        } else if (callBackData.contains("_PETS_BY_ID_")) {
//            Long petId = Long.parseLong(callBackData.split("_")[0]);
//            getPetService.setPetInfoMenu(chatId, "Что хотите узнать?", petId);
//        } else if (callBackData.contains("PET_INFO")) {
//            getPetService.getInfoByPetId(chatId, callBackData);
//        } else if (callBackData.contains("GETPET")) {
//            getPetService.getPetsOrRecommendationsMenu(chatId, callBackData);
//        }
//    }
}
