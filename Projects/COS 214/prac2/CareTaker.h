#ifndef CARETAKER_H
#define CARETAKER_H

#include <string>
#include <iostream>
#include "Memento.h"
using namespace std; 
class CareTaker {


public:
	void addMemento(Memento m);

	Memento getMemento();

	
};

#endif
