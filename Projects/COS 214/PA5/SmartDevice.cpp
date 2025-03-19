#include "SmartDevice.h"
#include "DeviceObserver.h"

SmartDevice::SmartDevice() : status("off") {}

void SmartDevice::addObserver(DeviceObserver* observer) {
    observers.push_back(observer);
}

void SmartDevice::removeObserver(DeviceObserver* observer) {
    observers.erase(std::remove(observers.begin(), observers.end(), observer), observers.end());
}

void SmartDevice::notifyObservers() {
    for (DeviceObserver* observer : observers) {
        observer->update();
    }
}

std::string SmartDevice::getStatus() const {
    return status;
}

void SmartDevice::setStatus(const std::string& newStatus) {
    status = newStatus;
    notifyObservers();  // Notify observers whenever the status changes
}
