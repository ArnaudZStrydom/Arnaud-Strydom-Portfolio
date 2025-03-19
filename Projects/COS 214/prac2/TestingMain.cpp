#include <iostream>
#include "InfantryFactory.h"
#include "ShieldBearerFactory.h"
#include "BoatmanFactory.h"

using namespace std;

int main() {
    // Create soldier factories
    SoldierFactory* infantryFactory = new InfantryFactory();
    SoldierFactory* shieldBearerFactory = new ShieldBearerFactory();
    SoldierFactory* boatmanFactory = new BoatmanFactory();

    // Create soldier units
    Soldiers* infantryUnit = infantryFactory->createUnit(100, 50, 30, 10);
    Soldiers* shieldBearerUnit = shieldBearerFactory->createUnit(120, 40, 50, 8);
    Soldiers* boatmanUnit = boatmanFactory->createUnit(80, 60, 20, 12);

    // Display unit details
    cout << "Infantry Total Health: " << infantryFactory->calculateTotalHealthPerUnit(infantryUnit) << endl;
    cout << "ShieldBearer Total Damage: " << shieldBearerFactory->calculateTotalDamagePerUnit(shieldBearerUnit) << endl;
    cout << "Boatman Total Defence: " << boatmanFactory->calculateTotalDefencePerUnit(boatmanUnit) << endl;

    // Engage in battle
    cout << "\nInfantry engaging:" << endl;
    infantryUnit->engage();

    cout << "\nShieldBearer engaging:" << endl;
    shieldBearerUnit->engage();

    cout << "\nBoatman engaging:" << endl;
    boatmanUnit->engage();

    // Attack sequence
    cout << "\nInfantry attacks ShieldBearer:" << endl;
    infantryUnit->attack(shieldBearerUnit);
    cout << "ShieldBearer Remaining Soldiers: " << shieldBearerUnit->getAmountOfSoldiersPerUnit() << endl;

    cout << "\nBoatman attacks Infantry:" << endl;
    boatmanUnit->attack(infantryUnit);
    cout << "Infantry Remaining Soldiers: " << infantryUnit->getAmountOfSoldiersPerUnit() << endl;

    // Disengage from battle
    cout << "\nInfantry disengaging:" << endl;
    infantryUnit->disengage();

    cout << "\nShieldBearer disengaging:" << endl;
    shieldBearerUnit->disengage();

    cout << "\nBoatman disengaging:" << endl;
    boatmanUnit->disengage();

    // Clean up
    delete infantryUnit;
    delete shieldBearerUnit;
    delete boatmanUnit;
    delete infantryFactory;
    delete shieldBearerFactory;
    delete boatmanFactory;

    return 0;
}
