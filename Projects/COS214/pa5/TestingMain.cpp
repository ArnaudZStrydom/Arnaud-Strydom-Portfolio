#include "Light.h"
#include "Alarm.h" 
#include "SmartDevice.h"
#include "DeviceObserver.h"
#include "Sensor.h"
#include "Command.h"
#include "DoorLock.h"
#include "Thermostat.h"
#include "Room.h"
#include "Section.h"
#include "LightObserver.h"
#include "AlarmObserver.h"
#include "Sensor.h"
#include "TurnOffAllLightsCommand.h"
#include "LockAllDoorsCommand.h"
#include "MacroRoutine.h"
#include "LegacyThermostat.h"
#include "SmartThermostatIntegrator.h"
#include "DoorLockObserver.h"
#include <iostream>
#include <vector>


void testObserverPattern() {
    std::cout << "Testing Observer Pattern..." << std::endl;

    // Create devices
    SmartDevice* light = new Light();
    SmartDevice* doorLock = new DoorLock();  // Using DoorLock as a simulated alarm
    SmartDevice* alarm = new Alarm();

    // Create observers and connect them to devices
    DeviceObserver* lightObserver = new LightObserver(light);
    DeviceObserver* alarmObserver = new AlarmObserver(alarm);
    DeviceObserver* doorLockObserver = new DoorLockObserver(doorLock);

    // Register observers in devices
    light->addObserver(lightObserver);
    alarm->addObserver(alarmObserver);
    doorLock->addObserver(doorLockObserver);

    // Create a sensor and add devices (direct SmartDevice instances)
    Sensor* motionSensor = new Sensor();
    motionSensor->addDevice(light);      // Add Light to Sensor
    motionSensor->addDevice(alarm);
    motionSensor->addDevice(doorLock);   // Add DoorLock to Sensor

    // Simulate movement detection by the sensor
    motionSensor->detectMovement();  // This should trigger a status change and notify observers

    // Check the status of devices after notification
    std::cout << "Light status: " << light->getStatus() << std::endl;
    std::cout << "DoorLock status: " << doorLock->getStatus() << std::endl;
    std::cout << "Alarm status: " << alarm->getStatus() << std::endl;

    // Clean up
    delete lightObserver;
    delete alarmObserver;
    delete doorLockObserver;

    delete alarm;
    delete light;
    delete doorLock;
    delete motionSensor;

    std::cout << "Observer Pattern test complete." << std::endl;
}

// Test Command pattern and MacroRoutine
void testMacroRoutine() {
    std::cout << "\nTesting Macro Routine..." << std::endl;

    // Create devices
    SmartDevice* light1 = new Light();
    SmartDevice* light2 = new Light();
    SmartDevice* doorLock1 = new DoorLock();

    // Create room and add devices
    Room* livingRoom = new Room("room1");
    livingRoom->addDevice(light1);
    livingRoom->addDevice(light2);

    livingRoom->addDevice(doorLock1);

    // Create a list of devices
    std::vector<SmartDevice*> devices = livingRoom->getDevices();


    // Create commands
    Command* turnOffLights = new TurnOffAllLightsCommand(livingRoom);
    Command* lockAllDoors = new LockAllDoorsCommand(livingRoom);

    // Create MacroRoutine to perform multiple actions
    MacroRoutine* goodnightRoutine = new MacroRoutine();
    goodnightRoutine->addProcedure(turnOffLights);
    goodnightRoutine->addProcedure(lockAllDoors);

    // Execute macro routine
    std::cout << "Executing Goodnight routine..." << std::endl;
    goodnightRoutine->execute();

    // Check device statuses after routine
    for (SmartDevice* device : livingRoom->getDevices()) {
        std::cout << device->getDeviceType() << " is " << device->getStatus() << std::endl;
    }

    // Clean up
    delete goodnightRoutine;  // This deletes the individual commands as well
    delete livingRoom;        // This deletes all devices
    delete doorLock1;
    delete light1;
    delete light2;
    delete turnOffLights;
    delete lockAllDoors;


    std::cout << "Macro Routine test complete." << std::endl;
}

// Test legacy thermostat integration using SmartThermostatIntegrator
void testLegacyThermostat() {
    std::cout << "\nTesting Legacy Thermostat Integration..." << std::endl;

    // Create a legacy thermostat
    LegacyThermostat* legacyThermo = new LegacyThermostat();

    // Adapt it using the SmartThermostatIntegrator
    SmartDevice* smartThermo = new SmartThermostatIntegrator(legacyThermo);

    // Create room and add the adapted thermostat
    Room* livingRoom = new Room("Living room 2");
    livingRoom->addDevice(smartThermo);

    // Perform actions on thermostat
    std::cout << "Initial thermostat status: " << smartThermo->getStatus() << std::endl;
    smartThermo->performAction("TurnOn");
    std::cout << "Thermostat status after action: " << smartThermo->getStatus() << std::endl;

    // Set and get temperature using the adapted interface
    SmartThermostatIntegrator* integrator = dynamic_cast<SmartThermostatIntegrator*>(smartThermo);
    integrator->setTemperature(25.5);
    std::cout << "Current temperature: " << integrator->getTemperature() << "°C" << std::endl;

    // Clean up
    delete livingRoom;
    delete legacyThermo;  // smartThermo does not own legacyThermo
    delete smartThermo;

    std::cout << "Legacy Thermostat Integration test complete." << std::endl;
}

// Main function that runs all the tests
int Testmain() {
    std::cout << "Running Smart Home System Tests..." << std::endl;

    // Test the observer pattern functionality
    testObserverPattern();

    // Test the macro routine (Command Pattern)
    testMacroRoutine();

    // Test the legacy thermostat integration
    testLegacyThermostat();

    std::cout << "All tests completed." << std::endl;

    return 0;
}
