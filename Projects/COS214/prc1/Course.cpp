#include "Course.h"
#include <iostream>

using namespace std;

Course::Course(string description, int maxNumberOfItems)
    : description(description), maxNumberOfItems(maxNumberOfItems) {}

Course::~Course() {
    for (MenuItem* item : menuItems) {
        delete item;
    }
}

bool Course::addMenuItem(string description, float price, int stock) {
    if(menuItems.size() < maxNumberOfItems){
            MenuItem* newItem = new MenuItem(description, price, stock);
            menuItems.push_back(newItem);
            return true;
    }else{
        return false;
    }
}

void Course::printMenuItems() {
    char index = 'a';
    for (MenuItem* item : menuItems) {
        cout << "\t" << index << "."<< "\t" << item->getDescription() << "\n";
        index++;
    }
}

void Course::printInventory() {
    char index = 'a';
    for (MenuItem* item : menuItems) {
        cout << "\t" ;
        cout<< index ;
        cout<< ".";
        cout<< "\t" ;
        cout<< item->getDescription() ;
        cout<< "\t" ;
        cout<< item->getPrice() ;
        cout<< "\t" ;
        cout<< item->getStock() ;
        cout<< "\n";
        index++;
    }
}

string Course::getDescription() {
    return this->description;
}


MenuItem* Course::getMenuItem(int index){
    int size = menuItems.size();
    if (index < 0 || index >= size) {
        return nullptr;
    }
    return menuItems[index];
}