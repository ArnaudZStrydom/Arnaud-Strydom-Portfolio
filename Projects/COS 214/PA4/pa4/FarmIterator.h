#ifndef FARMITERATOR_H
#define FARMITERATOR_H

#include "FarmUnit.h"

class FarmIterator {
public:
    virtual ~FarmIterator() = default;

    virtual void firstFarm() = 0;       // Initialize traversal
    virtual void next() = 0;            // Move to the next farm
    virtual bool isDone() const = 0;    // Check if traversal is complete
    virtual FarmUnit* currentFarm() const = 0; // Return the current farm
};

#endif // FARMITERATOR_H
