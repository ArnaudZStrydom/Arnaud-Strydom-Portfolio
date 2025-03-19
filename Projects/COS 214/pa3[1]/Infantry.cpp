#include "Infantry.h"
#include <iostream>

void Infantry::move() {
    std::cout << "Infantry is moving with mobility " << mobility << "." << std::endl;
}

void Infantry::attack() {
    std::cout << "Infantry attacks with power " << attackPower << "." << std::endl;
}

// Terrain-specific Infantry Implementations
WoodlandInfantry::WoodlandInfantry() {
    setMobility(8);
    setDefense(5);
    setAttackPower(7);
}

RiverbankInfantry::RiverbankInfantry() {
    setMobility(6);
    setDefense(7);
    setAttackPower(6);
}

OpenFieldInfantry::OpenFieldInfantry() {
    setMobility(7);
    setDefense(6);
    setAttackPower(8);
}

