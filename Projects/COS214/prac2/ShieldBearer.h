#ifndef SHIELDBEARER_H
#define SHIELDBEARER_H

#include "Soldiers.h"
#include <string>
#include <iostream>

using namespace std; 

class ShieldBearer : Soldiers {

private:
	int healthPerSoldier;
	int damagePerSoldier;
	int defencePerSoldier;
	int amountOfSoldiersPerUnit;
	string unitName;

public:
	void engage();

	void disengage();

private:
	void prepare();

	void execute();

	void retreat();

	void rest();

public:
	Soldiers* clonis();
};

#endif
