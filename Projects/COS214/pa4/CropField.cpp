#include "CropField.h"
#include "SoilState.h"
#include "DrySoil.h"
#include<iostream>
CropField::CropField(const std::string& type, int capacity , std::string fname)
    : cropType(type), totalCapacity(capacity), currentStoredCrops(0), soilState(new DrySoil()) , farmName(fname) {}

CropField::~CropField() {
    delete soilState;
}

int CropField::getTotalCapacity() const {
    return totalCapacity;
}

std::string CropField::getCropType() const {
    return cropType;
}

std::string CropField::getSoilStateName() const {
    return soilState ? soilState->getName() : "No State";
}

void CropField::setSoilState(SoilState* newState) {
    delete soilState;
    soilState = newState;
}

void CropField::addAdjacentFarm(FarmUnit* farm) {
    adjacentFarms.push_back(farm);
}

std::vector<FarmUnit*> CropField::getAdjacentFarms() {
    return adjacentFarms;
}

void CropField::storeCrops(int amount) {
    if (currentStoredCrops + amount <= totalCapacity) {
        currentStoredCrops += amount;
        std::cout<<"Stored "<<amount<<" crops in "<<farmName<<std::endl;
    } else {
        currentStoredCrops = totalCapacity;
        std::cout<<"Capacity reached for "<<farmName<<std::endl;
    }
}

void CropField::harvestCrops() {
    if (soilState) {
        int harvestedAmount = soilState->harvestCrops();
        std::cout<<"Harvested "<<harvestedAmount<<" crops from "<<farmName<<std::endl;
        storeCrops(harvestedAmount);
        std::cout<<"Current stored crops: "<<currentStoredCrops<<std::endl;
    }
}

void CropField::rain() {
    if (soilState) {
        soilState->rain(this);
        std::cout<<"Rained on "<<farmName<<std::endl;
    }
}

int CropField::getLeftoverCapacity() const {
    return totalCapacity - currentStoredCrops;  // Basic implementation for non-decorated CropField
}

std::string CropField::getName(){
    return farmName ;
}

