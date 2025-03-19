#ifndef WARARCHIVES_H
#define WARARCHIVES_H

#include "TacticalMemento.h"
#include <string>
#include <map>
#include <unordered_map>
#include <vector>

class WarArchives {
private:
    std::map<std::string, TacticalMemento*> mementos;
    std::unordered_map<std::string, int> strategySuccesses; // Maps strategy names to success counts
    std::unordered_map<std::string, int> strategyAttempts;

public:

void recordStrategyOutcome(const std::string& strategyName, bool success){
    if (strategyAttempts.find(strategyName) == strategyAttempts.end()) {
        strategyAttempts[strategyName] = 0;
        strategySuccesses[strategyName] = 0;
    }
    strategyAttempts[strategyName]++;
    if (success) {
        strategySuccesses[strategyName]++;
    }
}

    // Analyze and return the best strategy
std::string getBestStrategy(){
    double bestSuccessRate = 0.0;
    std::string bestStrategy = "";

    for (const auto& entry : strategyAttempts) {
        const std::string& strategyName = entry.first;
        int attempts = entry.second;
        int successes = strategySuccesses[strategyName];

        double successRate = static_cast<double>(successes) / attempts;

        if (successRate > bestSuccessRate) {
            bestSuccessRate = successRate;
            bestStrategy = strategyName;
        }
    }

    return bestStrategy.empty() ? "Ambush" : bestStrategy;
}
    // Add a memento with a label
    void addTacticalMemento(const std::string& label, TacticalMemento* memento) {
        mementos[label] = memento;
    }

    // Remove a memento by label
    void removeTacticalMemento(const std::string& label) {
        if (mementos.find(label) != mementos.end()) {
            delete mementos[label];
            mementos.erase(label);
        }
    }

    // Get a memento by label
    TacticalMemento* getTacticalMemento(const std::string& label) const {
        auto it = mementos.find(label);
        if (it != mementos.end()) {
            return it->second;
        }
        return nullptr;
    }

    ~WarArchives() {
        for (auto& pair : mementos) {
            if(pair.second != nullptr){
                delete pair.second;
            }
            
        }
        mementos.clear();
    }
};

#endif // WARARCHIVES_H


