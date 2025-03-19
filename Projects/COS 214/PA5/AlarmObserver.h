#pragma once
#include "DeviceObserver.h"

class AlarmObserver : public DeviceObserver {
public:
    AlarmObserver(SmartDevice* alarm);
    
    void update() override;
};
