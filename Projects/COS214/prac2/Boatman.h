#ifndef BOATMAN_H
#define BOATMAN_H

#include "Soldiers.h"
#include <string>
#include <iostream>

using namespace std; 
class Boatman : public Soldiers{

private:
	int healthPerSoldier;
	int damagePerSoldier;
	int defencePerSoldier;
	int amountOfSoldiersPerUnit;
	string unitName;

public:
	void engage();

	void disengage();

	void setUnitStats(int healthPerSoldier , int damagePerSoldier , int defencePerSoldier , int amountOfSoldiersPerUnit , string unitName ) override;

private:
	void prepare();

	void execute();

	void retreat();

	void rest();

public:
	Soldiers* clonis();
};

#endif
