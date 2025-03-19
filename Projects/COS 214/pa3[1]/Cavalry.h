#ifndef CAVALRY_H
#define CAVALRY_H

#include "LegionUnit.h"

class Cavalry : public LegionUnit {
public:
    void move() override;
    void attack() override;
};

// Terrain-specific subclasses
class WoodlandCavalry : public Cavalry {
public:
    WoodlandCavalry();
};

class RiverbankCavalry : public Cavalry {
public:
    RiverbankCavalry();
};

class OpenFieldCavalry : public Cavalry {
public:
    OpenFieldCavalry();
};

#endif // CAVALRY_H

