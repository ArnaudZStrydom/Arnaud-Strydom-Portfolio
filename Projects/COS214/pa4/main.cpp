#include <iostream>
#include <vector>
#include <chrono>
#include "FarmUnit.h"
#include "CropField.h"
#include "Barn.h"
#include "FertilizerTruck.h"
#include "DeliveryTruck.h"
#include "FertilizedField.h"
#include "ExtraBarnField.h"
#include "FarmTraverser.h"
#include "SoilState.h"
#include "DrySoil.h"
#include "FloodedSoil.h"
#include "FruitfulSoil.h"
#include "DepthFirstIterator.h"
#include "BreadthFirstIterator.h"

// Function prototypes for comprehensive testing
void testFarmUnit();
void testTrucks();
void testIterators();
void testSoilStates();
void testDecorators();
void testEdgeCases();
void benchmarkLargeFarm();

int main() {
    std::cout << "Starting comprehensive tests...\n";

    // Step 1: Test Farm Units
    std::cout << "\n--- Testing Farm Units ---\n";
    testFarmUnit();

    // Step 2: Test Truck Operations
    std::cout << "\n--- Testing Truck Operations ---\n";
    testTrucks();

    // Step 3: Test Iterators (Breadth-First and Depth-First)
    std::cout << "\n--- Testing Iterators ---\n";
    testIterators();

    // Step 4: Test Soil States
    std::cout << "\n--- Testing Soil States ---\n";
    testSoilStates();

    // Step 5: Test Decorator Combinations
    std::cout << "\n--- Testing Decorator Combinations ---\n";
    testDecorators();

    // Step 6: Test Edge Cases
    std::cout << "\n--- Testing Edge Cases ---\n";
    testEdgeCases();

    // Step 7: Performance Benchmarking
    std::cout << "\n--- Benchmarking Large Farm Traversal ---\n";
    benchmarkLargeFarm();

    return 0;
}

// Test farm units and their basic functionalities
void testFarmUnit() {
    CropField* wheatField = new CropField("Wheat", 100, "Wheatfield1");
    Barn* mainBarn = new Barn(200, "Main Barn");

    // Test basic capacities and names
    std::cout << "Wheat Field Capacity: " << wheatField->getTotalCapacity() << std::endl;
    std::cout << "Barn Capacity: " << mainBarn->getTotalCapacity() << std::endl;
    std::cout << "Wheat Field Name: " << wheatField->getName() << std::endl;

    // Add capacity updates and leftover capacity checks
    
    std::cout << "Wheat Field Updated Capacity: " << wheatField->getTotalCapacity() << std::endl;
    std::cout << "Wheat Field Leftover Capacity: " << wheatField->getLeftoverCapacity() << std::endl;

    delete wheatField;
    delete mainBarn;
}

// Test truck operations on various farm units
void testTrucks() {
    CropField* cornField = new CropField("Corn", 80, "CornField1");
    Barn* barn = new Barn(150, "Test Barn");
    FertilizerTruck* fertilizerTruck = new FertilizerTruck();
    DeliveryTruck* deliveryTruck = new DeliveryTruck();

    // Apply trucks to different farm units
    fertilizerTruck->performOperation(cornField);  // Should work
    deliveryTruck->performOperation(cornField);  // Check if truck can work on barns

    // Test trucks on empty farm units
    CropField* emptyField = new CropField("EmptyField", 0, "EmptyField");
    fertilizerTruck->performOperation(emptyField);  // Apply truck to an empty field

    std::cout << "Fertilizer Truck Type: " << fertilizerTruck->getType() << std::endl;
    std::cout << "Delivery Truck Type: " << deliveryTruck->getType() << std::endl;

    delete cornField;
    delete barn;
    delete emptyField;
    delete fertilizerTruck;
    delete deliveryTruck;
}

// Test farm iterators (Breadth-First and Depth-First) with edge cases
void testIterators() {
    CropField* wheatField = new CropField("Wheat", 100, "Wheatfield1");
    Barn* mainBarn = new Barn(200, "Main Barn");

    FarmIterator* bfsIterator = new BreadthFirstIterator(mainBarn);
    FarmIterator* dfsIterator = new DepthFirstIterator(wheatField);

    std::cout << "Breadth-First Traversal:\n";
    for (bfsIterator->firstFarm(); !bfsIterator->isDone(); bfsIterator->next()) {
        std::cout << "Visiting: " << bfsIterator->currentFarm()->getName() << std::endl;
    }

    std::cout << "Depth-First Traversal:\n";
    for (dfsIterator->firstFarm(); !dfsIterator->isDone(); dfsIterator->next()) {
        std::cout << "Visiting: " << dfsIterator->currentFarm()->getName() << std::endl;
    }

    // Test empty farm iterator
    CropField* emptyField = new CropField("EmptyField", 0, "EmptyField");
    FarmIterator* emptyBfsIterator = new BreadthFirstIterator(emptyField);
    if (emptyBfsIterator->isDone()) {
        std::cout << "Empty field traversal correctly handled.\n";
    }

    delete bfsIterator;
    delete dfsIterator;
    delete emptyBfsIterator;
    delete wheatField;
    delete mainBarn;
    delete emptyField;
}

// Test all soil states applied to fields
void testSoilStates() {
    CropField* field = new CropField("Test Field", 50, "TestField");

    // DrySoil test
    field->setSoilState(new DrySoil());
    std::cout << "Soil State: " << field->getSoilStateName() << std::endl;

    // FloodedSoil test
    field->setSoilState(new FloodedSoil());
    std::cout << "Soil State: " << field->getSoilStateName() << std::endl;

    // FruitfulSoil test
    field->setSoilState(new FruitfulSoil());
    std::cout << "Soil State: " << field->getSoilStateName() << std::endl;

    // Test soil state transitions
    field->setSoilState(new DrySoil());
    std::cout << "Soil State After Transition: " << field->getSoilStateName() << std::endl;

    delete field;
}

// Test different combinations of decorators
void testDecorators() {
    CropField* wheatField = new CropField("Wheat", 100, "Wheatfield1");
    Barn* mainBarn = new Barn(200, "Main Barn");

    CropField* fertilizedField = new FertilizedField(wheatField);
    CropField* extraBarnField = new ExtraBarnField(fertilizedField, 50);

    // Test capacity after applying multiple decorators
    std::cout << "Wheat Field Capacity After Decoration: " << extraBarnField->getTotalCapacity() << std::endl;
    std::cout << "Extra Barn Field Leftover Capacity: " << extraBarnField->getLeftoverCapacity() << std::endl;

    // Test with only FertilizedField
    CropField* fertilizedOnlyField = new FertilizedField(wheatField);
    std::cout << "Fertilized Only Field Capacity: " << fertilizedOnlyField->getTotalCapacity() << std::endl;

    delete extraBarnField;  // This should also delete `fertilizedField` and `wheatField`
    delete mainBarn;
}

// Test edge cases like invalid capacities or empty units
void testEdgeCases() {
    CropField* emptyField = new CropField("EmptyField", 0, "Empty");

    if (emptyField->getTotalCapacity() == 0) {
        std::cout << "Empty field capacity is correctly set to 0.\n";
    } else {
        std::cout << "Empty field test failed.\n";
    }

    // Invalid capacities (negative values)
    try {
        CropField* invalidField = new CropField("Invalid", -10, "InvalidField");
        std::cout << "Invalid field created (unexpected).\n";
        delete invalidField;
    } catch (...) {
        std::cout << "Caught error for invalid field capacity.\n";
    }

    delete emptyField;
}

// Benchmark performance for large farms
void benchmarkLargeFarm() {
    const int largeFarmSize = 10000;
    std::vector<FarmUnit*> largeFarm;

    for (int i = 0; i < largeFarmSize; ++i) {
        largeFarm.push_back(new CropField("Crop", i + 1, "Field" + std::to_string(i)));
    }

    auto start = std::chrono::high_resolution_clock::now();
    FarmIterator* dfsIterator = new DepthFirstIterator(largeFarm[0]);
    for (dfsIterator->firstFarm(); !dfsIterator->isDone(); dfsIterator->next()) {
        dfsIterator->currentFarm();
    }
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> durationDFS = end - start;
    std::cout << "Depth-First Traversal Time for Large Farm: " << durationDFS.count() << " seconds\n";
    delete dfsIterator;

    start = std::chrono::high_resolution_clock::now();
    FarmIterator* bfsIterator = new BreadthFirstIterator(largeFarm[0]);
    for (bfsIterator->firstFarm(); !bfsIterator->isDone(); bfsIterator->next()) {
        bfsIterator->currentFarm();
    }
    end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> durationBFS = end - start;
    std::cout << "Breadth-First Traversal Time for Large Farm: " << durationBFS.count() << " seconds\n";
    delete bfsIterator;

    for (auto farmUnit : largeFarm) {
        delete farmUnit;
    }
}

