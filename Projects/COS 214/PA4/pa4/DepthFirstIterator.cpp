#include "DepthFirstIterator.h"

DepthFirstIterator::DepthFirstIterator(FarmUnit* rootFarm) : current(nullptr) {
    if (rootFarm) {
        farmStack.push(rootFarm);
    }
}

void DepthFirstIterator::firstFarm() {
    if (!farmStack.empty()) {
        current = farmStack.top();
        farmStack.pop();
        // Push adjacent farms to the stack for traversal
        for (FarmUnit* adjacentFarm : current->getAdjacentFarms()) {
            farmStack.push(adjacentFarm);
        }
    } else {
        current = nullptr;
    }
}

void DepthFirstIterator::next() {
    if (!farmStack.empty()) {
        current = farmStack.top();
        farmStack.pop();
        // Push adjacent farms to the stack
        for (FarmUnit* adjacentFarm : current->getAdjacentFarms()) {
            farmStack.push(adjacentFarm);
        }
    } else {
        current = nullptr;
    }
}

bool DepthFirstIterator::isDone() const {
    return current == nullptr && farmStack.empty();
}

FarmUnit* DepthFirstIterator::currentFarm() const {
    return current;
}
