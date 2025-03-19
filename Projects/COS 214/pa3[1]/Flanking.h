#ifndef FLANKING_H
#define FLANKING_H

#include "BattleStrategy.h"
#include <iostream>

class Flanking : public BattleStrategy {
public:
    void engage() override {
        std::cout << "Executing Flanking maneuver!" << std::endl;
        // Implementation of the Flanking tactic
    }
};

#endif // FLANKING_H
