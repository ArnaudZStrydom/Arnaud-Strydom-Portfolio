#include "Legion.h"
#include <algorithm> // Include this header for std::remove

// Add a unit to the legion
void Legion::addUnit(LegionUnit* unit) {
    units.push_back(unit);
}

// Remove a unit from the legion
void Legion::removeUnit(LegionUnit* unit) {
    units.erase(std::remove(units.begin(), units.end(), unit), units.end());
}

// Command all units in the legion to move
void Legion::move() {
    for (LegionUnit* unit : units) {
        unit->move();
    }
}

// Command all units in the legion to attack
void Legion::attack() {
    for (LegionUnit* unit : units) {
        unit->attack();
    }
}

// Calculate total mobility for the legion
int Legion::getTotalMobility() const {
    int totalMobility = 0;
    for (const LegionUnit* unit : units) {
        totalMobility += unit->getMobility();
    }
    return totalMobility;
}

// Calculate total defense for the legion
int Legion::getTotalDefense() const {
    int totalDefense = 0;
    for (const LegionUnit* unit : units) {
        totalDefense += unit->getDefense();
    }
    return totalDefense;
}

// Calculate total attack power for the legion
int Legion::getTotalAttackPower() const {
    int totalAttackPower = 0;
    for (const LegionUnit* unit : units) {
        totalAttackPower += unit->getAttackPower();
    }
    return totalAttackPower;
}

// Destructor to clean up allocated units
Legion::~Legion() {
    for (LegionUnit* unit : units) {
        delete unit;
    }
}