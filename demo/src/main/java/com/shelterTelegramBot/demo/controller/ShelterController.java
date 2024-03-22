package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.service.ShelterInfoService;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;

@Component
public class ShelterController {

    private final ShelterInfoService shelterInfoService;

    public ShelterController(ShelterInfoService shelterInfoService) {
        this.shelterInfoService = shelterInfoService;
    }

    public void executeCommand(String callBackData, Long chatId) {
        if (callBackData.equals(ButtonsNames.INFO_ABOUT_SHELTER_BUTTON_DATA)) {
            shelterInfoService.setSheltersMenuBot(chatId, "Приюты: ");
        } else if (callBackData.contains("SHELTERS")) {
            Long shelterId = Long.parseLong(callBackData.split("_")[0]);
            shelterInfoService.setShelterInfoMenu(chatId, "Что Вы хотите узнать?", shelterId);
        } else if (callBackData.contains("INFO")) {
            shelterInfoService.getInfoByShelterId(chatId, callBackData);
        }
    }

}
