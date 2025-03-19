#ifndef SOILSTATE_H
#define SOILSTATE_H

#include <string>

class CropField;


class SoilState {
public:
    virtual ~SoilState() = default;
    virtual std::string getName() const = 0;
    virtual int harvestCrops() const = 0;
    virtual void rain(CropField* field) = 0;  // Allows state transitions
};

#endif // SOILSTATE_H
