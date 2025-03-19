#ifndef FARMTRAVERSER_H
#define FARMTRAVERSER_H

#include "FarmIterator.h"
#include "FarmUnit.h"
#include "BreadthFirstIterator.h"
#include "DepthFirstIterator.h"

class FarmTraverser {
public:
    enum TraversalType { BREADTH_FIRST, DEPTH_FIRST };

    static FarmIterator* createIterator(TraversalType type, FarmUnit* rootFarm) {
        switch (type) {
            case BREADTH_FIRST:
                return new BreadthFirstIterator(rootFarm);
            case DEPTH_FIRST:
                return new DepthFirstIterator(rootFarm);
            default:
                return nullptr;
        }
    }
};

#endif // FARMTRAVERSER_H
