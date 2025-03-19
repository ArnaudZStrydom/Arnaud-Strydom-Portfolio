#include "MenuItem.h"
#include <iostream>
#include <string>

using namespace std;

MenuItem::MenuItem(string description, float price, int stock)
    : description(description), price(price), stock(stock) {}

string MenuItem::getDescription() {
    return description;
}

float MenuItem::getPrice() {
    return price;
}

int MenuItem::getStock() {
    return stock;
}

void MenuItem::reduceStock() {
    if (stock > 0) {
        stock--;
    }
}