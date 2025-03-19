# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles\\CityManagementGUI_autogen.dir\\AutogenUsed.txt"
  "CMakeFiles\\CityManagementGUI_autogen.dir\\ParseCache.txt"
  "CityManagementGUI_autogen"
  )
endif()
