#include "MainCourse.h"
#include <iostream>
#include <string>

using namespace std;

MainCourse::MainCourse(int maxNumberOfItems)
    : Course("Main", maxNumberOfItems) {}

void MainCourse::recommendBeverage() {
    cout << "Coke" << endl;
}