#ifndef FERTILIZEDFIELD_H
#define FERTILIZEDFIELD_H

#include "CropFieldDecorator.h"

class FertilizedField : public CropFieldDecorator {
public:
    FertilizedField(CropField* field);

    void harvestCrops() override;
    void increaseProduction() override;  // Override to increase production
};

#endif // FERTILIZEDFIELD_H

