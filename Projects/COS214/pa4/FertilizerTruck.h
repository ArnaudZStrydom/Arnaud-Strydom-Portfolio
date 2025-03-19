#ifndef FERTILIZERTRUCK_H
#define FERTILIZERTRUCK_H

#include "Truck.h"

class FertilizerTruck : public Truck {
public:
    FertilizerTruck();

    void startEngine() const override;
    void performOperation(CropField* field) override;
};

#endif // FERTILIZERTRUCK_H
