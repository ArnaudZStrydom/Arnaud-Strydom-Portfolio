#ifndef FRUITFULSOIL_H
#define FRUITFULSOIL_H

#include "SoilState.h"

class FruitfulSoil : public SoilState {
public:
    std::string getName() const override;
    int harvestCrops() const override;
    void rain(CropField* field) override;  // No transition on rain
};

#endif // FRUITFULSOIL_H
