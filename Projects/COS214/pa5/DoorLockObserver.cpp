#include "DoorLockObserver.h"
#include <iostream>

DoorLockObserver::DoorLockObserver(SmartDevice* alarm) : DeviceObserver(alarm) {}

void DoorLockObserver::update() {
    std::cout << "DoorLockObserver: DoorLock status changed to " << device->getStatus() << std::endl;
}