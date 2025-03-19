CXX = g++
CXXFLAGS = -std=c++17 -I/usr/local/include  # Include Google Test headers
LDFLAGS = -L/usr/local/lib -lgtest -lgtest_main -pthread  # Link Google Test libraries

# Object files for the project
OBJS = Light.o DoorLock.o Thermostat.o Room.o Sensor.o \
       TestingMain.o LightObserver.o AlarmObserver.o \
       TurnOffAllLightsCommand.o LockAllDoorsCommand.o MacroRoutine.o \
       LegacyThermostat.o SmartThermostatIntegrator.o DeviceObserver.o DoorLockObserver.o Section.o SmartDevice.o Alarm.o UnitTestMain.o

# Target to build the test binary
test: $(OBJS)
	$(CXX) -o test $(OBJS) $(LDFLAGS)
	./test  # Run the test binary

# Memory leak check using Valgrind
memcheck: test
	valgrind --leak-check=full ./test

# Clean target
clean:
	rm -f $(OBJS) test

