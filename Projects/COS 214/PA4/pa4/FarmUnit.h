#ifndef FARMUNIT_H
#define FARMUNIT_H

#include <string>
#include <vector>

#include "Truck.h"
class CropField;

class FarmUnit {
protected:
    std::vector<Truck*> trucks;  // List of trucks owned by the farm unit

public:
    virtual ~FarmUnit();

    virtual int getTotalCapacity() const = 0;
    virtual std::string getName() = 0;

    virtual std::vector<FarmUnit*> getAdjacentFarms() = 0;

    // Truck management methods
    void buyTruck(Truck* truck);
    void sellTruck(Truck* truck);
    void callTruck(CropField* field, Truck* truck);  // Use of CropField pointer

    // Method to notify trucks based on conditions
    void notifyTrucks(CropField* field);

    // Methods for demonstration purposes
    void listTrucks() const;
};

#endif // FARMUNIT_H


