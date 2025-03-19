// File: Section.h
#ifndef SECTION_H
#define SECTION_H

#include <vector>
#include <string>
#include "Room.h"

class Section {
private:
    std::string sectionName;
    std::vector<Room*> rooms;  // List of rooms in the section.

public:
    Section(const std::string& name);

    void addRoom(Room* room);  // Add a room to the section.
    void performAction(const std::string& action);  // Perform an action on all rooms in the section.
    std::string getSectionName() const;  // Returns the section name.
    void listRooms() const;  // Lists all rooms and their devices.
};

#endif // SECTION_H
