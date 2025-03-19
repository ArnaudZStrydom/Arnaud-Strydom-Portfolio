#include "LightObserver.h"
#include <iostream>

LightObserver::LightObserver(SmartDevice* light) : DeviceObserver(light) {}

void LightObserver::update() {
    std::cout << "LightObserver: Light status changed to " << device->getStatus() << std::endl;
}
