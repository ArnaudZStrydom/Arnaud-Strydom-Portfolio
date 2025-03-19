#include "ShieldBearer.h"
#include <iostream>

ShieldBearer::ShieldBearer(int health, int damage, int defence, int amount)
    : Soldiers(health, damage, defence, amount, "ShieldBearer") {}

Soldiers* ShieldBearer::clonis() const {
    return new ShieldBearer(*this);
}

void ShieldBearer::prepare() {
    std::cout << "ShieldBearers preparing for battle! Forming shield wall infront of infantry." << std::endl;
}

void ShieldBearer::execute() {
    std::cout << "ShieldBearers engaging the enemy! Fighting commences." << std::endl;
}

void ShieldBearer::retreat() {
    std::cout << "ShieldBearers retreating after infantry starts retreating being victorius over the enemy!" << std::endl;
}

void ShieldBearer::rest() {
    std::cout << "ShieldBearer resting after retreat and victorius battle!" << std::endl;
}