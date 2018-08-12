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
{"refrigeratorList":[{"nameZh":"自家醃製小黃瓜","type":"其他","acquisitionDate":"20180810","expirationDate":"360"},{"nameZh":"大白菜","type":"青菜","acquisitionDate":"20180810","expirationDate":"7"},{"nameZh":"牛肉片","type":"肉","acquisitionDate":"20180810","expirationDate":"10"},{"nameZh":"義美紅豆冰","type":"冷凍食品","acquisitionDate":"20180810","expirationDate":"360"}]}
```

### Get the Items been added recently

```
curl -X GET "https://refrigerator-mgt-bot-backend.herokuapp.com/cabinet/{userId}/item_in_refrigerator"
```

Then, you would get the result like the following:

```
{"refrigeratorList":[{"nameZh":"牛奶","type":"飲料","acquisitionDate":"20180812","expirationDate":"8"},{"nameZh":"可樂","type":"飲料","acquisitionDate":"20180812","expirationDate":"413"},{"nameZh":"自家醃製小黃瓜","type":"其他","acquisitionDate":"20180810","expirationDate":"360"},{"nameZh":"大白菜","type":"青菜","acquisitionDate":"20180810","expirationDate":"7"},{"nameZh":"水梨","type":"水果","acquisitionDate":"20180812","expirationDate":"7"},{"nameZh":"冷凍水餃","type":"冷凍食品","acquisitionDate":"20180812","expirationDate":"720"},{"nameZh":"牛肉片","type":"肉","acquisitionDate":"20180810","expirationDate":"10"},{"nameZh":"義美紅豆冰","type":"冷凍食品","acquisitionDate":"20180810","expirationDate":"360"},{"nameZh":"生命之水","type":"飲料","acquisitionDate":"20180812","expirationDate":"1080"},{"nameZh":"味全糙米漿","type":"飲料","acquisitionDate":"20180812","expirationDate":"10"}]}
```

