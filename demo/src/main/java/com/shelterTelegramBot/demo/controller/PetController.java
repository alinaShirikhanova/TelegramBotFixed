package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.service.PetService;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class PetController{
    private final PetService getPetService;

    public PetController(PetService getPetService) {
        this.getPetService = getPetService;
    }

    public SendMessage executeCommand(String callBackData) {
        if (callBackData.equals(ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_DATA)) {
            return getPetService.getPetFromShelterMenu();
        } else if (callBackData.contains("_PETS_BY_ID_")) {
            Long petId = Long.parseLong(callBackData.split("_")[0]);
            return getPetService.setPetInfoMenu(petId);
        } else if (callBackData.contains("PET_INFO")) {
            return getPetService.getInfoByPetId(callBackData);
        } else if (callBackData.contains("GETPET")) {
            return getPetService.getPetsOrRecommendationsMenu(callBackData);
        }
        return null;
    }

}
