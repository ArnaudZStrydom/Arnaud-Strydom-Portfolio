#ifndef CROPFIELD_H
#define CROPFIELD_H

#include "FarmUnit.h"
#include "SoilState.h"
#include <string>
#include <vector>

class CropField : public FarmUnit {
private:
    std::string cropType;
    std::string farmName;
    int totalCapacity;
    int currentStoredCrops;
    SoilState* soilState;
    std::vector<FarmUnit*> adjacentFarms;

public:

    CropField(){
        cropType = "Miellies";
        totalCapacity = 150 ;
        farmName = "Jan se plaas";
    }
    CropField(const std::string& type, int capacity , std::string);
    virtual ~CropField();

    virtual int getTotalCapacity() const override;
    virtual std::string getCropType() const;
    virtual std::string getSoilStateName() const;
    void setSoilState(SoilState* newState);
     std::string getName() override;
    void addAdjacentFarm(FarmUnit* farm);
    std::vector<FarmUnit *> getAdjacentFarms() override;

    virtual void storeCrops(int amount);
    virtual void harvestCrops();
    virtual void rain();

    // Virtual method to be overridden by decorators
    virtual int getLeftoverCapacity() const;
};

#endif // CROPFIELD_H



