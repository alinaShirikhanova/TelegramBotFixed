package com.shelterTelegramBot.demo.service;


import com.shelterTelegramBot.demo.configuration.BotConfiguration;
import com.shelterTelegramBot.demo.entity.PetEntity;
import com.shelterTelegramBot.demo.entity.ShelterEntity;
import com.shelterTelegramBot.demo.entity.UserEntity;
import com.shelterTelegramBot.demo.repository.*;
import com.shelterTelegramBot.demo.repository.PetRepository;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class UtilsService {
    /**
     * Метод создания клавиатуры бота
     *
     * @param buttonsInfo  список кнопок
     * @param amountOfRows количество строк кнопок
     */
    public InlineKeyboardMarkup setKeyboard(List<List<String>> buttonsInfo, int amountOfRows) {
        //Создание кнопок
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(); //разметка кнопок
        List<List<InlineKeyboardButton>> rows = new ArrayList<>(); //список строк
        List<InlineKeyboardButton> row = new ArrayList<>(); //первый ряд кнопок
        for (int i = 0; i < buttonsInfo.size(); i++) {
            if (amountOfRows == row.size()) {
                rows.add(row);
                row = new ArrayList<>();
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(buttonsInfo.get(i).get(0));
            button.setCallbackData(buttonsInfo.get(i).get(1)); // Узнать информацию о приюте
            row.add(button);
        }
        //добавление в ряд
        rows.add(row);
        //добавляю все ряды в Markup
        keyboard.setKeyboard(rows);
        return keyboard;
    }


}
