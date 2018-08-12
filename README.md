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