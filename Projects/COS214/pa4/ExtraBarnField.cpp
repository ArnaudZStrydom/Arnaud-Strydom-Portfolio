#include "ExtraBarnField.h"

ExtraBarnField::ExtraBarnField(CropField* field, int extraCapacity)
    : CropFieldDecorator(field), additionalCapacity(extraCapacity) {}

int ExtraBarnField::getTotalCapacity() const {
    return decoratedField->getTotalCapacity() + additionalCapacity;
}

int ExtraBarnField::getLeftoverCapacity() const {
    int leftover = decoratedField->getTotalCapacity() - decoratedField->getTotalCapacity();
    return leftover > 0 ? leftover + additionalCapacity : additionalCapacity;
}

