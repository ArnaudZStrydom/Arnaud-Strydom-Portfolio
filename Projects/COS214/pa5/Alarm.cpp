#include "Alarm.h"

Alarm::Alarm() {
    status = "off";
}

std::string Alarm::getDeviceType() const {
    return "Alarm";
}

void Alarm::performAction(const std::string& action) {
    // Toggle light status
    if (action.find("Alarm On") != std::string::npos) {
        setStatus("On");
    } else {
        setStatus("Off");
    }
}