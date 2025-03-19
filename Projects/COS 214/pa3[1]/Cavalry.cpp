#include "Cavalry.h"
#include <iostream>

void Cavalry::move() {
    std::cout << "Cavalry is moving with mobility " << mobility << "." << std::endl;
}

void Cavalry::attack() {
    std::cout << "Cavalry attacks with power " << attackPower << "." << std::endl;
}

// Terrain-specific Cavalry Implementations
WoodlandCavalry::WoodlandCavalry() {
    setMobility(7);
    setDefense(4);
    setAttackPower(8);
}

RiverbankCavalry::RiverbankCavalry() {
    setMobility(5);
    setDefense(6);
    setAttackPower(7);
}

OpenFieldCavalry::OpenFieldCavalry() {
    setMobility(9);
    setDefense(5);
    setAttackPower(9);
}

