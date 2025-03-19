#ifndef ARTILLERY_H
#define ARTILLERY_H

#include "LegionUnit.h"

class Artillery : public LegionUnit {
public:
    void move() override;
    void attack() override;
};

// Terrain-specific subclasses
class WoodlandArtillery : public Artillery {
public:
    WoodlandArtillery();
};

class RiverbankArtillery : public Artillery {
public:
    RiverbankArtillery();
};

class OpenFieldArtillery : public Artillery {
public:
    OpenFieldArtillery();
};

#endif // ARTILLERY_H

