#ifndef SHIELDBEARERFACTORY_H
#define SHIELDBEARERFACTORY_H

#include"SoldierFactory.h"
class ShieldBearerFactory : public SoldierFactory {


public:
	Soldiers* createUnit(int healthPerSoldier , int damagePerSoldier , int defencePerSoldier , int amountOfSoldiersPerUnit , string unitName );

	int calculateTotalHealthPerUnit();

	int calculateTotalDamagePerUnit();

	int calculateTotalDefencePerUnit();
};

#endif
