#include <iostream>
#include "Legion.h"
#include "WoodlandFactory.h"
#include "OpenFieldFactory.h"
#include "RiverbankFactory.h"
#include "Ambush.h"
#include "Flanking.h"
#include "Fortification.h"
#include "TacticalCommand.h"
#include "WarArchives.h"

void testFactories() {
    // Create Factories
    LegionFactory* woodlandFactory = new WoodlandFactory();
    LegionFactory* openFieldFactory = new OpenFieldFactory();
    LegionFactory* riverbankFactory = new RiverbankFactory();

    // Test Infantry creation
    Infantry* woodlandInfantry = woodlandFactory->createInfantry();
    Infantry* openFieldInfantry = openFieldFactory->createInfantry();
    Infantry* riverbankInfantry = riverbankFactory->createInfantry();

    // Test Cavalry creation
    Cavalry* woodlandCavalry = woodlandFactory->createCavalry();
    Cavalry* openFieldCavalry = openFieldFactory->createCavalry();
    Cavalry* riverbankCavalry = riverbankFactory->createCavalry();

    // Test Artillery creation
    Artillery* woodlandArtillery = woodlandFactory->createArtillery();
    Artillery* openFieldArtillery = openFieldFactory->createArtillery();
    Artillery* riverbankArtillery = riverbankFactory->createArtillery();

    std::cout << "Testing unit creation with different factories:" << std::endl;
    woodlandInfantry->move();
    openFieldInfantry->move();
    riverbankInfantry->move();

    woodlandCavalry->move();
    openFieldCavalry->move();
    riverbankCavalry->move();

    woodlandArtillery->move();
    openFieldArtillery->move();
    riverbankArtillery->move();

    // Test additional behaviors like attack
    woodlandInfantry->attack();
    openFieldInfantry->attack();
    riverbankInfantry->attack();

    woodlandCavalry->attack();
    openFieldCavalry->attack();
    riverbankCavalry->attack();

    woodlandArtillery->attack();
    openFieldArtillery->attack();
    riverbankArtillery->attack();

    // Cleanup
    delete woodlandInfantry;
    delete openFieldInfantry;
    delete riverbankInfantry;
    delete woodlandCavalry;
    delete openFieldCavalry;
    delete riverbankCavalry;
    delete woodlandArtillery;
    delete openFieldArtillery;
    delete riverbankArtillery;

    delete woodlandFactory;
    delete openFieldFactory;
    delete riverbankFactory;
}

void testLegion() {
    // Create a Woodland Factory and Open Field Factory
    LegionFactory* woodlandFactory = new WoodlandFactory();
    LegionFactory* openFieldFactory = new OpenFieldFactory();

    // Create units using factories
    Infantry* woodlandInfantry = woodlandFactory->createInfantry();
    Cavalry* woodlandCavalry = woodlandFactory->createCavalry();
    Artillery* openFieldArtillery = openFieldFactory->createArtillery();

    // Create a Legion and add units
    Legion* legion = new Legion();
    legion->addUnit(woodlandInfantry);
    legion->addUnit(woodlandCavalry);
    legion->addUnit(openFieldArtillery);

    std::cout << "\nTesting legion composition and commands:" << std::endl;

    // Test moving the legion
    legion->move();

    // Test attacking with the legion
    legion->attack();

    // Test total attributes of the legion
    std::cout << "Total Mobility: " << legion->getTotalMobility() << std::endl;
    std::cout << "Total Defense: " << legion->getTotalDefense() << std::endl;
    std::cout << "Total Attack Power: " << legion->getTotalAttackPower() << std::endl;

    // Test removing units from the legion
    legion->removeUnit(woodlandInfantry);
    legion->removeUnit(woodlandCavalry);
    legion->removeUnit(openFieldArtillery);

    // Check attributes after removal
    std::cout << "After removing units:" << std::endl;
    std::cout << "Total Mobility: " << legion->getTotalMobility() << std::endl;
    std::cout << "Total Defense: " << legion->getTotalDefense() << std::endl;
    std::cout << "Total Attack Power: " << legion->getTotalAttackPower() << std::endl;

    // Cleanup
    delete legion;
    delete woodlandFactory;
    delete openFieldFactory;
}

void testStrategies() {
    // Create a Tactical Command
    TacticalCommand* command = new TacticalCommand();

    // Create strategies
    BattleStrategy* ambushStrategy = new Ambush();
    BattleStrategy* flankingStrategy = new Flanking();
    BattleStrategy* fortificationStrategy = new Fortification();

    std::cout << "\nTesting different battle strategies:" << std::endl;

    // Test Ambush strategy
    command->setStrategy(ambushStrategy);
    command->executeStrategy();

    // Test Flanking strategy
    command->setStrategy(flankingStrategy);
    command->executeStrategy();

    // Test Fortification strategy
    command->setStrategy(fortificationStrategy);
    command->executeStrategy();

    // Test strategy switching
    command->setStrategy(ambushStrategy);
    command->executeStrategy();
    command->setStrategy(flankingStrategy);
    command->executeStrategy();

    // Cleanup
    delete command;
    delete ambushStrategy;
    delete flankingStrategy;
    delete fortificationStrategy;
}

void testMementoPattern1() {
    TacticalCommand* command = new TacticalCommand();
    WarArchives* archives = new WarArchives();

    BattleStrategy* ambushStrategy = new Ambush();
    BattleStrategy* flankingStrategy = new Flanking();

    command->setStrategy(ambushStrategy);
    command->executeStrategy();
    archives->recordStrategyOutcome("Ambush", true);  // Example: Ambush succeeded

    command->setStrategy(flankingStrategy);
    command->executeStrategy();
    archives->recordStrategyOutcome("Flanking", false);  // Example: Flanking failed

    // Use the best strategy next time
    command->chooseBestStrategy();

    delete command;
    delete archives;
    delete ambushStrategy;
    delete flankingStrategy;
}

void testMementoPattern() {
    // Create a Tactical Command and War Archives
    TacticalCommand* command = new TacticalCommand();
    WarArchives* archives = new WarArchives();

    // Create strategies
    BattleStrategy* ambushStrategy = new Ambush();
    BattleStrategy* flankingStrategy = new Flanking();

    std::cout << "\nTesting the Memento pattern with battle strategies:" << std::endl;

    // Set and execute the Ambush strategy
    command->setStrategy(ambushStrategy);
    command->executeStrategy();

    // Save the Ambush strategy
    command->saveCurrentStrategy("AmbushStrategy");

    // Set and execute the Flanking strategy
    command->setStrategy(flankingStrategy);
    command->executeStrategy();

    // Save the Flanking strategy
    command->saveCurrentStrategy("FlankingStrategy");

    // Restore the Ambush strategy and execute it
    command->restoreStrategy("AmbushStrategy");
    command->executeStrategy();

    // Restore the Flanking strategy and execute it
    command->restoreStrategy("FlankingStrategy");
    command->executeStrategy();

    // Test restoring and executing multiple strategies in sequence
    command->restoreStrategy("AmbushStrategy");
    command->executeStrategy();
    command->restoreStrategy("FlankingStrategy");
    command->executeStrategy();

    // Cleanup
    delete command;
    delete archives;
    delete ambushStrategy;
    delete flankingStrategy;
}

int main() {
    testFactories();
    testLegion();
    testStrategies();
    testMementoPattern();
    testMementoPattern1();

    return 0;
}

