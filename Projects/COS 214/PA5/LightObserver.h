#pragma once
#include "DeviceObserver.h"

class LightObserver : public DeviceObserver {
public:
    LightObserver(SmartDevice* light);
    
    void update() override;
};
