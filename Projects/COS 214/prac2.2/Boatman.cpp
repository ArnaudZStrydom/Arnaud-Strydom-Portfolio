#include "Boatman.h"
#include <iostream>

Boatman::Boatman(int health, int damage, int defence, int amount)
    : Soldiers(health, damage, defence, amount, "Boatman") {}

Soldiers* Boatman::clonis() const {
    return new Boatman(*this);
}

void Boatman::prepare() {
    std::cout << "Boatman preparing for battle, moving ships in position to assist infantry and shieldbearers from the water!" << std::endl;
}

void Boatman::execute() {
    std::cout << "Boatman engaging the enemy from the water !" << std::endl;
}

void Boatman::retreat() {
    std::cout << "Boatman retreating back to the base harbour!" << std::endl;
}

void Boatman::rest() {
    std::cout << "Boatman resting after retreat and victorius battle and ships are being repaired!" << std::endl;
}
