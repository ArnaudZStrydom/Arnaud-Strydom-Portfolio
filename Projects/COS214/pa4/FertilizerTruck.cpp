#include "FertilizerTruck.h"
#include "CropField.h"
#include "FertilizedField.h"
#include<iostream>

FertilizerTruck::FertilizerTruck() : Truck("FertilizerTruck") {}

void FertilizerTruck::startEngine() const {
    std::cout << "Starting the engine of " << type << "." << std::endl;
}

void FertilizerTruck::performOperation(CropField* field) {
    std::cout << "Delivering fertilizer to " << field->getCropType() << " field." << std::endl;
    // Wrap the field with a FertilizedField decorator
    field = new FertilizedField(field);
    field->harvestCrops();  // Trigger a harvest after fertilization
}
