#ifndef INFANTRYFACTORY_H
#define INFANTRYFACTORY_H

#include "SoldierFactory.h"
#include "Infantry.h"

class InfantryFactory : public SoldierFactory {
public:
    Soldiers* createUnit(int health, int damage, int defence, int amount) override;
};

#endif // INFANTRYFACTORY_H

