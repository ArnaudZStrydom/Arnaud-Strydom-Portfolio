#ifndef DRYSOIL_H
#define DRYSOIL_H

#include "SoilState.h"

class DrySoil : public SoilState {
public:
    std::string getName() const override;
    int harvestCrops() const override;
    void rain(CropField* field) ;
};

#endif // DRYSOIL_H

