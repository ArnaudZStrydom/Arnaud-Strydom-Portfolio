#ifndef BOATMANFACTORY_H
#define BOATMANFACTORY_H

#include"SoldierFactory.h"
class BoatmanFactory {


public:
	Soldiers* createUnit(int healthPerSoldier , int damagePerSoldier , int defencePerSoldier , int amountOfSoldiersPerUnit , string unitName );

	int calculateTotalHealthPerUnit();

	int calculateTotalDamagePerUnit();

	int calculateTotalDefencePerUnit();
};

#endif
