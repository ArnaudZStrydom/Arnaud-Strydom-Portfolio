#ifndef WOODLANDFACTORY_H
#define WOODLANDFACTORY_H

#include "LegionFactory.h"
#include "Infantry.h"
#include "Cavalry.h"
#include "Artillery.h"

class WoodlandFactory : public LegionFactory {
public:
    Infantry* createInfantry() override {
        return new WoodlandInfantry(); // Specialized for woodland
    }
    Cavalry* createCavalry() override {
        return new WoodlandCavalry(); // Specialized for woodland
    }
    Artillery* createArtillery() override {
        return new WoodlandArtillery(); // Specialized for woodland
    }

    void deployInfantry(Infantry* infantry) override;
    void deployCavalry(Cavalry* cavalry) override;
    void deployArtillery(Artillery* artillery) override;
};

#endif // WOODLANDFACTORY_H

