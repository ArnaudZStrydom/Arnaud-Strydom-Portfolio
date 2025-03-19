#include "Truck.h"

Truck::Truck(const std::string& truckType) : type(truckType) {}

std::string Truck::getType(){
    return this->type;
}

// No implementation needed for pure virtual functions
