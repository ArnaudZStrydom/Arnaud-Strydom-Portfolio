#include "InfantryFactory.h"
#include "Infantry.h"

Soldiers* InfantryFactory::createUnit(int healthPerSoldier , int damagePerSoldier , int defencePerSoldier , int amountOfSoldiersPerUnit , string unitName ) {
	Infantry* infantry = new Infantry();
	infantry->setUnitStats(healthPerSoldier, damagePerSoldier, defencePerSoldier, amountOfSoldiersPerUnit, unitName);
	return infantry;
}
     


int InfantryFactory::calculateTotalHealthPerUnit() {
	// TODO - implement InfantryFactory::calculateTotalHealthPerUnit
	throw "Not yet implemented";
}

int InfantryFactory::calculateTotalDamagePerUnit() {
	// TODO - implement InfantryFactory::calculateTotalDamagePerUnit
	throw "Not yet implemented";
}

int InfantryFactory::calculateTotalDefencePerUnit() {
	// TODO - implement InfantryFactory::calculateTotalDefencePerUnit
	throw "Not yet implemented";
}
