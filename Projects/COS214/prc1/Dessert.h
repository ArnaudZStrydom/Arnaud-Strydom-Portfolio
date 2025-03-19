#ifndef DESSERT_H
#define DESSERT_H

#include <iostream>
#include <string>
#include "Course.h"

using namespace std;

class Dessert: public Course{
    public:
        Dessert(int maxNumberOfItems);
        void recommendBeverage();
};


#endif /*DESSERT_H*/