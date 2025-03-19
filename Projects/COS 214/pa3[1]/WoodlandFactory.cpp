#include "WoodlandFactory.h"
#include <iostream>

void WoodlandFactory::deployInfantry(Infantry* infantry) {
    std::cout << "Deploying Woodland infantry with mobility " << infantry->getMobility() <<" and attack power "  << infantry->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field infantry
}

void WoodlandFactory::deployCavalry(Cavalry* cavalry) {
    std::cout << "Deploying Woodland cavalry with mobility " << cavalry->getMobility() <<" and attack power " << cavalry->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field cavalry
}

void WoodlandFactory::deployArtillery(Artillery* artillery) {
    std::cout << "Deploying Woodland cavalry with mobility " << artillery->getMobility() <<" and attack power " << artillery->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field artillery
}
