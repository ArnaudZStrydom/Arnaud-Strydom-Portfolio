#ifndef TACTICALCOMMAND_H
#define TACTICALCOMMAND_H

#include "BattleStrategy.h"
#include "TacticalPlanner.h"
#include "WarArchives.h"

class TacticalCommand {
private:
    TacticalPlanner* planner;
    WarArchives* archives;

public:
    TacticalCommand();

    // Set the current strategy
    void setStrategy(BattleStrategy* s);

    // Execute the current strategy
    void executeStrategy();

    // Choose the best strategy based on previous results or conditions
    void chooseBestStrategy();

    // Save the current strategy to a memento
    void saveCurrentStrategy(const std::string& label);

    // Restore a previous strategy from a memento
    void restoreStrategy(const std::string& label);

    // Destructor
    ~TacticalCommand();
};

#endif // TACTICALCOMMAND_H


