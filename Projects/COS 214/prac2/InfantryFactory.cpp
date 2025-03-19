#include "InfantryFactory.h"

Soldiers* InfantryFactory::createUnit(int health, int damage, int defence, int amount) {
    return new Infantry(health, damage, defence, amount);
}
