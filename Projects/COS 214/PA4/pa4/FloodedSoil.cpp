#include "FloodedSoil.h"

std::string FloodedSoil::getName() const {
    return "Flooded";
}

int FloodedSoil::harvestCrops() const {
    return 0;  // No yield
}

void FloodedSoil::rain(CropField* field) {
    // Remain flooded, no transition
}
