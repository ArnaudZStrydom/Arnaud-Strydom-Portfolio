#ifndef DEPTHFIRSTITERATOR_H
#define DEPTHFIRSTITERATOR_H

#include "FarmIterator.h"
#include <stack>

class DepthFirstIterator : public FarmIterator {
private:
    std::stack<FarmUnit*> farmStack;  // Stack for DFS traversal
    FarmUnit* current;

public:
    DepthFirstIterator(FarmUnit* rootFarm);

    void firstFarm() override;
    void next() override;
    bool isDone() const override;
    FarmUnit* currentFarm() const override;
};

#endif // DEPTHFIRSTITERATOR_H
