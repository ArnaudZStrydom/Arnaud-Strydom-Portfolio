#pragma once
#include "SmartDevice.h"

class DoorLock : public SmartDevice {
public:
    DoorLock();

    std::string getDeviceType() const override;
    void performAction(const std::string& action) override;
};


