package service.controller;

import service.model.Food;
import service.data.ShoppingList;
import service.data.ShoppingItem;
import service.repository.CabinetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

import java.lang.Boolean;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/cabinet")
public class CabinetController {

	// static private Logger logger = LoggerFactory.getLogger(CabinetController.class.getName()); 

    @Autowired
    CabinetRepository cabinetRepository;

    @GetMapping(value = "/{userId}/shopping_list", produces = "application/json")
    public ResponseEntity<ShoppingList> GetShoppingList(@PathVariable("userId") String userId) {
        System.out.println("-----GetShoppingList-----");
        System.out.println("-----before fetch-----");
        List<Food> foods = cabinetRepository.findByStatus(1);
        if (null == foods || foods.isEmpty()) {
        	System.out.println("-----foods got nothing.------");
        	ShoppingList shoppingList = new ShoppingList(new ArrayList<>());
        	return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        }
        System.out.println("------after fetch-----");
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        for (Food food: foods) {
        	ShoppingItem shoppingItem = new ShoppingItem(food.getNameZh(), food.getType());
        	shoppingItems.add(shoppingItem);
        }
        ShoppingList shoppingList = new ShoppingList(shoppingItems);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/recommedation_list", produces = "application/json")
    public ResponseEntity<Food> GetRecommedationList(@PathVariable("userId") String userId) {
        System.out.println("-----before fetch-----");
        List<Food> foods = cabinetRepository.findByStatus(2);
        if (null == foods.get(0)) {
        	System.out.println("foods got nothing.");
        }
        System.out.println("------after fetch-----");
        Food food = foods.get(0);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/buy", produces = "application/json")
    public ResponseEntity<Food> PostProuct(@PathVariable("userId") String userId) {
        System.out.println("-----before fetch-----");
        // status 1 = 在冰箱
        Food food =  cabinetRepository.save(new Food("紅酒", "酒", "20180804", "30", 1, Boolean.FALSE, Boolean.TRUE));
        System.out.println("-----before fetch-----");
        // Foods foods = new Foods("紅酒", "酒", "20180804", "30", 1, Boolean.FALSE, Boolean.TRUE);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}