// Barn.cpp
#include "Barn.h"

Barn::Barn(int capacity ,std::string barnname)  {
    storageCapacity = capacity ;
    this->barnname = barnname ;
}

int Barn::getTotalCapacity() const {
    return storageCapacity;
}

std::string Barn::getName() {
    return "Barn";
}
