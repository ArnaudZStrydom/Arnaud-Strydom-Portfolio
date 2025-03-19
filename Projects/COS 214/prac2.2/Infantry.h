#ifndef INFANTRY_H
#define INFANTRY_H

#include "Soldiers.h"

class Infantry : public Soldiers {
public:
    Infantry(int health, int damage, int defence, int amount);
    Soldiers* clonis() const override;

    // Override abstract methods
    void prepare() override;
    void execute() override;
    void retreat() override;
    void rest() override;
};

#endif // INFANTRY_H
