#include <string>
#include <iostream>
#include "Soldiers.h"
#include "Infantry.h"
#include "ShieldBearer.h"
#include "InfantryFactory.h"
#include "ShieldBearerFactory.h"
#include "SoldierFactory.h"
#include <vector>


using namespace std;

int main() {
    vector<Soldiers*> soldiers;
    InfantryFactory* infantryFactory = new InfantryFactory();
    ShieldBearerFactory* shieldBearerFactory = new ShieldBearerFactory();
    Soldiers* invantry1 = infantryFactory->createUnit(100, 50, 25, 10, "Infantry Unit");
    soldiers.push_back(invantry1);
    soldiers.push_back(shieldBearerFactory->createUnit(200, 15, 75, 5, "shield Unit"));
    for (Soldiers* soldier : soldiers) {
        soldier->engage();
        soldier->disengage();
    }
    return 0;
}