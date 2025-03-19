#ifndef INFANTRY_H
#define INFANTRY_H

#include "Soldiers.h"
#include <string>
#include <iostream>

using namespace std; 
class Infantry : public Soldiers {

private:
	int healthPerSoldier;
	int damagePerSoldier;
	int defencePerSoldier;
	int amountOfSoldiersPerUnit;
	string unitName;

public:
	void setUnitStats(int healthPerSoldier, int damagePerSoldier, int defencePerSoldier, int amountOfSoldiersPerUnit, string unitName) override;
	void engage();

	 void disengage() ;

private:
	
	void prepare();

	void execute();

	void retreat();

	void rest();

public:
	Soldiers* clonis();
};

#endif
