package service.controller;

import service.model.Food;
import service.model.Foods;
import service.repository.ShoppingListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.lang.Boolean;
import java.util.List;

@RestController
@RequestMapping(value = "/cabinet")
public class CabinetController {

    @Autowired
    ShoppingListRepository shoppingListRepository;

    @GetMapping(value = "/{userId}/shopping_list", produces = "application/json")
    public ResponseEntity<Foods> GetShoppingList(@PathVariable("userId") String userId) {
        System.out.println("-----before fetch-----");
        List<Foods> foodss = shoppingListRepository.findAll();
        if (null == foodss.get(0)) {
        	System.out.println("foods got nothing.");
        }
        System.out.println("------after fetch-----");
        Foods foods = foodss.get(0);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/recommedation_list", produces = "application/json")
    public ResponseEntity<Foods> GetRecommedationList(@PathVariable("userId") String userId) {
        System.out.println("-----before fetch-----");
        List<Foods> foodss = shoppingListRepository.findByStatus(2);
        if (null == foodss.get(0)) {
        	System.out.println("foods got nothing.");
        }
        System.out.println("------after fetch-----");
        Foods foods = foodss.get(0);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/buy", produces = "application/json")
    public ResponseEntity<Foods> PostProuct(@PathVariable("userId") String userId) {
        System.out.println("-----before fetch-----");
        // status 1 = 在冰箱
        Foods foods =  shoppingListRepository.save(new Foods("紅酒", "酒", "20180804", "30", 1, Boolean.FALSE, Boolean.TRUE));
        System.out.println("-----before fetch-----");
        // Foods foods = new Foods("紅酒", "酒", "20180804", "30", 1, Boolean.FALSE, Boolean.TRUE);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}