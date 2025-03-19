#include "Soldiers.h"
#include <iostream>

Soldiers::Soldiers(int health, int damage, int defence, int amount, const string& name)
    : healthPerSoldier(health), damagePerSoldier(damage), defencePerSoldier(defence),
      amountOfSoldiersPerUnit(amount), unitName(name) {}

int Soldiers::getHealthPerSoldier() const {
    return healthPerSoldier;
}

int Soldiers::getDamagePerSoldier() const {
    return damagePerSoldier;
}

int Soldiers::getDefencePerSoldier() const {
    return defencePerSoldier;
}

int Soldiers::getAmountOfSoldiersPerUnit() const {
    return amountOfSoldiersPerUnit;
}

string Soldiers::getUnitName() const {
    return unitName;
}

void Soldiers::attack(Soldiers* target) {
    int totalDamage = damagePerSoldier * amountOfSoldiersPerUnit;
    std::cout << unitName << " attacks " << target->getUnitName() << " with " << totalDamage << " total damage!" << std::endl;
    target->defend(totalDamage);
}

void Soldiers::defend(int damage) {
    int effectiveDamage = damage - (defencePerSoldier * amountOfSoldiersPerUnit);
    if (effectiveDamage < 0) effectiveDamage = 0;

    int soldiersLost = effectiveDamage / healthPerSoldier;
    if (soldiersLost > amountOfSoldiersPerUnit) soldiersLost = amountOfSoldiersPerUnit;

    amountOfSoldiersPerUnit -= soldiersLost;
    std::cout << unitName << " defends and loses " << soldiersLost << " soldiers. " 
              << amountOfSoldiersPerUnit << " soldiers remaining." << std::endl;
}

void Soldiers::engage() {
    prepare();
    execute();
}

void Soldiers::disengage() {
    retreat();
    rest();
}
