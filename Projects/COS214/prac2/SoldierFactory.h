// SoldierFactory.h

#ifndef SOLDIERFACTORY_H
#define SOLDIERFACTORY_H
#include "Soldiers.h"
using namespace std; 

class SoldierFactory {
private:
	Soldiers* soldiers;
protected:
    virtual Soldiers* createUnit(int healthPerSoldier , int damagePerSoldier , int defencePerSoldier , int amountOfSoldiersPerUnit , string unitName ) = 0;
	virtual int calculateTotalHealthPerUnit() = 0;
    virtual int calculateTotalDamagePerUnit() = 0;
    virtual int calculateTotalDefencePerUnit() = 0;
    

public:
    virtual ~SoldierFactory(){};
};

#endif // SOLDIERFACTORY_H
