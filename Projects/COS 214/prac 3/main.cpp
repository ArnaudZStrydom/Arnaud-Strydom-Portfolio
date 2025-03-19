#include "TacticalCommand.h"
#include "Flanking.h"
#include "Fortification.h"
#include "Ambush.h"

int main() {
    // Create the TacticalCommand context
    TacticalCommand* command = new TacticalCommand();

    // Set and save a strategy
    command->setStrategy(new Flanking());
    command->saveCurrentStrategy("initial_flanking");

    // Change and save another strategy
    command->setStrategy(new Fortification());
    command->saveCurrentStrategy("defensive_position");

    // Execute the current strategy
    command->executeStrategy();

    // Restore the initial flanking strategy and execute it
    command->restoreStrategy("initial_flanking");
    command->executeStrategy();

    // Automatically choose and execute the best strategy
    command->chooseBestStrategy();
    command->executeStrategy();

    // Clean up
    delete command;

    return 0;
}



