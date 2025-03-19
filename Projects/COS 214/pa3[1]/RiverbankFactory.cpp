#include "RiverbankFactory.h"
#include <iostream>

void RiverbankFactory::deployInfantry(Infantry* infantry) {
    std::cout << "Deploying River bank infantry with mobility " << infantry->getMobility() <<" and attack power " << infantry->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field infantry
}

void RiverbankFactory::deployCavalry(Cavalry* cavalry) {
    std::cout << "Deploying River bank cavalry with mobility " << cavalry->getMobility() <<" and attack power " << cavalry->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field cavalry
}

void RiverbankFactory::deployArtillery(Artillery* artillery) {
    std::cout << "Deploying River bank cavalry with mobility " << artillery->getMobility() <<" and attack power " << artillery->getAttackPower()<< "." << std::endl;
    // Deployment-specific tasks for open field artillery
}

