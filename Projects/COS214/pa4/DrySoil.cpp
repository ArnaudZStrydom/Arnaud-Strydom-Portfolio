#include "DrySoil.h"
#include "CropField.h"
#include "FruitfulSoil.h"  // Include for state transition
#include <iostream>


std::string DrySoil::getName() const
{
    return "Dry";
}

int DrySoil::harvestCrops() const {
    return 1;  // Minimal yield
}

void DrySoil::rain(CropField* field) {
    field->setSoilState(new FruitfulSoil());  // Transition to FruitfulSoil
    std::cout<<"Soil state has changed to Fruitful soil"<<std::endl;
}
