#ifndef AMBUSH_H
#define AMBUSH_H

#include "BattleStrategy.h"
#include <iostream>

class Ambush : public BattleStrategy {
public:
    void engage() override {
        std::cout << "Executing Ambush tactic!" << std::endl;
        // Implementation of the Ambush tactic
    }
};

#endif // AMBUSH_H
