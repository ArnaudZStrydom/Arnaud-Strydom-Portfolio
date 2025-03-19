// File: SmartThermostatIntegrator.cpp
#include "SmartThermostatIntegrator.h"
#include <iostream>

SmartThermostatIntegrator::SmartThermostatIntegrator(LegacyThermostat* legacyDevice)
    : legacyThermostat(legacyDevice) {}

std::string SmartThermostatIntegrator::getStatus() const {
    return legacyThermostat->isTOn() ? "on" : "off";  // Checking if the legacy device is on.
}

void SmartThermostatIntegrator::performAction(const std::string& action) {
    if (action == "TurnOn") {
        legacyThermostat->turnOn();
        
    } else if (action == "TurnOff") {
        legacyThermostat->turnOff();
    }
}

std::string SmartThermostatIntegrator::getDeviceType() const {
    return "SmartThermostatIntegrator";
}

void SmartThermostatIntegrator::setTemperature(double temperature) {
    legacyThermostat->setLegacyTemperature(temperature);  // Set temperature on legacy device.
}

double SmartThermostatIntegrator::getTemperature() const {
    return legacyThermostat->getLegacyTemperature();  // Get current temperature from legacy device.
}
