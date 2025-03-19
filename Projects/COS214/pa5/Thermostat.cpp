// File: Thermostat.cpp
#include "Thermostat.h"

Thermostat::Thermostat() : isOn(false) {}  // Initially, the thermostat is off.

std::string Thermostat::getStatus() const {
    return isOn ? "on" : "off";
}

void Thermostat::performAction(const std::string& action) {
    if (action == "TurnOn") {
        isOn = true;
    } else if (action == "TurnOff") {
        isOn = false;
    }
}

std::string Thermostat::getDeviceType() const {
    return "Thermostat";
}
