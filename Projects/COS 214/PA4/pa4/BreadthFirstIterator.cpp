#include "BreadthFirstIterator.h"

BreadthFirstIterator::BreadthFirstIterator(FarmUnit* rootFarm) : current(nullptr) {
    if (rootFarm) {
        farmQueue.push(rootFarm);
    }
}

void BreadthFirstIterator::firstFarm() {
    if (!farmQueue.empty()) {
        current = farmQueue.front();
        farmQueue.pop();
        // Push adjacent farms to the queue for traversal
        for (FarmUnit* adjacentFarm : current->getAdjacentFarms()) {
            farmQueue.push(adjacentFarm);
        }
    } else {
        current = nullptr;
    }
}

void BreadthFirstIterator::next() {
    if (!farmQueue.empty()) {
        current = farmQueue.front();
        farmQueue.pop();
        // Push adjacent farms to the queue
        for (FarmUnit* adjacentFarm : current->getAdjacentFarms()) {
            farmQueue.push(adjacentFarm);
        }
    } else {
        current = nullptr;
    }
}

bool BreadthFirstIterator::isDone() const {
    return current == nullptr && farmQueue.empty();
}

FarmUnit* BreadthFirstIterator::currentFarm() const {
    return current;
}
