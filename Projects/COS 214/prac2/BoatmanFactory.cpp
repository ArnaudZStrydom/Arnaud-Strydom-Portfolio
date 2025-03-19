#include "BoatmanFactory.h"

Soldiers* BoatmanFactory::createUnit(int health, int damage, int defence, int amount) {
    return new Boatman(health, damage, defence, amount);
}
