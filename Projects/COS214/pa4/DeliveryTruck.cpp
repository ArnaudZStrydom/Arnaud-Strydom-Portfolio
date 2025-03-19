#include "DeliveryTruck.h"
#include "CropField.h"
#include <iostream>

DeliveryTruck::DeliveryTruck() : Truck("DeliveryTruck") {}

void DeliveryTruck::startEngine() const {
    std::cout << "Starting the engine of " << type << "." << std::endl;
}

void DeliveryTruck::performOperation(CropField* field) {
    std::cout << "Collecting harvested crops from " << field->getCropType() << " field." << std::endl;
    // Simulate collection, assuming field has a method to reset storage or handle crop collection
    // This part will be abstracted for now
}
