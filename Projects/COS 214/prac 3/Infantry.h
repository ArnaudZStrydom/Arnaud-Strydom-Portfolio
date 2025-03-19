#ifndef INFANTRY_H
#define INFANTRY_H

#include "LegionUnit.h"

class Infantry : public LegionUnit {
public:
    void move() override;
    void attack() override;
};

// Terrain-specific subclasses
class WoodlandInfantry : public Infantry {
public:
    WoodlandInfantry();
};

class RiverbankInfantry : public Infantry {
public:
    RiverbankInfantry();
};

class OpenFieldInfantry : public Infantry {
public:
    OpenFieldInfantry();
};

#endif // INFANTRY_H

