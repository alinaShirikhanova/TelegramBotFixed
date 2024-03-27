package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.service.ShelterService;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class ShelterController {

    private final ShelterService shelterInfoService;

    public ShelterController(ShelterService shelterInfoService) {
        this.shelterInfoService = shelterInfoService;
    }

    public SendMessage executeCommand(String callBackData) {
        if (callBackData.equals(ButtonsNames.SHELTER_GROUP_INFO_ABOUT_SHELTER_BUTTON_DATA)) {
            return shelterInfoService.setSheltersMenuBot();
        } else if (callBackData.contains("SHELTERS")) {
            Long shelterId = Long.parseLong(callBackData.split("_")[0]);
            return shelterInfoService.setShelterInfoMenu(shelterId);
        }
        else if (callBackData.contains("INFO")) {
           return shelterInfoService.getInfoByShelterId(callBackData);
        }
        return null;
    }
}
