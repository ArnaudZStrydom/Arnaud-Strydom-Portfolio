// File: LegacyThermostat.cpp
#include "LegacyThermostat.h"

LegacyThermostat::LegacyThermostat() {
    isOn = false ;
    currentTemperature = 20.0;
}  // Default temperature 20.0°C

void LegacyThermostat::turnOn() {
    isOn = true;
}

void LegacyThermostat::turnOff() {
    isOn = false;
}

bool LegacyThermostat::isTOn() const {
    return isOn;
}

void LegacyThermostat::setLegacyTemperature(double temperature) {
    currentTemperature = temperature;
}

double LegacyThermostat::getLegacyTemperature() const {
    return currentTemperature;
}
