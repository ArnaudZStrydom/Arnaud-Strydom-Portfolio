#pragma once
#include <string>
#include <vector>
#include <algorithm>  // for std::remove

class DeviceObserver;  // Forward declaration

class SmartDevice {
protected:
    std::string status;
    std::vector<DeviceObserver*> observers;

public:
    SmartDevice();
    virtual ~SmartDevice() = default;

    void addObserver(DeviceObserver* observer);
    void removeObserver(DeviceObserver* observer);
    void notifyObservers();

    virtual std::string getStatus() const;
    virtual void setStatus(const std::string& newStatus);

    virtual std::string getDeviceType() const = 0;  // Pure virtual for device type
    virtual void performAction(const std::string& action) = 0;               // Pure virtual for device action
};

