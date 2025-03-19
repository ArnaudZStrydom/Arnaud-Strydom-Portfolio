// File: Thermostat.h
#ifndef THERMOSTAT_H
#define THERMOSTAT_H

#include "SmartDevice.h"

class Thermostat : public SmartDevice {
private:
    bool isOn;

public:
    Thermostat();  // Constructor
    std::string getStatus() const override;
    void performAction(const std::string& action) override;
    std::string getDeviceType() const override;
};

#endif // THERMOSTAT_H
