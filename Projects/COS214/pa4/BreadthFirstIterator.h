#ifndef BREADTHFIRSTITERATOR_H
#define BREADTHFIRSTITERATOR_H

#include "FarmIterator.h"
#include <queue>

class BreadthFirstIterator : public FarmIterator {
private:
    std::queue<FarmUnit*> farmQueue;  // Queue for BFS traversal
    FarmUnit* current;

public:
    BreadthFirstIterator(FarmUnit* rootFarm);

    void firstFarm() override;
    void next() override;
    bool isDone() const override;
    FarmUnit* currentFarm() const override;
};

#endif // BREADTHFIRSTITERATOR_H
