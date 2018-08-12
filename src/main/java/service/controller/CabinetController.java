package service.controller;

import service.model.Food;
import service.model.ExpirationDoc;
import service.data.ShoppingList;
import service.data.ShoppingItem;
import service.data.RecommendationList;
import service.data.RecommendationItem;
import service.repository.CabinetRepository;
import service.repository.ExpirationRepository;
import service.util.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.lang.Boolean;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping(value = "/cabinet")
public class CabinetController {

	// static private Logger logger = LoggerFactory.getLogger(CabinetController.class.getName()); 

    @Autowired
    CabinetRepository cabinetRepository;

    @Autowired
    ExpirationRepository expirationRepository;

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

    @GetMapping(value = "/{userId}/recommendation_list", produces = "application/json")
    public ResponseEntity<RecommendationList> GetRecommedationList(@PathVariable("userId") String userId) {
        System.out.println("-----RecommendationList-----");
        System.out.println("-----before fetch-----");
        List<Food> foods = cabinetRepository.findByStatus(3);
        if (null == foods || foods.isEmpty()) {
        	System.out.println("-----foods got nothing.------");
        	RecommendationList recommendationList = new RecommendationList(new ArrayList<>());
        	return new ResponseEntity<>(recommendationList, HttpStatus.OK);
        }
        System.out.println("------after fetch-----");

        Map<Pair, Integer> recommendationItemsMap = new HashMap<Pair, Integer>();
        for (Food food: foods) {	
    		Pair key = new Pair(food.getNameZh(), food.getType());
        	Boolean contains = Boolean.FALSE;
        	for (Map.Entry<Pair, Integer> entry: recommendationItemsMap.entrySet()) {
        		if (entry.getKey().equals(new Pair(food.getNameZh(), food.getType()))) {
        			key = entry.getKey();
        			contains = Boolean.TRUE;
        			break;
        		}
        	}
        	// System.out.println(contains);
        	if (!contains) {
        		recommendationItemsMap.put(key, 1);
        	} else {
        		Integer value = recommendationItemsMap.get(key);
        		recommendationItemsMap.put(key, value + 1);
        	}
        	// System.out.println(key.getLeft() + key.getRight() + recommendationItemsMap.get(key));
        }
        List<RecommendationItem> recommendationItems = new ArrayList<>();
        for (Map.Entry<Pair, Integer> entry: recommendationItemsMap.entrySet()) {
        	RecommendationItem recommendationItem = new RecommendationItem(entry.getKey().getLeft(), entry.getKey().getRight(), entry.getValue());
        	System.out.println(entry.getKey().getLeft() + entry.getKey().getRight() + entry.getValue());
        	recommendationItems.add(recommendationItem);
        }

        RecommendationList recommendationList = new RecommendationList(recommendationItems);
        return new ResponseEntity<>(recommendationList, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/buy", produces = "application/json")
    public ResponseEntity<String> PostProuct(@RequestBody String buyList) throws JsonGenerationException ,JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		ObjectMapper objectmapper = new ObjectMapper();
		List<ShoppingItem> shoppingItems = objectmapper.readValue(buyList, new TypeReference<List<ShoppingItem>>(){});
		System.out.println("--parse--: " + shoppingItems);

		LocalDate localDate = LocalDate.now();//For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedString = localDate.format(formatter);

        System.out.println("-----before save-----" + formattedString);
        for (ShoppingItem shoppingItem: shoppingItems) {
        	System.out.println("----parsing shoppingItem---");
        	ExpirationDoc expirationDoc = expirationRepository.findByNameZh(shoppingItem.getNameZh());
        	System.out.println("---ExpirationDoc: " + expirationDoc);
        	Food food = cabinetRepository.save(new Food(shoppingItem.getNameZh(), shoppingItem.getType(), formattedString, expirationDoc.getExpirationDate(), 2, null, Boolean.TRUE));
        	System.out.println(food.getNameZh() + " " + food.getType() + " " + food.getAcquisitionDate() + " " + food.getExpirationDate() + " " + food.getStatus() + " " + food.getEatenBeforeExpired() + " " + food.getNotify());
        }
        System.out.println("-----after save-----");
        String reply = "save to db." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

}