#include "Memento.h"

Memento::Memento(int value1, int value2, int value3, int value4, std::string value5)
    : healthPerSoldier(value1), damagePerSoldier(value2), defencePerSoldier(value3), amountOfSoldiersPerUnit(value4), unitName(value5) {}

int Memento::getHealthPerSoldier() const {
    return healthPerSoldier;
}

int Memento::getDamagePerSoldier() const {
    return damagePerSoldier;
}

int Memento::getDefencePerSoldier() const {
    return defencePerSoldier;
}

int Memento::getAmountOfSoldiersPerUnit() const {
    return amountOfSoldiersPerUnit;
}

std::string Memento::getUnitName() const {
    return unitName;
}