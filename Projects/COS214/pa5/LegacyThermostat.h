// File: LegacyThermostat.h
#ifndef LEGACYTHERMOSTAT_H
#define LEGACYTHERMOSTAT_H

class LegacyThermostat {
private:
    bool isOn;
    double currentTemperature;

public:
    LegacyThermostat();  // Constructor
    
    void turnOn();  // Turn on the legacy thermostat.
    void turnOff();  // Turn off the legacy thermostat.
    bool isTOn() const;  // Check if the thermostat is on.
    
    void setLegacyTemperature(double temperature);  // Set the temperature (legacy method).
    double getLegacyTemperature() const;  // Get the current temperature (legacy method).
};

#endif // LEGACYTHERMOSTAT_H
