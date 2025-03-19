#ifndef CROPFIELDDECORATOR_H
#define CROPFIELDDECORATOR_H

#include "CropField.h"

class CropFieldDecorator : public CropField {
protected:
    CropField* decoratedField;

public:
    CropFieldDecorator(CropField* field);

    int getTotalCapacity() const override;
    std::string getCropType() const override;
    std::string getSoilStateName() const override;

    void harvestCrops() override;
    void rain() override;
    void storeCrops(int amount) override;

    // New methods for increasing production and getting leftover capacity
    virtual void increaseProduction();
    virtual int getLeftoverCapacity() const;

    ~CropFieldDecorator();
};

#endif // CROPFIELDDECORATOR_H

