#ifndef MEMENTO_H
#define MEMENTO_H

#include <string>

class Memento {
private:
    int healthPerSoldier;
    int damagePerSoldier;
    int defencePerSoldier;
    int amountOfSoldiersPerUnit;
    std::string unitName;

public:
    Memento(int value1, int value2, int value3, int value4, std::string value5);

    // Getter methods
    int getHealthPerSoldier() const;
    int getDamagePerSoldier() const;
    int getDefencePerSoldier() const;
    int getAmountOfSoldiersPerUnit() const;
    std::string getUnitName() const;
    ~Memento() = default;
};

#endif