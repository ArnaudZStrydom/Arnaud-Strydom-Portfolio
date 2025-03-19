#ifndef FORTIFICATION_H
#define FORTIFICATION_H

#include "BattleStrategy.h"
#include <iostream>

class Fortification : public BattleStrategy {
public:
    void engage() override {
        std::cout << "Executing Fortification strategy!" << std::endl;
        // Implementation of the Fortification tactic
    }
};

#endif // FORTIFICATION_H
