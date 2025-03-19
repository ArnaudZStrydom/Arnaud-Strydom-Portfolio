#ifndef SHIELDBEARERFACTORY_H
#define SHIELDBEARERFACTORY_H

#include "SoldierFactory.h"
#include "ShieldBearer.h"

class ShieldBearerFactory : public SoldierFactory {
public:
    Soldiers* createUnit(int health, int damage, int defence, int amount) override;
};

#endif // SHIELDBEARERFACTORY_H

