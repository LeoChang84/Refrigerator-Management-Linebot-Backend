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
