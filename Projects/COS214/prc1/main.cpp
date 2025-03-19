#include "Menu.h"
#include "Course.h"
#include <iostream>
#include <string>
#include <cstring>

using namespace std;

int main() {


    const int size =20;
    char st1[size] = "Hostess Snack "; 
    char st2[] = "Cakes";
    strcat(st1 ,st2);
    
     

    // Create a MenuItem object
    MenuItem burger("Cheeseburger", 5.99, 10);

    // Test getDescription
    cout << "Description: " << burger.getDescription() << endl;

    // Test getPrice
    cout << "Price: " << burger.getPrice() << endl;

    // Test getStock
    cout << "Stock: " << burger.getStock() << endl;

    // Test reduceStock
    burger.reduceStock();
    cout << "Stock after reducing: " << burger.getStock() << endl;

    // Reduce stock until it hits 0
    for (int i = 0; i < 10; i++) {
        burger.reduceStock();
        cout << "Stock after reducing: " << burger.getStock() << endl;
    }


    MainCourse mainCourse(5);

    // Add MenuItems to MainCourse
    mainCourse.addMenuItem("Grilled Chicken", 75.99, 10);
    mainCourse.addMenuItem("Beef Steak", 120.50, 5);

    // Test printMenuItems for MainCourse
    cout << "Main Course Menu Items:" << endl;
    mainCourse.printMenuItems();

    // Test printInventory for MainCourse
    cout << "Main Course Inventory:" << endl;
    mainCourse.printInventory();

    // Test getMenuItem for MainCourse
    MenuItem* mainCourseItem = mainCourse.getMenuItem(0);
    if (mainCourseItem) {
        cout << "Retrieved Item from Main Course: " << mainCourseItem->getDescription() << endl;
    }

    // Test recommendBeverage for MainCourse
    mainCourse.recommendBeverage();

    // Create a Starter object
    Starter starter(3);

    // Add MenuItems to Starter
    starter.addMenuItem("Bruschetta", 30.99, 15);
    starter.addMenuItem("Stuffed Mushrooms", 40.75, 8);

    // Test printMenuItems for Starter
    cout << "Starter Menu Items:" << endl;
    starter.printMenuItems();

    // Test printInventory for Starter
    cout << "Starter Inventory:" << endl;
    starter.printInventory();

    // Test getMenuItem for Starter
    MenuItem* starterItem = starter.getMenuItem(0);
    if (starterItem) {
        cout << "Retrieved Item from Starter: " << starterItem->getDescription() << endl;
    }

    // Test recommendBeverage for Starter
    starter.recommendBeverage();

    // Create a Dessert object
    Dessert dessert(4);

    // Add MenuItems to Dessert
    dessert.addMenuItem("Cheesecake", 50.99, 12);
    dessert.addMenuItem("Tiramisu", 45.50, 7);

    // Test printMenuItems for Dessert
    cout << "Dessert Menu Items:" << endl;
    dessert.printMenuItems();

    // Test printInventory for Dessert
    cout << "Dessert Inventory:" << endl;
    dessert.printInventory();

    // Test getMenuItem for Dessert
    MenuItem* dessertItem = dessert.getMenuItem(0);
    if (dessertItem) {
        cout << "Retrieved Item from Dessert: " << dessertItem->getDescription() << endl;
    }

    // Test recommendBeverage for Dessert
    dessert.recommendBeverage();

    Menu menu;

    // Create some courses
    Course* starter1 = new Starter(10); // Max 10 items for starters
    Course* mainCourse1 = new MainCourse(10); // Max 10 items for main courses
    Course* dessert1 = new Dessert(10); // Max 10 items for desserts
    // Add courses to the menu
    menu.addCourse(starter1);
    menu.addCourse(mainCourse1);
    menu.addCourse(dessert1);

    // Add menu items to courses
    menu.addMenuItem("Starter", "Onion Soup", 35.99, 6);
    menu.addMenuItem("Starter", "Caesar Salad", 45.99, 4);
    menu.addMenuItem("Main", "Steak", 105.99, 5);
    menu.addMenuItem("Main", "Chicken", 95.99, 2);
    menu.addMenuItem("Main", "Fish", 85.99, 3);
    menu.addMenuItem("Dessert", "Ice Cream", 65.99, 7);

    // Print the menu
    cout << "Menu:" << endl;
    menu.printMenu();

    // Print inventory
    cout << "\nInventory:" << endl;
    menu.inventory();

    // Order items
    cout << "\nOrdering items:" << endl;
    float price = menu.orderItem("Main", 'c'); // Order "Chicken"
    if (price > 0) {
        cout << "Ordered item for $" << price << endl;
    } else {
        cout << "Item not available or out of stock" << endl;
    }

    cout << "\nOrdering items:" << endl;
    float price1 = menu.orderItem("Main", 'c'); // Order "Chicken"
    if (price1 > 0) {
        cout << "Ordered item for $" << price1 << endl;
    } else {
        cout << "Item not available or out of stock" << endl;
    }

        cout << "\nOrdering items:" << endl;
    float price2 = menu.orderItem("Main", 'c'); // Order "Chicken"
    if (price2 > 0) {
        cout << "Ordered item for $" << price2 << endl;
    } else {
        cout << "Item not available or out of stock" << endl;
    }

        cout << "\nOrdering items:" << endl;
    float price3 = menu.orderItem("Main", 'c'); // Order "Chicken"
    if (price3 > 0) {
        cout << "Ordered item for $" << price3 << endl;
    } else {
        cout << "Item not available or out of stock" << endl;
    }

    // Check if courses are empty
    cout << "\nAre courses empty? " << (menu.isCoursesEmpty() ? "Yes" : "No") << endl;


        // Print the menu
    cout << "Menu:" << endl;
    menu.printMenu();

    // Print inventory
    cout << "\nInventory:" << endl;
    menu.inventory();


    return 0;
}
