#include "TacticalCommand.h"
#include "Flanking.h"
#include "Fortification.h"
#include "Ambush.h"
#include <iostream>
#include <stdlib.h>

// Constructor initializes the planner and archives
TacticalCommand::TacticalCommand() {
    planner = new TacticalPlanner();
    archives = new WarArchives();
}

// Set the current strategy
void TacticalCommand::setStrategy(BattleStrategy* s) {
    planner->setStrategy(s);
}

// Execute the current strategy
void TacticalCommand::executeStrategy() {
    if (planner->getStrategy()) {
        planner->getStrategy()->engage();
    } else {
        std::cout << "No strategy set!" << std::endl;
    }
}

void TacticalCommand::chooseBestStrategy() {
    std::cout << "Choosing the best strategy based on conditions from WarArchives..." << std::endl;

    // Assuming WarArchives has methods to provide insights based on previous battles
    std::string bestStrategy = archives->getBestStrategy(); // This method needs to be implemented in WarArchives

    if (bestStrategy == "Flanking") {
        setStrategy(new Flanking());
        std::cout << "Flanking strategy chosen based on past successes." << std::endl;
    } else if (bestStrategy == "Fortification") {
        setStrategy(new Fortification());
        std::cout << "Fortification strategy chosen based on past successes." << std::endl;
    } else if (bestStrategy == "Ambush") {
        setStrategy(new Ambush());
        std::cout << "Ambush strategy chosen based on past successes." << std::endl;
    } else {
        // Fallback to random or default strategy
        std::cout << "No historical data available, choosing a random strategy." << std::endl;
        int condition = rand() % 3;
        switch (condition) {
            case 0:
                setStrategy(new Flanking());
                std::cout << "Flanking strategy chosen." << std::endl;
                break;
            case 1:
                setStrategy(new Fortification());
                std::cout << "Fortification strategy chosen." << std::endl;
                break;
            case 2:
                setStrategy(new Ambush());
                std::cout << "Ambush strategy chosen." << std::endl;
                break;
            default:
                std::cout << "No valid condition met." << std::endl;
                break;
        }
    }
}

// Save the current strategy to a memento
void TacticalCommand::saveCurrentStrategy(const std::string& label) {
    TacticalMemento* memento = planner->createMemento();
    archives->addTacticalMemento(label, memento);
    std::cout << "Strategy saved under label: " << label << std::endl;
}

// Restore a previous strategy from a memento
void TacticalCommand::restoreStrategy(const std::string& label) {
    TacticalMemento* memento = archives->getTacticalMemento(label);
    if (memento) {
        planner->restoreMemento(memento);
        std::cout << "Strategy restored from label: " << label << std::endl;
    } else {
        std::cout << "No strategy found for label: " << label << std::endl;
    }
}

// Destructor cleans up the planner and archives
TacticalCommand::~TacticalCommand() {
    delete planner;
    delete archives;
}
