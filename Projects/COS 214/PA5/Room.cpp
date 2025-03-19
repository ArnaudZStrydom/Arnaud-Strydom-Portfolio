// File: Room.cpp
#include "Room.h"
#include <iostream>

Room::Room(const std::string& name) : roomName(name) {}

void Room::addDevice(SmartDevice* device) {
    devices.push_back(device);
}

void Room::performAction(const std::string& action) {
    for (auto* device : devices) {
        device->performAction(action);
    }
}

std::string Room::getRoomName() const {
    return roomName;
}

void Room::listDevices() const {
    std::cout << "Devices in room: " << roomName << "\n";
    for (const auto* device : devices) {
        std::cout << "  - " << device->getDeviceType() << ": " << device->getStatus() << "\n";
    }
}

std::vector<SmartDevice*> Room::getDevices(){
    return devices;
}
