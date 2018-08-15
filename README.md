# Refrigerator-Management-Linebot-Backend

This project provides multiple APIs for accessing database.

## Getting Started

The following instruction will lead you how to use these APIs, whether you do it locally or remotely.

### Prerequisites

Install Java 8

```
brew tap caskroom/versions
```

```
brew cask install java8
```

## Using the APIs

The following will show the APIs we provided, and let you know how to try it.

### Hello World

```
curl -X GET "https://refrigerator-mgt-bot-backend.herokuapp.com/"
```

### Get the shopping list

```
curl -X GET "https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/{userId}/shopping_list"
```

Then, you would get the result like the following:

```
{"shoppingItems":[{"nameZh":"義美紅豆包","type":"冷凍食品"},{"nameZh":"菠菜","type":"青菜"},{"nameZh":"小黃瓜","type":"青菜"},{"nameZh":"優酪乳","type":"飲料"},{"nameZh":"鮭魚片","type":"海鮮"},{"nameZh":"雞腿肉","type":"肉"},{"nameZh":"雞腿肉","type":"肉"},{"nameZh":"雞腿肉","type":"肉"},{"nameZh":"台灣啤酒","type":"飲料"},{"nameZh":"優酪乳","type":"飲料"}]}
```

### Get the recommendation list

```
curl -X GET "https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/{userId}/recommendation_list"
```

Then, you would get the result like the following:

```
{"recommendationList":[{"nameZh":"杜老爺巧克力雪糕","type":"甜點","quantity":3},{"nameZh":"青江菜","type":"菜","quantity":2},{"nameZh":"生鮮干貝","type":"海鮮","quantity":1},{"nameZh":"高麗菜","type":"菜","quantity":3},{"nameZh":"火龍果","type":"水果","quantity":2}]}
```

### Post the shopping list item

```
curl --header "Content-Type: application/json"  --request POST  --data '{"nameZh":"牛奶"}' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/add_item_to_shoppingist
```

Then, you would get the result like the following:

```
Add item to shoppingList.%
```

### Delete item from the shopping list

```
curl --header "Content-Type: application/json"  --request POST  --data '{"id":"5b6fb35b08f3e000045888b1", "type": "飲料", "nameZh":"牛奶"}' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/add_item_to_shoppingist
```

Then, you would get the result like the following:

```
Delete item from shoppingList.%
```

### Buy the shopping list's item

```
curl --header "Content-Type: application/json"  --request POST  --data '[{"nameZh":"牛奶","type":"飲料"}]' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/buy
```

Then, you would get the result like the following:

```
save to db.%
```

### Post the adding item manually

```
curl --header "Content-Type: application/json" --request POST --data '{"nameZh":"可樂","type":"飲料","expirationDate":"2019-09-29"}' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/add_item
```

Then, you would get the result like the following:

```
save to db.%
```

### Get the Items been added recently

```
curl -X GET "https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/{userId}/recently_added"
```

Then, you would get the result like the following:

```
{"refrigeratorList":[{"id":"5b6fd8e358152a0004f0a88e","nameZh":"可樂","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"413"},{"id":"5b6fb35b08f3e111045888b1","nameZh":"水梨","type":"水果","acquisitionDate":"2018-08-12","expirationDate":"7"},{"id":"5b6fb35b08f3e111145888b1","nameZh":"冷凍水餃","type":"冷凍食品","acquisitionDate":"2018-08-12","expirationDate":"720"},{"id":"5b6fb35b18f3e211245888b1","nameZh":"生命之水","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"1080"},{"id":"5b6fb35b18f3e311245888b1","nameZh":"味全糙米漿","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"10"}]}
```

### Get all items in refrigerator

```
curl -X GET "https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/{userId}/item_in_refrigerator"
```

Then, you would get the result like the following:

```
{"refrigeratorList":[{"id":"5b6fb35b08f3e000045888b1","nameZh":"牛奶","type":"飲料","acquisitionDate":"2018-08-09","expirationDate":"7"},{"id":"5b6fd8e358152a0004f0a88e","nameZh":"可樂","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"413"},{"id":"5b6fb35b08f3e100045888b1","nameZh":"自家醃製小黃瓜","type":"其他","acquisitionDate":"2018-08-10","expirationDate":"360"},{"id":"5b6fb35b08f3e110045888b1","nameZh":"大白菜","type":"青菜","acquisitionDate":"2018-08-10","expirationDate":"7"},{"id":"5b6fb35b08f3e111045888b1","nameZh":"水梨","type":"水果","acquisitionDate":"2018-08-12","expirationDate":"7"},{"id":"5b6fb35b08f3e111145888b1","nameZh":"冷凍水餃","type":"冷凍食品","acquisitionDate":"2018-08-12","expirationDate":"720"},{"id":"5b6fb35b08f3e111245888b1","nameZh":"牛肉片","type":"肉","acquisitionDate":"2018-08-10","expirationDate":"10"},{"id":"5b6fb35b08f3e211245888b1","nameZh":"義美紅豆冰","type":"冷凍食品","acquisitionDate":"2018-08-10","expirationDate":"360"},{"id":"5b6fb35b18f3e211245888b1","nameZh":"生命之水","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"1080"},{"id":"5b6fb35b18f3e311245888b1","nameZh":"味全糙米漿","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"10"}]}%
```

### Update selected item's information in refrigerator

```
curl --header "Content-Type: application/json" --request POST --data '{"id":"5b6fb35b08f3e000045888b1","nameZh":"牛奶","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"2019-12-12"}' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/edit_item
```

Then, you would get the result like the following:

```
Edited has been saved to db.%
```

### Update item's status as eaten.

```
curl --header "Content-Type: application/json" --request POST --data '{"id":"5b6fb35b08f3e000045888b1","nameZh":"牛奶","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"7"}' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/eaten
```

Then, you would get the result like the following:

```
Edited has been saved to db.%
```

### Turn off item's notification in refrigerator

```
curl --header "Content-Type: application/json" --request POST --data '{"id":"5b6fb35b08f3e000045888b1","nameZh":"牛奶","type":"飲料","acquisitionDate":"2018-08-12","expirationDate":"2019-12-12"}' https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/123/unnotify
```

Then, you would get the result like the following:

```
Notify has been turned off..%
```
