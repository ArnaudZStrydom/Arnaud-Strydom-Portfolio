#include "Infantry.h"
#include <iostream>

Infantry::Infantry(int health, int damage, int defence, int amount)
    : Soldiers(health, damage, defence, amount, "Infantry") {}

Soldiers* Infantry::clonis() const {
    return new Infantry(*this);
}

void Infantry::prepare() {
    std::cout << "Infantry preparing for battle! Marching forward to form legion formation." << std::endl;
}

void Infantry::execute() {
    std::cout << "Infantry engaging the enemy! Fighting commences" << std::endl;
}

void Infantry::retreat() {
    std::cout << "Infantry retreating after battle is won" << std::endl;
}

void Infantry::rest() {
    std::cout << "Infantry resting after retreat and victory!" << std::endl;
}
