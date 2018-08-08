package service.controller;

import service.model.Food;
import service.repository.ShoppingListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping(value = "/food")
public class FoodController {

    @Autowired
    ShoppingListRepository shoppingListRepository;

    @GetMapping(value = "/{userId}/shopping_list", produces = "application/json")
    // @RequestMapping(value = "/test")
    public ResponseEntity<Food> GetBusTimeline(@PathVariable("userId") String userId) {
        System.out.println("-----before fetch-----");
        Food food = shoppingListRepository.findByNameZh("高麗菜");
        System.out.println("------after fetch-----");
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}