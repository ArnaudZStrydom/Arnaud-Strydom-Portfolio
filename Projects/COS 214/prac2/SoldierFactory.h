#ifndef SOLDIERFACTORY_H
#define SOLDIERFACTORY_H

#include "Soldiers.h"

class SoldierFactory {
public:
    virtual Soldiers* createUnit(int health, int damage, int defence, int amount) = 0;

    virtual int calculateTotalHealthPerUnit(Soldiers* unit);
    virtual int calculateTotalDamagePerUnit(Soldiers* unit);
    virtual int calculateTotalDefencePerUnit(Soldiers* unit);

    virtual ~SoldierFactory() {}
};

#endif // SOLDIERFACTORY_H
