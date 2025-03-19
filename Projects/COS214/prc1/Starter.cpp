#include "Starter.h"
#include <iostream>
#include <string>

using namespace std;

Starter::Starter(int maxNumberOfItems)
    : Course("Starter", maxNumberOfItems) {}

void Starter::recommendBeverage() {
    cout << "Sparkling Water" << endl;
}