package ru.otus.highload.hw.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.highload.hw.controller.api.dto.ListDataHolder;
import ru.otus.highload.hw.dao.CityMapper;
import ru.otus.highload.hw.model.City;

import java.util.List;

/**
 * Контроллер справочной информации
 */
@RestController
@RequiredArgsConstructor
public class DirectoriesController {

    private final CityMapper cityMapper;

    /**
     * Список доступных городов
     * @return
     */
    @GetMapping("/api/v1/directories/cities")
    public ResponseEntity<ListDataHolder<City>> findAllCities() {
        List<City> cities = cityMapper.findAll();
        return ResponseEntity.ok(new ListDataHolder<>(cities));
    }
}
