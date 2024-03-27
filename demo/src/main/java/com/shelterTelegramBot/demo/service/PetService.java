package com.shelterTelegramBot.demo.service;
import com.shelterTelegramBot.demo.entity.PetEntity;
import com.shelterTelegramBot.demo.repository.PetRepository;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shelterTelegramBot.demo.utils.ButtonsNames.*;


@Component
public class PetService {
    private final PetRepository petRepository;

    private final UtilsService utilsService;

    public PetService(PetRepository petRepository, UtilsService utilsService) {
        this.petRepository = petRepository;
        this.utilsService = utilsService;
    }

    public SendMessage getPetsOrRecommendationsMenu(String callbackData) {
        if (callbackData.equals(OUR_PETS_BUTTON_DATA)){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Наши питомцы");
            sendMessage.setReplyMarkup(setAllPetsMenu());
            return sendMessage;
            return ("Наши питомцы");
        }
        else {
            return setRecommendationsMenu();
        }
        }


    private InlineKeyboardMarkup setRecommendationsMenu() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.RULES_DATING_PETS_BUTTON_NAME, ButtonsNames.RULES_DATING_PETS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.DOCUMENTS_PETS_BUTTON_NAME, ButtonsNames.DOCUMENTS_PETS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.REFUSAL_TO_ISSUE_ANIMAL_BUTTON_NAME, ButtonsNames.REFUSAL_TO_ISSUE_ANIMAL_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.TRANSPORTATION_RECOMMENDATIONS_BUTTON_NAME, ButtonsNames.TRANSPORTATION_RECOMMENDATIONS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.RECOMMENDATION_HOUSE_BUTTON_NAME, ButtonsNames.RECOMMENDATION_HOUSE_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.RECOMMENDATIONS_DOG_HANDLER_BUTTON_NAME, ButtonsNames.RECOMMENDATIONS_DOG_HANDLER_BUTTON_DATA));
        return utilsService.setKeyboard(lists, 1);
    }

    /**
     * Меню отображения животных
     *
     * @param chatId ID чата
     * @param text   текст кнопок
     */
//    public void getPetMenu(Long chatId, String text) {
//        List<List<String>> lists = new ArrayList<>();
//        lists.add(List.of(ButtonsNames.PET_NAME_BUTTON_NAME, ButtonsNames.PET_NAME_BUTTON_DATA));
//        lists.add(List.of(ButtonsNames.PET_AGE_BUTTON_NAME, ButtonsNames.PET_AGE_BUTTON_DATA));
//        setKeyboard(chatId, text, lists, 1);
//    }

    /**
     * Метод вывода всех животных приюта
     * <br/>
     * По циклу <b>информация из репозитория БД</b> {@link PetRepository} преобразуется в сущность {@link PetEntity}, где затем кладется в коллекцию {@link List}
     * <br/>
     * В цикле путем добавления из БД информации, создается меню <b>кнопок</b> {@code setKeyboard(chatId, text, lists, 2);}
     *
     * @return
     */
    public InlineKeyboardMarkup setAllPetsMenu() {
        List<List<String>> lists = new ArrayList<>();
        for (PetEntity petEntity : petRepository.findAll()) {
            lists.add(List.of(petEntity.getBreed() + " " + " возраст: " + petEntity.getAge() + " года ", petEntity.getId() + "_PETS_BY_ID_" + "PET_GROUP_" + "BUTTON"));
        }
        return utilsService.setKeyboard(lists, 2);
    }

    /**
     * Метод в котором информация о питомце ищется путем резделения строки и поиску по информации ID {@code Long petId = Long.parseLong(callBackData.split("_")[0])}
     *
     * @param callBackData аргумент (нажатая кнопка)
     */
    public SendMessage getInfoByPetId(String callBackData) {
        Long petId = Long.parseLong(callBackData.split("_")[0]);
        Optional<PetEntity> pet = petRepository.findById(petId);
        SendMessage sendMessage = new SendMessage();
        if (pet.isPresent()) {
            if (callBackData.contains(ButtonsNames.PET_NAME_BUTTON_DATA)) {
                sendMessage.setText(pet.get().getName());
            } else if (callBackData.contains(ButtonsNames.PET_AGE_BUTTON_DATA)) {
                sendMessage.setText(String.valueOf(pet.get().getAge()));
            } else if (callBackData.contains(ButtonsNames.PET_BREED_BUTTON_DATA)) {
                sendMessage.setText(pet.get().getBreed());
            } else if (callBackData.contains(ButtonsNames.PET_COMMENT_BUTTON_DATA)) {
                sendMessage.setText(pet.get().getComment());
            }
        }
        return sendMessage;
    }

    /**
     * Метод создания кнопок
     *
     * @param petId  ID питомца
     */
    public SendMessage setPetInfoMenu(Long petId) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.PET_NAME_BUTTON_NAME, petId + "_" + ButtonsNames.PET_NAME_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_AGE_BUTTON_NAME, petId + "_" + ButtonsNames.PET_AGE_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_BREED_BUTTON_NAME, petId + "_" + ButtonsNames.PET_BREED_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_COMMENT_BUTTON_NAME, petId + "_" + ButtonsNames.PET_COMMENT_BUTTON_DATA));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Что хотите узнать? ");
        sendMessage.setReplyMarkup(utilsService.setKeyboard(lists, 1));
        return sendMessage;
    }

    public SendMessage getPetFromShelterMenu() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.RECOMMENDATIONS_BUTTON_NAME, ButtonsNames.RECOMMENDATIONS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.OUR_PETS_BUTTON_NAME, ButtonsNames.OUR_PETS_BUTTON_DATA));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Выберите пункт: ");
        sendMessage.setReplyMarkup(utilsService.setKeyboard(lists, 1));
        return sendMessage;
    }


}
