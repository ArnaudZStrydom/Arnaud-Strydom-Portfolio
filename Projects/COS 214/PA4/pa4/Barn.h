// Barn.h
#ifndef BARN_H
#define BARN_H

#include "FarmUnit.h"


class Barn : public FarmUnit {
private:
    int storageCapacity;
    std::string barnname ;
public:
    Barn(int capacity , std::string barnname);
    std::vector<FarmUnit*> getAdjacentFarms() override {
        // Default behavior or empty implementation
        return std::vector<FarmUnit*>{};
    }
    int getTotalCapacity() const override;
    std::string getName()  override;
};

#endif // BARN_H

