#ifndef LEGION_H
#define LEGION_H

#include <vector>
#include "LegionUnit.h"

class Legion : public LegionUnit {
private:
    std::vector<LegionUnit*> units;

public:
    // Add a unit to the legion
    void addUnit(LegionUnit* unit);

    // Remove a unit from the legion
    void removeUnit(LegionUnit* unit);

    // Command all units in the legion to move
    void move() override;

    // Command all units in the legion to attack
    void attack() override;

    // Calculate overall stats for the legion
    int getTotalMobility() const;
    int getTotalDefense() const;
    int getTotalAttackPower() const;

    // Destructor to clean up allocated units
    ~Legion();
};

#endif // LEGION_H
