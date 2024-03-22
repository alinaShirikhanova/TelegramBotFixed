package com.shelterTelegramBot.demo.controller;

import com.shelterTelegramBot.demo.configuration.BotConfiguration;
import com.shelterTelegramBot.demo.entity.UserEntity;
import com.shelterTelegramBot.demo.repository.*;
import com.shelterTelegramBot.demo.repository.PetRepository;
import com.shelterTelegramBot.demo.service.UtilsService;
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

    private final UtilsService utilsService;

    private final PetController petController;

    private final ShelterController shelterInfoController;

    public TelegramBotController(UserRepository userRepository,
                                 BotConfiguration configuration,
                                 ShelterRepository shelterRepository,
                                 PetRepository petRepository,
                                 RuleRepository ruleRepository, UtilsService utilsService, PetController petController, ShelterController shelterInfoController) {
        super(configuration.getToken());
        this.userRepository = userRepository;
        this.configuration = configuration;
        this.shelterRepository = shelterRepository;
        this.petRepository = petRepository;
        this.ruleRepository = ruleRepository;
        this.utilsService = utilsService;
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
//            if (userRepository.findByChatId(chatId).isPresent()) {
//                sendMessage(chatId, "И снова здравствуйте! " + userEntity.getName());
//            } else {
//                sendMessage(chatId, "Здравствуйте! " + userEntity.getName());
//                userRepository.save(userEntity);
//            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(setStarMenuBot());
            executeMessage(chatId, sendMessage);

        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callBackData.contains("SHELTER_GROUP"))
            {
                executeMessage(chatId, shelterInfoController.executeCommand(callBackData));
            }
//            if (callBackData.contains("PET_GROUP")) {
//                petController.executePets(callBackData, chatId);
//            }
        }
    }
    /**
     * Метод создания стартового меню с кнопками
     *
     */
    //ПЕРЕНЕСТИ В СЕРВИС
    private InlineKeyboardMarkup setStarMenuBot() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.INFO_ABOUT_SHELTER_BUTTON_NAME, ButtonsNames.SHELTER_GROUP_INFO_ABOUT_SHELTER_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_NAME, ButtonsNames.GET_PET_FROM_SHELTER_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.SEND_REPORT_PETS_BUTTON_NAME, ButtonsNames.SEND_REPORT_PETS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.CALL_VOLUNTEER_BUTTON_NAME, ButtonsNames.CALL_VOLUNTEER_BUTTON_DATA));
        return utilsService.setKeyboard(lists, 2);
    }

    public void executeMessage(Long chatId,  SendMessage sendMessage) {
        sendMessage.setChatId(chatId);
        sendMessage.setText(" 23123");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getBotUsername() {
        return configuration.getName();
    }

}