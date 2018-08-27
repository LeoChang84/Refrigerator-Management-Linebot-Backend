package service.controller;

import service.model.EasyExpired;
import service.model.Food;
import service.model.ExpirationDoc;
import service.data.ShoppingList;
import service.data.ShoppingItem;
import service.data.RecommendationList;
import service.data.RecommendationItem;
import service.data.AddedItem;
import service.data.RefrigeratorList;
import service.data.RefrigeratorItem;
import service.data.AddItemToShoppingList;
import service.repository.CabinetRepository;
import service.repository.EasyExpiredrepository;
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

import javax.swing.text.StyledEditorKit;
import java.io.BufferedOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.io.IOException;
import java.lang.Boolean;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/cabinet")
public class CabinetController {

	static private Logger logger = LoggerFactory.getLogger(CabinetController.class.getName());

    @Autowired
    CabinetRepository cabinetRepository;

    @Autowired
    ExpirationRepository expirationRepository;

    @Autowired
    EasyExpiredrepository easyExpiredrepository;

    @GetMapping(value = "/{userId}/shopping_list", produces = "application/json")
    public ResponseEntity<ShoppingList> getShoppingList(@PathVariable("userId") String userId) {
        logger.info("Start to get ShoppingList");
        List<Food> foods = cabinetRepository.findByStatus(1);
        if (null == foods || foods.isEmpty()) {
        	logger.info("Foods got nothing");
        	ShoppingList shoppingList = new ShoppingList(new ArrayList<>());
        	return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        }
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        for (Food food: foods) {
        	ShoppingItem shoppingItem = new ShoppingItem(food.getId(), food.getNameZh(), food.getType());
        	shoppingItems.add(shoppingItem);
        }
        logger.info("Successfully transform food info to shoppingItem");
        ShoppingList shoppingList = new ShoppingList(shoppingItems);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/add_item_to_shoppingist", produces = "application/json")
    public ResponseEntity<String> itemToShoppingList(@RequestBody String itemNameZh) throws JsonGenerationException ,JsonMappingException, IOException {
        logger.info("Start to post item to ShoppingList");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectMapper objectmapper = new ObjectMapper();
        AddItemToShoppingList addItemToShoppingList = objectmapper.readValue(itemNameZh, AddItemToShoppingList.class);

        logger.info("Parse input object successfully: " + itemNameZh);
        Food food = cabinetRepository.save(new Food(addItemToShoppingList.getNameZh(), addItemToShoppingList.getType(), null, null, 1, null, null, Boolean.TRUE, null));
        logger.info("Post item to shopping list.");
        return new ResponseEntity<>(food.getId(), HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/delete_item_from_shoppingist", produces = "application/json")
    public ResponseEntity<String> deleteItemfromShoppingList(@RequestBody String deleteItem) throws JsonGenerationException ,JsonMappingException, IOException {
        logger.info("Start to delete item from ShoppingList");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectMapper objectmapper = new ObjectMapper();
        ShoppingItem shoppingItem = objectmapper.readValue(deleteItem, ShoppingItem.class);
        logger.info("Parse input object successfully: " + shoppingItem);

        Food food = cabinetRepository.findOneById(shoppingItem.getId());
        food.setStatus(4);
        food = cabinetRepository.save(food);
        logger.info("Delete from db successfully.");
        String reply = "Delete item from shoppingList." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/recommendation_list", produces = "application/json")
    public ResponseEntity<RecommendationList> getRecommedationList(@PathVariable("userId") String userId) {
        logger.info(" Recommendation List");
        List<Food> foods = cabinetRepository.findByStatus(3);
        if (null == foods || foods.isEmpty()) {
        	RecommendationList recommendationList = new RecommendationList(new ArrayList<>());
        	return new ResponseEntity<>(recommendationList, HttpStatus.OK);
        }
        logger.info("Analysis eaten data");
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
        	if (!contains) {
        		recommendationItemsMap.put(key, 1);
        	} else {
        		Integer value = recommendationItemsMap.get(key);
        		recommendationItemsMap.put(key, value + 1);
        	}
        }
        List<RecommendationItem> recommendationItems = new ArrayList<>();
        for (Map.Entry<Pair, Integer> entry: recommendationItemsMap.entrySet()) {
        	RecommendationItem recommendationItem = new RecommendationItem(entry.getKey().getLeft(), entry.getKey().getRight(), entry.getValue());
        	System.out.println(entry.getKey().getLeft() + entry.getKey().getRight() + entry.getValue());
        	recommendationItems.add(recommendationItem);
        }
        logger.info("Got recommendationList");
        RecommendationList recommendationList = new RecommendationList(recommendationItems);
        return new ResponseEntity<>(recommendationList, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/buy", produces = "application/json")
    public ResponseEntity<String> postProuct(@RequestBody String buyList) throws JsonGenerationException ,JsonMappingException, IOException {
		logger.info("Buy item from shopping list");
        ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		ObjectMapper objectmapper = new ObjectMapper();
		List<ShoppingItem> shoppingItems = objectmapper.readValue(buyList, new TypeReference<List<ShoppingItem>>(){});
		logger.info("Get shopping item from shopping list" + shoppingItems);
		LocalDate now = LocalDate.now();

        for (ShoppingItem shoppingItem: shoppingItems) {
        	System.out.println("----parsing shoppingItem---");
        	ExpirationDoc expirationDoc = expirationRepository.findByNameZh(shoppingItem.getNameZh());
        	Food food = cabinetRepository.findOneById(shoppingItem.getId());
        	food.setAcquisitionDate(String.valueOf(now));
        	if( expirationDoc != null) {
                food.setExpirationDate(expirationDoc.getExpirationDate());
            } else {
                food.setExpirationDate("0");
            }
            food.setStatus(2);
        	food.setEatenBeforeExpired(null);
        	food.setNotify(Boolean.TRUE);
        	food.setFirstUse(Boolean.TRUE);
        	logger.info("food get type");
        	logger.info(food.getType());
            EasyExpired easyExpired = easyExpiredrepository.findOneByNameZh(food.getNameZh());
            if (easyExpired == null) {
                food.setEasyExpired(Boolean.FALSE);
            } else {
                food.setEasyExpired(Boolean.TRUE);
            }
//        	food = cabinetRepository.save(new Food(shoppingItem.getNameZh(), shoppingItem.getType(), String.valueOf(now), expirationDoc.getExpirationDate(), 2, null, Boolean.TRUE));
            food = cabinetRepository.save(food);
            logger.info("food info: " + food);
        }
        logger.info("save all element to db");
        String reply = "save to db." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/add_item", produces = "application/json")
    public ResponseEntity<String> postAddedItem(@RequestBody String item) throws JsonGenerationException ,JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    	logger.debug("Add item manually: " + item);
		ObjectMapper objectmapper = new ObjectMapper();
		AddedItem addedItem = objectmapper.readValue(item, AddedItem.class);
        logger.debug("Parsed: " + addedItem);

		LocalDate now = LocalDate.now();
        String expirationDate = calculateExpirationDate(now, LocalDate.parse(addedItem.getExpirationDate()));
        EasyExpired easyExpired = easyExpiredrepository.findOneByNameZh(addedItem.getNameZh());
        Boolean flag = Boolean.FALSE;
        if (easyExpired != null) {
            flag = Boolean.TRUE;
        }
    	Food food = cabinetRepository.save(new Food(addedItem.getNameZh(), addedItem.getType(), addedItem.getAcquisitionDate(), expirationDate, 2, null, Boolean.TRUE, Boolean.TRUE, flag));

        logger.debug("Add successfully.");
        String reply = "save to db." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

//	@GetMapping(value = "/{userId}/recently_added", produces = "application/json")
//    public ResponseEntity<RefrigeratorList> getRecentlyAdded(@PathVariable("userId") String userId) {
//        System.out.println("-----GetRecentlyAdded-----");
//        System.out.println("-----before fetch-----");
//
//		LocalDate now = LocalDate.now();
//		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//		// String formattedString = localDate.format(formatter);
//
//        // List<Food> foods = cabinetRepository.findByAcquisitionDateAndStatus(formattedString ,2);
//        List<Food> foods = cabinetRepository.findByAcquisitionDateAndStatus("2018-08-12" ,2);
//
//        if (null == foods || foods.isEmpty()) {
//        	System.out.println("-----foods got nothing.------");
//        	RefrigeratorList refrigeratorList = new RefrigeratorList(new ArrayList<>());
//        	return new ResponseEntity<>(refrigeratorList, HttpStatus.OK);
//        }
//        System.out.println("------after fetch-----");
//        List<RefrigeratorItem> refrigeratorItems = new ArrayList<>();
//        for (Food food: foods) {
//        	RefrigeratorItem refrigeratorItem = new RefrigeratorItem(food.getId() ,food.getNameZh(), food.getType(), food.getAcquisitionDate(), food.getExpirationDate(), food.getNotify());
//        	refrigeratorItems.add(refrigeratorItem);
//        }
//        RefrigeratorList refrigeratorList = new RefrigeratorList(refrigeratorItems);
//        return new ResponseEntity<>(refrigeratorList, HttpStatus.OK);
//    }

	@GetMapping(value = "/{userId}/item_in_refrigerator", produces = "application/json")
    public ResponseEntity<RefrigeratorList> getItemInRefrigerator(@PathVariable("userId") String userId) {
        logger.info("Get Item in Refrigerator");

        List<Food> foods = cabinetRepository.findByStatus(2);

        if (null == foods || foods.isEmpty()) {
            logger.info("Foods got nothing");
        	RefrigeratorList refrigeratorList = new RefrigeratorList(new ArrayList<>());
        	return new ResponseEntity<>(refrigeratorList, HttpStatus.OK);
        }
        List<RefrigeratorItem> refrigeratorItems = new ArrayList<>();
        for (Food food: foods) {
        	RefrigeratorItem refrigeratorItem = new RefrigeratorItem(food.getId(), food.getNameZh(), food.getType(), food.getAcquisitionDate(), food.getExpirationDate(), food.getNotify(), food.getFirstUse(), food.getEasyExpired());
        	refrigeratorItems.add(refrigeratorItem);
        }
        logger.debug("Get all from refrigerator");
        RefrigeratorList refrigeratorList = new RefrigeratorList(refrigeratorItems);
        return new ResponseEntity<>(refrigeratorList, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/edit_item", produces = "application/json")
    public ResponseEntity<String> posteditedItem(@RequestBody String item) throws JsonGenerationException ,JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    	System.out.println("-----start parsing-----: " + item);
		ObjectMapper objectmapper = new ObjectMapper();
		RefrigeratorItem editedItem = objectmapper.readValue(item, RefrigeratorItem.class);
		System.out.println("--parse--: " + editedItem.getId() + " " + editedItem.getType() + " " + editedItem.getAcquisitionDate() + " " + editedItem.getExpirationDate());

        Food food = cabinetRepository.findOneById(editedItem.getId());
        if (food == null) { System.out.println(">>>>>>>>>> food doesn's not find <<<<<<<<<"); }
        System.out.println("-------" + food + "------");
        System.out.println("-----before save-----" + " " + editedItem.getAcquisitionDate() + " " + editedItem.getExpirationDate());

        food.setNameZh(editedItem.getNameZh());
        food.setType(editedItem.getType());
        food.setFirstUse(editedItem.getFirstUse());
        food.setEasyExpired(editedItem.getEasyExpired());
        String expirationDate = calculateExpirationDate(LocalDate.parse(editedItem.getAcquisitionDate()), LocalDate.parse(editedItem.getExpirationDate()));
        food.setExpirationDate(expirationDate);

	    Food foodUpdate = cabinetRepository.save(food);

        System.out.println("-----after save-----" + foodUpdate);
        String reply = "Edited has been saved to db." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/eaten", produces = "application/json")
    public ResponseEntity<String> hasEaten(@RequestBody String item) throws JsonGenerationException ,JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("-----start parsing-----: " + item);
        ObjectMapper objectmapper = new ObjectMapper();
        RefrigeratorItem editedItem = objectmapper.readValue(item, RefrigeratorItem.class);
        // System.out.println("--parse--: " + editedItem.getId() + " " + editedItem.getType() + " " + editedItem.getAcquisitionDate() + " " + editedItem.getExpirationDate());

        Food food = cabinetRepository.findOneById(editedItem.getId());
        if (food == null) { System.out.println(">>>>>>>>>> food doesn's not find <<<<<<<<<"); }
        System.out.println("-------" + food + "------");
        // System.out.println("-----before save-----" + " " + editedItem.getAcquisitionDate() + " " + editedItem.getExpirationDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Integer expirationOrNot = Integer.valueOf(calculateExpirationDate(LocalDate.now(), LocalDate.parse(food.getAcquisitionDate()).plusDays(Integer.valueOf(food.getExpirationDate()))));
        
        Boolean expirationBoolean = Boolean.TRUE; 
        if (expirationOrNot <  0) { expirationBoolean = Boolean.FALSE;} 
        food.setStatus(3);
        food.setEatenBeforeExpired(expirationBoolean);
        food.setNotify(Boolean.FALSE);

        Food foodUpdate = cabinetRepository.save(food);

        System.out.println("-----after save-----" + foodUpdate);
        String reply = "Status has been set eaten." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/unnotify", produces = "application/json")
    public ResponseEntity<String> notNotify(@RequestBody String item) throws JsonGenerationException ,JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println("-----start parsing-----: " + item);
        ObjectMapper objectmapper = new ObjectMapper();
        RefrigeratorItem editedItem = objectmapper.readValue(item, RefrigeratorItem.class);

        Food food = cabinetRepository.findOneById(editedItem.getId());
        if (food == null) { System.out.println(">>>>>>>>>> food doesn's not find <<<<<<<<<"); }
        System.out.println("-------" + food + "------");
        food.setNotify(Boolean.FALSE);
        Food foodUpdate = cabinetRepository.save(food);
        System.out.println("-----after save-----" + foodUpdate);
        String reply = "Notify has been turned off." ;
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }


    public String calculateExpirationDate(LocalDate now, LocalDate expirationDate) {
    	return String.valueOf(ChronoUnit.DAYS.between(now, expirationDate));
    }
}