#include "FruitfulSoil.h"

std::string FruitfulSoil::getName() const {
    return "Fruitful";
}

int FruitfulSoil::harvestCrops() const {
    return 3;  // Triple yield
}

void FruitfulSoil::rain(CropField* field) {
    // No state change, remain fruitful
}
