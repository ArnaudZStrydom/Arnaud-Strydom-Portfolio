// File: SmartThermostatIntegrator.h
#ifndef SMARTTHERMOSTATINTEGRATOR_H
#define SMARTTHERMOSTATINTEGRATOR_H

#include "SmartDevice.h"
#include "LegacyThermostat.h"  // This will represent the legacy thermostat.

class SmartThermostatIntegrator : public SmartDevice {
private:
    LegacyThermostat* legacyThermostat;  // The legacy thermostat being adapted.

public:
    SmartThermostatIntegrator(LegacyThermostat* legacyDevice);

    std::string getStatus() const override;
    void performAction(const std::string& action) override;
    std::string getDeviceType() const override;

    void setTemperature(double temperature);  // Set temperature on the adapted device.
    double getTemperature() const;  // Get current temperature from the adapted device.
};

#endif // SMARTTHERMOSTATINTEGRATOR_H
