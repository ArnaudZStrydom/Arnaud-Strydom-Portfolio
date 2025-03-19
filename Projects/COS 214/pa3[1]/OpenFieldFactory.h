#ifndef OPENFIELDFACTORY_H
#define OPENFIELDFACTORY_H

#include "LegionFactory.h"
#include "Infantry.h"
#include "Cavalry.h"
#include "Artillery.h"

class OpenFieldFactory : public LegionFactory {
public:
    Infantry* createInfantry() override {
        return new OpenFieldInfantry(); // Specialized for open field
    }
    Cavalry* createCavalry() override {
        return new OpenFieldCavalry(); // Specialized for open field
    }
    Artillery* createArtillery() override {
        return new OpenFieldArtillery(); // Specialized for open field
    }

    void deployInfantry(Infantry* infantry) override;
    void deployCavalry(Cavalry* cavalry) override;
    void deployArtillery(Artillery* artillery) override;
};

#endif // OPENFIELDFACTORY_H


