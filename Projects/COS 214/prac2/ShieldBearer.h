#ifndef SHIELDBEARER_H
#define SHIELDBEARER_H

#include "Soldiers.h"

class ShieldBearer : public Soldiers {
public:
    ShieldBearer(int health, int damage, int defence, int amount);
    Soldiers* clonis() const override;

    // Override abstract methods
    void prepare() override;
    void execute() override;
    void retreat() override;
    void rest() override;
};

#endif // SHIELDBEARER_H
