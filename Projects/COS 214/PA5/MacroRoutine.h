// File: MacroRoutine.h
#ifndef MACROROUTINE_H
#define MACROROUTINE_H

#include <vector>
#include "Command.h"

class MacroRoutine {
private:
    std::vector<Command*> commands;

public:
    void addProcedure(Command* command);  // Add a command to the macro routine.
    void removeProcedure(Command* command);  // Remove a command from the macro routine.
    void execute();  // Execute all commands in the routine.
};

#endif // MACROROUTINE_H
