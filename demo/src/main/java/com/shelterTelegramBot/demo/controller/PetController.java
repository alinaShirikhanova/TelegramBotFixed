package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.service.PetService;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;

@Component
public class PetController{
    private final PetService getPetService;

    public PetController(PetService getPetService) {
        this.getPetService = getPetService;
    }

    public void executeCommand(String callBackData) {
        if (callBackData.equals(ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_DATA)) {
            return getPetService.getPetFromShelterMenu( "Выберите пункт: ");
        } else if (callBackData.contains("_PETS_BY_ID_")) {
            Long petId = Long.parseLong(callBackData.split("_")[0]);
            getPetService.setPetInfoMenu( "Что хотите узнать?", petId);
        } else if (callBackData.contains("PET_INFO")) {
            getPetService.getInfoByPetId(callBackData);
        } else if (callBackData.contains("GETPET")) {
            getPetService.getPetsOrRecommendationsMenu(callBackData);
        }
    }

}
