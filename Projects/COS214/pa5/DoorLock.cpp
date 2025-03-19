#include "DoorLock.h"

DoorLock::DoorLock() {
    status = "unlocked";
}

std::string DoorLock::getDeviceType() const {
    return "DoorLock";
}

void DoorLock::performAction(const std::string& action) {
    // Toggle door lock status
    if (action.find("Doors Locked") != std::string::npos) {
        setStatus("locked");
    } else {
        setStatus("unlocked");
    }
}

