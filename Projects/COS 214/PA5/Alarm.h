#pragma once
#include "SmartDevice.h"

class Alarm : public SmartDevice {
public:
    
    Alarm();
    std::string getDeviceType() const override;
    void performAction(const std::string& action) override;
};
