// File: TurnOffAllLightsCommand.cpp
#include "TurnOffAllLightsCommand.h"

TurnOffAllLightsCommand::TurnOffAllLightsCommand(Room* room) : room(room) {}

void TurnOffAllLightsCommand::execute() {
    room->performAction("Lights off");  // Turns off all lights in the room.
}
