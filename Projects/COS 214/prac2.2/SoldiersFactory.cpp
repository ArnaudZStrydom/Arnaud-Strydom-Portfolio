#include "SoldierFactory.h"

int SoldierFactory::calculateTotalHealthPerUnit(Soldiers* unit) {
    return unit->getHealthPerSoldier() * unit->getAmountOfSoldiersPerUnit();
}

int SoldierFactory::calculateTotalDamagePerUnit(Soldiers* unit) {
    return unit->getDamagePerSoldier() * unit->getAmountOfSoldiersPerUnit();
}

int SoldierFactory::calculateTotalDefencePerUnit(Soldiers* unit) {
    return unit->getDefencePerSoldier() * unit->getAmountOfSoldiersPerUnit();
}