// File: Command.h
#ifndef COMMAND_H
#define COMMAND_H

class Command {
public:
    virtual void execute() = 0;  // Executes the command.
    virtual ~Command() = default;
};

#endif // COMMAND_H

