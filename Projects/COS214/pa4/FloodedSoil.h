#ifndef FLOODEDSOIL_H
#define FLOODEDSOIL_H

#include "SoilState.h"

class FloodedSoil : public SoilState {
public:
    std::string getName() const override;
    int harvestCrops() const override;
    void rain(CropField* field) override;  // No further growth allowed
};

#endif // FLOODEDSOIL_H
