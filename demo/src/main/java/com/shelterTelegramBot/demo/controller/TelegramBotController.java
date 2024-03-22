package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.configuration.BotConfiguration;
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

@Component
public class TelegramBotController extends TelegramLongPollingBot {
    private final UserRepository userRepository;
    private final BotConfiguration configuration;
    private final ShelterRepository shelterRepository;
    private final PetRepository petRepository;
    private final RuleRepository ruleRepository;

    private final PetController petController;

    private final ShelterController shelterInfoController;

    public TelegramBotController(UserRepository userRepository,
                                 BotConfiguration configuration,
                                 ShelterRepository shelterRepository,
                                 PetRepository petRepository,
                                 RuleRepository ruleRepository, PetController petController, ShelterController shelterInfoController) {
        super(configuration.getToken());
        this.userRepository = userRepository;
        this.configuration = configuration;
        this.shelterRepository = shelterRepository;
        this.petRepository = petRepository;
        this.ruleRepository = ruleRepository;
        this.petController = petController;
        this.shelterInfoController = shelterInfoController;
    }

    /**
     * Основной метод взаимодействия бота с пользователем
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        Long chatId;
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
            String text = update.getMessage().getText();
            UserEntity userEntity = new UserEntity().setChatId(chatId).setName(name);
            if (userRepository.findByChatId(chatId).isPresent()) {
                sendMessage(chatId, "И снова здравствуйте!" + userEntity.getName());
            } else {
                sendMessage(chatId, "Здравствуйте!" + userEntity.getName());
                userRepository.save(userEntity);
            }
            setStarMenuBot(chatId, "Главное меню");
        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callBackData.contains("SHELTER_GROUP")) {
                shelterInfoController.executeCommand(callBackData, chatId);
            }
            if (callBackData.contains("PET_GROUP")) {
                petController.executePets(callBackData, chatId);
            }
        }
    }
    /**
     * Метод создания стартового меню с кнопками
     *
     * @param chatId ID чата
     * @param text   текст информации кнопок
     */
    private void setStarMenuBot(Long chatId, String text) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.INFO_ABOUT_SHELTER_BUTTON_NAME, ButtonsNames.INFO_ABOUT_SHELTER_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_NAME, ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.SEND_REPORT_PETS_BUTTON_NAME, ButtonsNames.SEND_REPORT_PETS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.CALL_VOLUNTEER_BUTTON_NAME, ButtonsNames.CALL_VOLUNTEER_BUTTON_DATA));
        setKeyboard(chatId, text, lists, 2);
    }
    /**
     * Метод создания клавиатуры бота
     *
     * @param chatId       ID чата
     * @param text         текс с информацией кнопки
     * @param buttonsInfo  список кнопок
     * @param amountOfRows количество строк кнопок
     */
    public void setKeyboard(Long chatId, String text, List<List<String>> buttonsInfo, int amountOfRows) {
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
        sendMessageWithKeyboard(chatId, text, keyboard);
    }
    /**
     * Метод создания кливиатуры
     *
     * @param chatId ID чата
     * @param text   Текст кнопок
     * @param markup разметка кнопок
     */
    public void sendMessageWithKeyboard(Long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(markup);
        executeMessage(chatId, text, sendMessage);
    }
    public void executeMessage(Long chatId, String text, SendMessage sendMessage) {
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(Long chatId, String text) {
        executeMessage(chatId, text, new SendMessage());
    }
    @Override
    public String getBotUsername() {
        return configuration.getName();
    }

}