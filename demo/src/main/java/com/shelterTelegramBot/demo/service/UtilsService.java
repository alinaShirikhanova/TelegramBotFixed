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

}
