#ifndef WARARCHIVES_H
#define WARARCHIVES_H

#include "TacticalMemento.h"
#include <string>
#include <map>

class WarArchives {
private:
    std::map<std::string, TacticalMemento*> mementos;

public:
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
            delete pair.second;
        }
        mementos.clear();
    }
};

#endif // WARARCHIVES_H


