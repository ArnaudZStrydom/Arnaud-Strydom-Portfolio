// File: Section.cpp
#include "Section.h"
#include <iostream>

Section::Section(const std::string& name) : sectionName(name) {}

void Section::addRoom(Room* room) {
    rooms.push_back(room);
}

void Section::performAction(const std::string& action) {
    for (auto* room : rooms) {
        room->performAction(action);
    }
}

std::string Section::getSectionName() const {
    return sectionName;
}

void Section::listRooms() const {
    std::cout << "Rooms in section: " << sectionName << "\n";
    for (const auto* room : rooms) {
        std::cout << "Room: " << room->getRoomName() << "\n";
        room->listDevices();
    }
}
