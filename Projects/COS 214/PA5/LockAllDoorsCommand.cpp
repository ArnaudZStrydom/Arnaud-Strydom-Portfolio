// File: LockAllDoorsCommand.cpp
#include "LockAllDoorsCommand.h"

LockAllDoorsCommand::LockAllDoorsCommand(Room* room) : room(room) {}

void LockAllDoorsCommand::execute() {
    room->performAction("Doors Locked");  // Locks all doors in the room.
}
