package com.shelterTelegramBot.demo.service;
import com.shelterTelegramBot.demo.entity.PetEntity;
import com.shelterTelegramBot.demo.repository.PetRepository;
import com.shelterTelegramBot.demo.utils.ButtonsNames;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.shelterTelegramBot.demo.controller.TelegramBotController.*;
@Component
public class GetPetService {
    private final PetRepository petRepository;

    public GetPetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void getPetsOrRecommendationsMenu(Long chatId, String callbackData) {
        if (callbackData.equals(ButtonsNames.OUR_PETS_BUTTON_DATA)){
            setAllPetsMenu(chatId, "Наши питомцы");
        }
        else {
            setRecommendationsMenu(chatId, "Выберите рекоммендацию: ");
        }
    }

    /**
     * Меню отображения животных
     *
     * @param chatId ID чата
     * @param text   текст кнопок
     */
    public void getPetMenu(Long chatId, String text) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.PET_NAME_BUTTON_NAME, ButtonsNames.PET_NAME_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_AGE_BUTTON_NAME, ButtonsNames.PET_AGE_BUTTON_DATA));
        setKeyboard(chatId, text, lists, 1);
    }

    /**
     * Метод вывода всех животных приюта
     * <br/>
     * По циклу <b>информация из репозитория БД</b> {@link PetRepository} преобразуется в сущность {@link PetEntity}, где затем кладется в коллекцию {@link List}
     * <br/>
     * В цикле путем добавления из БД информации, создается меню <b>кнопок</b> {@code setKeyboard(chatId, text, lists, 2);}
     *
     * @param chatId ID чата
     * @param text   текст кнопок с информацией о питомце
     */
    public void setAllPetsMenu(Long chatId, String text) {
        List<List<String>> lists = new ArrayList<>();
        for (PetEntity petEntity : petRepository.findAll()) {
            lists.add(List.of(petEntity.getBreed() + " " + " возраст: " + petEntity.getAge() + " года ", petEntity.getId() + "_PETS_BY_ID_" + "BUTTON"));
        }
        setKeyboard(chatId, text, lists, 2);
    }

    /**
     * Метод в котором информация о питомце ищется путем резделения строки и поиску по информации ID {@code Long petId = Long.parseLong(callBackData.split("_")[0])}
     *
     * @param chatId       ID чата
     * @param callBackData аргумент (нажатая кнопка)
     */
    public void getInfoByPetId(Long chatId, String callBackData) {
        Long petId = Long.parseLong(callBackData.split("_")[0]);
//        System.out.println(petId.getClass().getName());
        Optional<PetEntity> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            if (callBackData.contains(ButtonsNames.PET_NAME_BUTTON_DATA)) {
                sendMessage(chatId, pet.get().getName());
            } else if (callBackData.contains(ButtonsNames.PET_AGE_BUTTON_DATA)) {
                sendMessage(chatId, String.valueOf(pet.get().getAge()));
            } else if (callBackData.contains(ButtonsNames.PET_BREED_BUTTON_DATA)) {
                sendMessage(chatId, pet.get().getBreed());
            } else if (callBackData.contains(ButtonsNames.PET_COMMENT_BUTTON_DATA)) {
                sendMessage(chatId, pet.get().getComment());
            }
        }
    }

    /**
     * Метод создания кнопок
     *
     * @param chatId ID чата
     * @param text   текст информации о питомце
     * @param petId  ID питомца
     */
    public void setPetInfoMenu(Long chatId, String text, Long petId) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.PET_NAME_BUTTON_NAME, petId + "_" + ButtonsNames.PET_NAME_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_AGE_BUTTON_NAME, petId + "_" + ButtonsNames.PET_AGE_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_BREED_BUTTON_NAME, petId + "_" + ButtonsNames.PET_BREED_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.PET_COMMENT_BUTTON_NAME, petId + "_" + ButtonsNames.PET_COMMENT_BUTTON_DATA));
        setKeyboard(chatId, text, lists, 2);
    }

    public void getPetFromShelterMenu(Long chatId, String text) {
        List<List<String>> lists = new ArrayList<>();
        lists.add(List.of(ButtonsNames.RECOMMENDATIONS_BUTTON_NAME, ButtonsNames.RECOMMENDATIONS_BUTTON_DATA));
        lists.add(List.of(ButtonsNames.OUR_PETS_BUTTON_NAME, ButtonsNames.OUR_PETS_BUTTON_DATA));
        setKeyboard(chatId, text, lists, 1);
    }


}
