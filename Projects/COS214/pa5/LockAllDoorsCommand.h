// File: LockAllDoorsCommand.h
#ifndef LOCKALLDOORSCOMMAND_H
#define LOCKALLDOORSCOMMAND_H

#include "Command.h"
#include "Room.h"

class LockAllDoorsCommand : public Command {
private:
    Room* room;

public:
    LockAllDoorsCommand(Room* room);
    void execute() override;
};

#endif // LOCKALLDOORSCOMMAND_H
