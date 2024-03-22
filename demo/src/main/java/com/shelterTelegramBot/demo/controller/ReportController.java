package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.shelterTelegramBot.demo.controller.TelegramBotController.*;

@Component
public class ReportController {
    private void setRecommendationsMenu(Long chatId, String text) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.RULES_DATING_PETS_BUTTON_NAME, ButtonsNames.RULES_DATING_PETS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.DOCUMENTS_PETS_BUTTON_NAME, ButtonsNames.DOCUMENTS_PETS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.REFUSAL_TO_ISSUE_ANIMAL_BUTTON_NAME, ButtonsNames.REFUSAL_TO_ISSUE_ANIMAL_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.TRANSPORTATION_RECOMMENDATIONS_BUTTON_NAME, ButtonsNames.TRANSPORTATION_RECOMMENDATIONS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.RECOMMENDATION_HOUSE_BUTTON_NAME, ButtonsNames.RECOMMENDATION_HOUSE_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.RECOMMENDATIONS_DOG_HANDLER_BUTTON_NAME, ButtonsNames.RECOMMENDATIONS_DOG_HANDLER_BUTTON_DATA));
        setKeyboard(chatId, text, lists, 1);
    }
}
