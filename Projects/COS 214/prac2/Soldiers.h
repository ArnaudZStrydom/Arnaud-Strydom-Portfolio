#ifndef SOLDIERS_H
#define SOLDIERS_H

#include <string>
using namespace std;

class Soldiers {
protected:
    int healthPerSoldier;
    int damagePerSoldier;
    int defencePerSoldier;
    int amountOfSoldiersPerUnit;
    string unitName;

public:
    Soldiers(int health, int damage, int defence, int amount, const string& name);
    virtual Soldiers* clonis() const = 0;

    // Getter methods
    int getHealthPerSoldier() const;
    int getDamagePerSoldier() const;
    int getDefencePerSoldier() const;
    int getAmountOfSoldiersPerUnit() const;
    string getUnitName() const;


	// Template methods
    void engage();
    void disengage();

	void attack(Soldiers* target);
    void defend(int damage);

    // Abstract methods
    virtual void prepare() = 0;
    virtual void execute() = 0;
    virtual void retreat() = 0;
    virtual void rest() = 0;

	//destructor
    virtual ~Soldiers() {}
};

#endif // SOLDIERS_H
