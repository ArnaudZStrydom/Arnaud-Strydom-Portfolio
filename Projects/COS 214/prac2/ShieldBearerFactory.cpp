#include "ShieldBearerFactory.h"

Soldiers* ShieldBearerFactory::createUnit(int health, int damage, int defence, int amount) {
    return new ShieldBearer(health, damage, defence, amount);
}
