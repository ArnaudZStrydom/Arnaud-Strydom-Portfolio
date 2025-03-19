#ifndef BOATMAN_H
#define BOATMAN_H

#include "Soldiers.h"

class Boatman : public Soldiers {
public:
    Boatman(int health, int damage, int defence, int amount);
    Soldiers* clonis() const override;

    // Override abstract methods
    void prepare() override;
    void execute() override;
    void retreat() override;
    void rest() override;
};

#endif // BOATMAN_H
