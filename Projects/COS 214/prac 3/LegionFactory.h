#ifndef LEGIONFACTORY_H
#define LEGIONFACTORY_H

class Infantry;
class Cavalry;
class Artillery;

class LegionFactory {
public:
    virtual Infantry* createInfantry() = 0;
    virtual Cavalry* createCavalry() = 0;
    virtual Artillery* createArtillery() = 0;

    virtual void deployInfantry(Infantry* infantry) = 0;
    virtual void deployCavalry(Cavalry* cavalry) = 0;
    virtual void deployArtillery(Artillery* artillery) = 0;

    virtual ~LegionFactory() = default;
};

#endif // LEGIONFACTORY_H
