#include "LegionUnit.h"

void LegionUnit::setMobility(int m) {
    mobility = m;
}

int LegionUnit::getMobility() const {
    return mobility;
}

void LegionUnit::setDefense(int d) {
    defense = d;
}

int LegionUnit::getDefense() const {
    return defense;
}

void LegionUnit::setAttackPower(int ap) {
    attackPower = ap;
}

int LegionUnit::getAttackPower() const {
    return attackPower;
}
