#ifndef COURSE_H
#define COURSE_H

#include <string>
#include <vector>
#include "MenuItem.h"


using namespace std;

class Course {
    protected:
        string description;
        int maxNumberOfItems;
        vector<MenuItem*> menuItems;
    
    public:
        Course(string description, int maxNumberOfItems);

        virtual ~Course();

        bool addMenuItem(string description, float price, int stock);

        void printMenuItems();

        void printInventory();

        string getDescription();


        MenuItem* getMenuItem(int index) ;

        virtual void recommendBeverage() = 0;
};

#endif /*COURSE_H*/