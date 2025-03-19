#include "AlarmObserver.h"
#include <iostream>

AlarmObserver::AlarmObserver(SmartDevice* alarm) : DeviceObserver(alarm) {}

void AlarmObserver::update() {
    std::cout << "AlarmObserver: Alarm status changed to " << device->getStatus() << std::endl;
}
