#ifndef BATTLESTRATEGY_H
#define BATTLESTRATEGY_H

class BattleStrategy {
public:
    virtual void engage() = 0;  // Engagement protocol
    virtual ~BattleStrategy() = default;
};

#endif // BATTLESTRATEGY_H
