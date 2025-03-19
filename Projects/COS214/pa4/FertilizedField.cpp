#include "FertilizedField.h"
#include "FruitfulSoil.h"
#include <iostream>

FertilizedField::FertilizedField(CropField* field)
    : CropFieldDecorator(field) {}

void FertilizedField::harvestCrops() {
    if (decoratedField->getSoilStateName() == "Dry") {
        decoratedField->setSoilState(new FruitfulSoil());  // Apply fertilizer
    }
    decoratedField->harvestCrops();
}

void FertilizedField::increaseProduction() {
    // Optionally, apply more fertilizer or another state transition
    if (decoratedField->getSoilStateName() == "Dry") {
        decoratedField->setSoilState(new FruitfulSoil());
        std::cout<<"Dry field has been fertilized."<<std::endl;
    }
}

