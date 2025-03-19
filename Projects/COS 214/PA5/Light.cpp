#include "Light.h"

Light::Light() {
    status = "off";
}

std::string Light::getDeviceType() const {
    return "Light";
}

void Light::performAction(const std::string& action) {
    // Toggle light status
    if (action.find("Lights On") != std::string::npos) {
        setStatus("On");
    } else {
        setStatus("Off");
    }
}

