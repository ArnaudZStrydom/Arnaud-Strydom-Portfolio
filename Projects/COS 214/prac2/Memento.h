#ifndef MEMENTO_H
#define MEMENTO_H

#include <string>
#include <iostream>

using namespace std; 
class Memento {

private:
	int healthPerSoldier;
	int damagePerSoldier;
	int defencePerSoldier;
	int amountOfSoldiersPerUnit;
	string unitName;

public:
	Memento(int value1, int value2, int value3, int value4, string value5);
};

#endif
