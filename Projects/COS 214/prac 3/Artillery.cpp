#include "Artillery.h"
#include <iostream>

void Artillery::move() {
    std::cout << "Artillery is moving with mobility " << mobility << "." << std::endl;
}

void Artillery::attack() {
    std::cout << "Artillery attacks with power " << attackPower << "." << std::endl;
}

// Terrain-specific Artillery Implementations
WoodlandArtillery::WoodlandArtillery() {
    setMobility(4);
    setDefense(8);
    setAttackPower(10);
}

RiverbankArtillery::RiverbankArtillery() {
    setMobility(3);
    setDefense(9);
    setAttackPower(9);
}

OpenFieldArtillery::OpenFieldArtillery() {
    setMobility(5);
    setDefense(7);
    setAttackPower(11);
}

