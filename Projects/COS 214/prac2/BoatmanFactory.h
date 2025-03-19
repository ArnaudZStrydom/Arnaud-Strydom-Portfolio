#ifndef BOATMANFACTORY_H
#define BOATMANFACTORY_H

#include "SoldierFactory.h"
#include "Boatman.h"

class BoatmanFactory : public SoldierFactory {
public:
    Soldiers* createUnit(int health, int damage, int defence, int amount) override;
};

#endif // BOATMANFACTORY_H

