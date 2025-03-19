// File: Room.h
#ifndef ROOM_H
#define ROOM_H

#include <vector>
#include <string>
#include "SmartDevice.h"

class Room {
private:
    std::string roomName;
    std::vector<SmartDevice*> devices;  // List of smart devices in the room.

public:
    Room(const std::string& name);

    void addDevice(SmartDevice* device);  // Add a device to the room.
    void performAction(const std::string& action);  // Perform an action on all devices in the room.
    std::string getRoomName() const;  // Returns the room name.
    void listDevices() const;  // Lists the current status of all devices in the room.
     std::vector<SmartDevice*> getDevices();
};

#endif // ROOM_H
