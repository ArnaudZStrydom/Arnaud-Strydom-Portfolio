#include "OpenFieldFactory.h"
#include <iostream>

void OpenFieldFactory::deployInfantry(Infantry* infantry) {
    std::cout << "Deploying open field infantry with mobility " << infantry->getMobility() <<" and attack power " << infantry->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field infantry
}

void OpenFieldFactory::deployCavalry(Cavalry* cavalry) {
    std::cout << "Deploying open field cavalry with mobility " << cavalry->getMobility() <<" and attack power " << cavalry->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field cavalry
}

void OpenFieldFactory::deployArtillery(Artillery* artillery) {
    std::cout << "Deploying open field cavalry with mobility " << artillery->getMobility() <<" and attack power " << artillery->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field artillery
}



