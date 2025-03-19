#ifndef MENU_H
#define MENU_H

#include <string>
#include <map>
#include "Course.h"
#include "Starter.h"
#include "MainCourse.h"
#include "Dessert.h"

using namespace std;

class Menu {
    private:
        map<string,Course*> courses;
    public:
    Menu();

    ~Menu();

    bool addCourse(Course* course);

    bool addMenuItem(string courseDescription, string description, float price,int stock);

    void printMenu() const;

    void inventory() const;

    float orderItem(string courseDescription, char item);

    bool isCoursesEmpty();

    void closeShop();

};


#endif /*MENU_H*/