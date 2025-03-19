#ifndef RIVERBANKFACTORY_H
#define RIVERBANKFACTORY_H

#include "LegionFactory.h"
#include "Infantry.h"
#include "Cavalry.h"
#include "Artillery.h"

class RiverbankFactory : public LegionFactory {
public:
    Infantry* createInfantry() override {
        return new RiverbankInfantry(); // Specialized for riverbank
    }
    Cavalry* createCavalry() override {
        return new RiverbankCavalry(); // Specialized for riverbank
    }
    Artillery* createArtillery() override {
        return new RiverbankArtillery(); // Specialized for riverbank
    }

    void deployInfantry(Infantry* infantry) override;
    void deployCavalry(Cavalry* cavalry) override;
    void deployArtillery(Artillery* artillery) override;
};

#endif // RIVERBANKFACTORY_H


