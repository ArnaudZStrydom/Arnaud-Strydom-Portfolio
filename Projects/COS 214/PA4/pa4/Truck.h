#ifndef TRUCK_H
#define TRUCK_H

#include <string>

class CropField;

class Truck {
protected:
    std::string type;

public:
    Truck(const std::string& truckType);
    virtual ~Truck() = default;

    virtual void startEngine() const = 0;  // Pure virtual function to start the truck
    virtual void performOperation(CropField* field) = 0;  // Operation the truck performs
    std::string getType()  ;
};

#endif // TRUCK_H
