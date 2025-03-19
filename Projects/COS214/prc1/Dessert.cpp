#include "Dessert.h"
#include <iostream>
#include <string>

using namespace std;

Dessert::Dessert(int maxNumberOfItems)
    : Course("Dessert", maxNumberOfItems) {}

void Dessert::recommendBeverage() {
    cout << "Coffee" << endl;
}