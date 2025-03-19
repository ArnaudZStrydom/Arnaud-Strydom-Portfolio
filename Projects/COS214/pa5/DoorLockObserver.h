#pragma once
#include "DeviceObserver.h"

class DoorLockObserver : public DeviceObserver {
public:
    DoorLockObserver(SmartDevice* alarm);
    
    void update() override;
};
