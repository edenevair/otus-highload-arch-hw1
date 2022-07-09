package ru.otus.highload.hw.config.flyway;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.hw.dao.UserMapper;
import ru.otus.highload.hw.model.City;
import ru.otus.highload.hw.model.Gender;
import ru.otus.highload.hw.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseUsersDataPopulateCallback implements Callback {

    public static final String COMMA_DELIMITER = ";";
    public final UserMapper userMapper;

    @Override
    public boolean supports(Event event, Context context) {
        return event == Event.AFTER_VALIDATE;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @SneakyThrows
    @Transactional
    @Override
    public void handle(Event event, Context context) {
        File russianNames = new ClassPathResource("init_data/russian_names.csv").getFile();
        File russianSurnames = new ClassPathResource("init_data/russian_surnames.csv").getFile();

        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(russianNames))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                names.add(values[1]);
            }
        }

        List<String> surnames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(russianSurnames))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                surnames.add(values[1]);
            }
        }
        Random rand = new Random();
        for (int i = 0; i < 1_000_000; i++) {
            User user = User.builder()
                    .firstName(names.get(rand.nextInt(names.size())))
                    .lastName(surnames.get(rand.nextInt(surnames.size())))
                    .city(new City(1L, "1"))
                    .interests("Test data")
                    .gender(Gender.values()[rand.nextInt(2)])
                    .age(18)
                    .build();

            userMapper.insert(user);
            if (i%100 == 0) {
                log.info("Users count = " + i);
            }
        }
    }

    @Override
    public String getCallbackName() {
        return DatabaseUsersDataPopulateCallback.class.getSimpleName();
    }
}
