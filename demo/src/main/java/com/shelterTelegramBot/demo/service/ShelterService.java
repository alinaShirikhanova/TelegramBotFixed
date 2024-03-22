package com.shelterTelegramBot.demo.service;

import com.shelterTelegramBot.demo.entity.ShelterEntity;
import com.shelterTelegramBot.demo.repository.ShelterRepository;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShelterService {

    private final UtilsService utilsService;

    private final ShelterRepository shelterRepository;

    public ShelterService(UtilsService utilsService, ShelterRepository shelterRepository) {
        this.utilsService = utilsService;
        this.shelterRepository = shelterRepository;
    }


//    /**
//     * Метод в котором информация о приюте ищется путем резделения строки и поиску по информации ID {@code Long petId = Long.parseLong(callBackData.split("_")[0])}
//     *
//     * @param chatId       ID чата
//     * @param callBackData аргумент (нажатая кнопка)
//     */
//    public void getInfoByShelterId(Long chatId, String callBackData) {
//        Long shelterId = Long.parseLong(callBackData.split("_")[0]);
//        Optional<ShelterEntity> shelter = shelterRepository.findById(shelterId);
//        if (shelter.isPresent()) {
//            if (callBackData.contains(ButtonsNames.SHELTER_GROUP_SCHEDULE_BUTTON_DATA)) {
//                sendMessage(chatId, shelter.get().getSchedule());
//            } else if (callBackData.contains(ButtonsNames.SHELTER_GROUP_DRIVING_DIRECTIONS_BUTTON_DATA)) {
//                sendMessage(chatId, shelter.get().getDrivingDirections());
//            } else if (callBackData.contains(ButtonsNames.SHELTER_GROUP_GUARD_DETAILS_BUTTON_DATA)) {
//                sendMessage(chatId, shelter.get().getGuardDetails());
//            } else if (callBackData.contains(ButtonsNames.SHELTER_GROUP_SAFETY_PRECAUTIONS_BUTTON_DATA)) {
//                sendMessage(chatId, shelter.get().getSafetyPrecautions());
//            }
//        }
//    }

    /**
     * Метод вывода всех животных приюта
     * <br/>
     * По циклу <b>информация из репозитория БД</b> {@link ShelterRepository} преобразуется в сущность {@link ShelterEntity}, где затем кладется в коллекцию {@link List}
     * <br/>
     * В цикле путем добавления из БД информации, создается меню <b>кнопок</b> {@code setKeyboard(chatId, text, lists, 1);}
     *
     */
    public SendMessage setSheltersMenuBot() {
        List<List<String>> lists = new ArrayList<>();
        for (ShelterEntity shelterEntity : shelterRepository.findAll()) {
            lists.add(List.of(shelterEntity.getName(), shelterEntity.getId() + "_SHELTERS_" + "BUTTON"));
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Приюты:");
        sendMessage.setReplyMarkup(utilsService.setKeyboard(lists, 1));
        return sendMessage;
    }

    /**
     * Метод создания кнопок
     *
     * @param shelterId ID приюта
     */
    public SendMessage setShelterInfoMenu(Long shelterId) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.SCHEDULE_BUTTON_NAME, shelterId + "_" + ButtonsNames.SHELTER_GROUP_SCHEDULE_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.DRIVING_DIRECTIONS_BUTTON_NAME, shelterId + "_" + ButtonsNames.SHELTER_GROUP_DRIVING_DIRECTIONS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.GUARD_DETAILS_BUTTON_NAME, shelterId + "_" + ButtonsNames.SHELTER_GROUP_GUARD_DETAILS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.SAFETY_PRECAUTIONS_BUTTON_NAME, shelterId + "_" + ButtonsNames.SHELTER_GROUP_SAFETY_PRECAUTIONS_BUTTON_DATA));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Что вы хотите узнать?");
        sendMessage.setReplyMarkup(utilsService.setKeyboard(lists, 2));
        return sendMessage;
    }



}
