// File: Sensor.cpp
#include "Sensor.h"
#include <algorithm>  // for std::remove
#include <iostream>

void Sensor::addDevice(SmartDevice* device) {
    connectedDevices.push_back(device);
}

void Sensor::removeDevice(SmartDevice* device) {
    // Use std::remove to move the matching device to the end, and erase it
    connectedDevices.erase(
        std::remove(connectedDevices.begin(), connectedDevices.end(), device),
        connectedDevices.end()
    );
}

void Sensor::notifyDevices(const std::string& event) {
    for (auto* device : connectedDevices) {
        device->performAction(event);  // Notify each device with the event.
    }
}

void Sensor::detectMovement() {
    std::cout << "Sensor detected movement. Triggering devices..." << std::endl;

    // Loop through all connected devices and trigger their action
    for (SmartDevice* device : connectedDevices) {
        device->performAction("Lights On , Doors Locked , Alarm On");  // Change the state of the device (e.g., turn on light, lock door)
    }
}
