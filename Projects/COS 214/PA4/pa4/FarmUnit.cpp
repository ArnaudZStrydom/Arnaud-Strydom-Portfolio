#include "FarmUnit.h"
#include <algorithm>
#include <iostream>
#include "CropField.h"


class CropField;
FarmUnit::~FarmUnit() {
    for (auto truck : trucks) {
        delete truck;
    }
}

void FarmUnit::buyTruck(Truck* truck) {
    trucks.push_back(truck);
    std::cout << truck->getType() << " purchased." << std::endl;
}

void FarmUnit::sellTruck(Truck* truck) {
    trucks.erase(std::remove(trucks.begin(), trucks.end(), truck), trucks.end());
    delete truck;
    std::cout << truck->getType() << " sold." << std::endl;
}

void FarmUnit::callTruck(CropField* field, Truck* truck) {
    truck->startEngine();
    truck->performOperation(field);
    std::cout << "Truck " << truck->getType() << " called for " << field->getName() << std::endl;
}

void FarmUnit::notifyTrucks(CropField* field) {
    for (auto truck : trucks) {
        if (field->getSoilStateName() == "Dry" && truck->getType() == "FertilizerTruck") {
            callTruck(field, truck);
            std::cout << "Truck " << truck->getType() << " called for " << field->getName() << std::endl;
        } else if (field->getLeftoverCapacity() <= 10 && truck->getType() == "DeliveryTruck") {  // Arbitrary threshold for demo
            callTruck(field, truck);
            std::cout << "Truck " << truck->getType() << " called for " << field->getName() << std::endl;
        }
    }
}

void FarmUnit::listTrucks() const {
    for (auto truck : trucks) {
        std::cout << "Truck: " << truck->getType() << std::endl;
    }
}
