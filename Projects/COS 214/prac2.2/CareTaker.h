#ifndef CARETAKER_H
#define CARETAKER_H

#include <vector>
#include "Memento.h"

class CareTaker {
public:
    void addMemento(Memento* memento);
    Memento* getMemento(int index);
    ~CareTaker();  // Destructor to clean up allocated memory

private:
    std::vector<Memento*> mementos;
};

#endif // CARETAKER_H