#ifndef UNITCOMPONENT_H
#define UNITCOMPONENT_H

class UnitComponent {
public:
    virtual void move() = 0;
    virtual void fight() = 0;
    virtual void add(UnitComponent* component) { /* Default empty implementation */ }
    virtual void remove(UnitComponent* component) { /* Default empty implementation */ }
    virtual ~UnitComponent() = default;
};

#endif // UNITCOMPONENT_H
