#include "CropFieldDecorator.h"
#include <iostream>

CropFieldDecorator::CropFieldDecorator(CropField* field)
    : decoratedField(field) {}

int CropFieldDecorator::getTotalCapacity() const {
    return decoratedField->getTotalCapacity();
}

std::string CropFieldDecorator::getCropType() const {
    return decoratedField->getCropType();
}

std::string CropFieldDecorator::getSoilStateName() const {
    return decoratedField->getSoilStateName();
}

void CropFieldDecorator::harvestCrops() {
    decoratedField->harvestCrops();
    std::cout<<"Crops harvested from "<<getCropType()<<" field."<<std::endl;
}

void CropFieldDecorator::rain() {
    decoratedField->rain();
    std::cout<<"It rained on "<<getCropType()<<" field."<<std::endl;
}

void CropFieldDecorator::storeCrops(int amount) {
    decoratedField->storeCrops(amount);
    std::cout<<amount<<" crops stored in "<<getCropType()<<" field."<<std::endl;
}

void CropFieldDecorator::increaseProduction() {
    // Default implementation does nothing; concrete decorators will override this
}

int CropFieldDecorator::getLeftoverCapacity() const {
    return decoratedField->getTotalCapacity() - decoratedField->getTotalCapacity();
}

CropFieldDecorator::~CropFieldDecorator() {
    delete decoratedField;
}

