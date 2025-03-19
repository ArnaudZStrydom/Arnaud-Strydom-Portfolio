#ifndef DELIVERYTRUCK_H
#define DELIVERYTRUCK_H

#include "Truck.h"

class DeliveryTruck : public Truck {
public:
    DeliveryTruck();

    void startEngine() const override;
    void performOperation(CropField* field) override;
};

#endif // DELIVERYTRUCK_H
