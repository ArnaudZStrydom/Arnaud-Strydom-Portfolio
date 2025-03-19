#include <iostream>
#include "Soldiers.h"
#include "Memento.h"
#include "CareTaker.h"
#include "InfantryFactory.h"
#include "ShieldBearerFactory.h"
#include "BoatmanFactory.h"

using namespace std;

void test1(){
    cout << "\n";
    cout << "Memento Pattern Test" << endl;
    cout << "---------------------" << endl;

    // Create the factories
    InfantryFactory* infantryFactory = new InfantryFactory();
    ShieldBearerFactory* shieldBearerFactory = new ShieldBearerFactory();
    BoatmanFactory* boatmanFactory = new BoatmanFactory();

    // Create the units
    Soldiers* infantryUnit = infantryFactory->createUnit(100, 50, 30, 10);
    Soldiers* shieldBearerUnit = shieldBearerFactory->createUnit(120, 40, 50, 8);
    Soldiers* boatmanUnit = boatmanFactory->createUnit(80, 60, 20, 12);

    // Create CareTaker obj
    CareTaker careTaker;

    // Save initial states
    careTaker.addMemento(infantryUnit->militusMemento());
    careTaker.addMemento(shieldBearerUnit->militusMemento());
    careTaker.addMemento(boatmanUnit->militusMemento());

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

    // Restore states
    infantryUnit->vivificaMemento(careTaker.getMemento(0));
    shieldBearerUnit->vivificaMemento(careTaker.getMemento(1));
    boatmanUnit->vivificaMemento(careTaker.getMemento(2));

    // Verify restored states
    cout << "\nRestord Infantry Soldiers: " << infantryUnit->getAmountOfSoldiersPerUnit() << endl;
    cout << "Restored ShieldBearer Soldiers: " << shieldBearerUnit->getAmountOfSoldiersPerUnit() << endl;
    cout << "Restored Boatman Soldiers: " << boatmanUnit->getAmountOfSoldiersPerUnit() << endl;

    // Clean up
    delete infantryUnit;
    delete shieldBearerUnit;
    delete boatmanUnit;
    delete infantryFactory;
    delete shieldBearerFactory;
    delete boatmanFactory;
}


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
    test1();
    return 0;
}