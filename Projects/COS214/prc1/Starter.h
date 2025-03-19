#ifndef STARTER_H
#define STARTER_H

#include <iostream>
#include <string>
#include "Course.h"

using namespace std;

class Starter: public Course{
    public:
        Starter(int maxNumberOfItems);
        void recommendBeverage();
};


#endif /*STARTER_H*/