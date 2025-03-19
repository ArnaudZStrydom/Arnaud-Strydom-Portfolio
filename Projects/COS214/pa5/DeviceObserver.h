#pragma once
#include "SmartDevice.h"

class DeviceObserver {
protected:
    SmartDevice* device;  // The device this observer is monitoring

public:
    DeviceObserver(SmartDevice* dev);
    virtual ~DeviceObserver() = default;
    
    virtual void update() = 0;  // Pure virtual function to be implemented by subclasses
};

