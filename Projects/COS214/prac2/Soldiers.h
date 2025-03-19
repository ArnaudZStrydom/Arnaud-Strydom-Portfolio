#ifndef SOLDIERS_H
#define SOLDIERS_H

#include <iostream>
#include <string>
using namespace std; 
class Soldiers {

private:
	int healthPerSoldier;
	int damagePerSoldier;
	int defencePerSoldier;
	int amountOfSoldiersPerUnit;
	string unitName;

public:
	//Memento* militusMemento();
	
	

	//void vivificaMemento(Memento* mem);

	void engage();

	void disengage();

	 virtual void setUnitStats(int healthPerSoldier, int damagePerSoldier, int defencePerSoldier, int amountOfSoldiersPerUnit, string unitName) = 0;
private:
	
	virtual void prepare() = 0;

	virtual void execute() = 0;

	virtual void retreat() = 0;

	virtual void rest() = 0;

public:
	Soldiers* clonis();
};

#endif
