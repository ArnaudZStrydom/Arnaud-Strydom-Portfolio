#include "CareTaker.h"

CareTaker::~CareTaker() {
    for (Memento* memento : mementos) {
        delete memento;
    }
}

void CareTaker::addMemento(Memento* memento) {
    mementos.push_back(memento);
}

Memento* CareTaker::getMemento(int index) {
    return mementos.at(index);
}