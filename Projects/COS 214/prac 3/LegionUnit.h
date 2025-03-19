#ifndef LEGIONUNIT_H
#define LEGIONUNIT_H

class LegionUnit {
protected:
    int mobility;
    int defense;
    int attackPower;

public:
    virtual void move() = 0;
    virtual void attack() = 0;
    
    // Terrain-specific attributes setters and getters
    void setMobility(int m);
    int getMobility() const;

    void setDefense(int d);
    int getDefense() const;

    void setAttackPower(int ap);
    int getAttackPower() const;

    virtual ~LegionUnit() = default;
};

#endif // LEGIONUNIT_H

