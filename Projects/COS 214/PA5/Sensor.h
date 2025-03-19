// File: Sensor.h
#ifndef SENSOR_H
#define SENSOR_H

#include <vector>
#include "SmartDevice.h"

class Sensor {
private:
    std::vector<SmartDevice*> connectedDevices;  // List of devices to notify.

public:
    void addDevice(SmartDevice* device);  // Adds a device to the sensor.
    void removeDevice(SmartDevice* device);  // Removes a device from the sensor.
    void notifyDevices(const std::string& event);  // Notifies all connected devices.
    void detectMovement();
};

#endif // SENSOR_H
