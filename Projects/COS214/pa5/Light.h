#pragma once
#include "SmartDevice.h"

class Light : public SmartDevice {
public:
    Light();

    std::string getDeviceType() const override;
    void performAction(const std::string& action) override;
};


