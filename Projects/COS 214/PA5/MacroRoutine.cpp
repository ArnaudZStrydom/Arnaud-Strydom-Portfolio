// File: MacroRoutine.cpp
#include "MacroRoutine.h"
#include <algorithm>  // for std::remove

void MacroRoutine::addProcedure(Command* command) {
    commands.push_back(command);
}

void MacroRoutine::removeProcedure(Command* command) {
    // Removes the command from the list.
    commands.erase(std::remove(commands.begin(), commands.end(), command), commands.end());
}

void MacroRoutine::execute() {
    for (auto* command : commands) {
        command->execute();
    }
}
