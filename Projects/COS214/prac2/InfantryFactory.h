#ifndef INFANTRYFACTORY_H
#define INFANTRYFACTORY_H

#include"SoldierFactory.h"
class InfantryFactory : public SoldierFactory {

private:
	int healthPerSoldier;
	int damagePerSoldier;
	int defencePerSoldier;
	int amountOfSoldiersPerUnit;
	string unitName;
public:
	Soldiers* createUnit(int healthPerSoldier , int damagePerSoldier , int defencePerSoldier , int amountOfSoldiersPerUnit , string unitName )override;

	int calculateTotalHealthPerUnit() override;

	int calculateTotalDamagePerUnit() override;

	int calculateTotalDefencePerUnit() override;
};

#endif
