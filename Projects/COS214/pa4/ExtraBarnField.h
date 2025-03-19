#ifndef EXTRABARNFIELD_H
#define EXTRABARNFIELD_H

#include "CropFieldDecorator.h"

class ExtraBarnField : public CropFieldDecorator {
private:
    int additionalCapacity;

public:
    ExtraBarnField(CropField* field, int extraCapacity);

    int getTotalCapacity() const override;
    int getLeftoverCapacity() const override;  // Override to track leftover capacity
};

#endif // EXTRABARNFIELD_H

