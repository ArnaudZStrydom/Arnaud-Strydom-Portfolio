#ifndef TACTICALPLANNER_H
#define TACTICALPLANNER_H

#include "BattleStrategy.h"
#include "TacticalMemento.h"
#include <cstddef>

class TacticalPlanner {
private:
    BattleStrategy* currentStrategy;

public:
    TacticalPlanner() : currentStrategy(nullptr) {}

    void setStrategy(BattleStrategy* strategy) {
        currentStrategy = strategy;
    }

    BattleStrategy* getStrategy() const {
        return currentStrategy;
    }

    // Create a memento containing the current strategy
    TacticalMemento* createMemento() {
        return new TacticalMemento(currentStrategy);
    }

    // Restore a strategy from a memento
    void restoreMemento(TacticalMemento* memento) {
        setStrategy(memento->getSavedStrategy());
    }

    ~TacticalPlanner() {
        
    }
};

#endif // TACTICALPLANNER_H
