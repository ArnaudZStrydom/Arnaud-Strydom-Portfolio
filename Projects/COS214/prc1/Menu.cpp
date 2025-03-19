#include "Menu.h"
#include <iostream>
#include <string>

using namespace std;


Menu::Menu() {}


Menu::~Menu() {
    cout << "Menu destructor called" << endl;
    if(courses.empty() != true){
        closeShop();
    }
    
}


bool Menu::addCourse(Course* course) {
    auto itterator = courses.find(course->getDescription());
    if (itterator == courses.end()) { 
        courses.emplace(course->getDescription(), course);
        return true; 
    }
    
    return false;
    
}


bool Menu::addMenuItem(string courseDescription,  string description, float price, int stock) {
    auto itterator = courses.find(courseDescription);
    if (itterator != courses.end()) {
        return itterator->second->addMenuItem(description, price, stock);
    }
    return false; 
}


void Menu::printMenu() const {
    for (auto courseanddesc : courses) {
        Course* course = courseanddesc.second;
        cout << course->getDescription() << endl;
        course->printMenuItems();
    }
}


void Menu::inventory() const {
    for (auto courseanddesc : courses) {
        Course* course = courseanddesc.second;
        cout << course->getDescription() << endl;
        course->printInventory();
    }
}


float Menu::orderItem(string courseDescription, char item) {
    auto itterator = courses.find(courseDescription);
    if (itterator != courses.end()) {
        Course* course = itterator->second;
        MenuItem* menuItem = course->getMenuItem(item - 'a');
        if (menuItem && menuItem->getStock() > 0) {
            menuItem->reduceStock();
            return menuItem->getPrice();
        }
    }
    return 0; 
}


bool Menu::isCoursesEmpty() {
    return courses.empty();
}


void Menu::closeShop() {
    cout << "Closing shop. Deleting all " << courses.size() << " courses" << endl;
    for (auto courseanddesc : courses) {
        delete courseanddesc.second;
    }
    courses.clear();
}