#include <gtest/gtest.h>
#include "Light.h"
#include "DoorLock.h"
#include "Thermostat.h"
#include "Room.h"
#include "Sensor.h"
#include "Alarm.h"

// Test for the Light class
TEST(LightTest, InitialStateIsOff) {
    Light light;
    EXPECT_EQ(light.getStatus(), "off");  // Assert that the initial state is "off"
}

TEST(LightTest, ToggleOnChangesState) {
    Light light;
    light.performAction("Lights On");
    EXPECT_EQ(light.getStatus(), "On");  // Assert that after toggling, it turns "on"
}

TEST(LightTest, ToggleOffChangesState) {
    Light light;
    light.performAction("Lights On");  // Turn on the light
    light.performAction("Lights Off");  // Turn off the light
    EXPECT_EQ(light.getStatus(), "Off");
}

// Test for the DoorLock class
TEST(DoorLockTest, InitialStateIsUnlocked) {
    DoorLock door;
    EXPECT_EQ(door.getStatus(), "unlocked");  // Assert that the initial state is "unlocked"
}

TEST(DoorLockTest, LockAndUnlock) {
    DoorLock door;
    door.performAction("Doors Locked");
    EXPECT_EQ(door.getStatus(), "locked");  // Door should now be locked
    door.performAction("Unlock");
    EXPECT_EQ(door.getStatus(), "unlocked");  // Door should now be unlocked
}

// Test for the Thermostat class
TEST(ThermostatTest, InitialStateIsOff) {
    Thermostat thermostat;
    EXPECT_EQ(thermostat.getStatus(), "off");  // Thermostat should be off initially
}

TEST(ThermostatTest, TurnOnAndOff) {
    Thermostat thermostat;
    thermostat.performAction("TurnOn");
    EXPECT_EQ(thermostat.getStatus(), "on");  // Thermostat should now be on
    thermostat.performAction("TurnOff");
    EXPECT_EQ(thermostat.getStatus(), "off");  // Thermostat should now be off
}

// Test for the Room class
TEST(RoomTest, AddDeviceAndPerformActions) {
    Room livingRoom("Living Room");
    SmartDevice* light = new Light();
    SmartDevice* door = new DoorLock();
    SmartDevice* alarm = new Alarm();
    
    livingRoom.addDevice(light);
    livingRoom.addDevice(door);
     livingRoom.addDevice(alarm);

    livingRoom.performAction("Lights On");  // Should turn on the light
    EXPECT_EQ(light->getStatus(), "On");
    livingRoom.performAction("Doors Locked");  // Should lock the door
    EXPECT_EQ(door->getStatus(), "locked");
    livingRoom.performAction("Alarm On");  
    EXPECT_EQ(alarm->getStatus(), "On");

    delete light;  // Clean up memory
    delete door;
    delete alarm;
}

// Test for the Sensor class
TEST(SensorTest, AddDeviceAndNotify) {
    Sensor motionSensor;
    Light* light = new Light();
    
    motionSensor.addDevice(light);
    motionSensor.notifyDevices("Lights On , Doors Locked , Alarm On");  // Sensor triggers the light to turn on
    EXPECT_EQ(light->getStatus(), "On");
    
    delete light;  // Clean up memory
}

// Main function to run all tests
int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();  // Runs all the test cases
}

