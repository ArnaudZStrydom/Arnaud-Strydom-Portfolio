// File: TurnOffAllLightsCommand.h
#ifndef TURNOFFALLLIGHTSCOMMAND_H
#define TURNOFFALLLIGHTSCOMMAND_H

#include "Command.h"
#include "Room.h"

class TurnOffAllLightsCommand : public Command {
private:
    Room* room;

public:
    TurnOffAllLightsCommand(Room* room);
    void execute() override;
};

#endif // TURNOFFALLLIGHTSCOMMAND_H
