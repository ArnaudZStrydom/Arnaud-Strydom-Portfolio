#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <vector>
#include <thread>
#include <random>
#include <chrono> 
#include <cmath>

#include "GL/glew.h"
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>

#include "shader.hpp"
#include "Matrix.cpp"
#include "Vector.cpp"

using namespace glm;
using namespace std;

struct Object {
    Matrix<4, 4> transform;         // Transformation matrix
    Vector<3> color;                // Original color
    Vector<3> selectedColor;        // Color when selected
    std::vector<float> vertices;    // Vertex positions
    std::vector<float> wireframeVertices; // For wireframe mode
    bool selected;                  // Selection state
    std::string type;               // "floor", "door", "counter", "seating1", etc.
    int vertexCount;                // Number of vertices
};

std::vector<float> createRectangle(float width, float height) {
    std::vector<float> vertices = {
        // Position      
        -width/2, -height/2, 0.0f,  // bottom left
         width/2, -height/2, 0.0f,  // bottom right
         width/2,  height/2, 0.0f,  // top right
         
         width/2,  height/2, 0.0f,  // top right
        -width/2,  height/2, 0.0f,  // top left
        -width/2, -height/2, 0.0f   // bottom left
    };
    return vertices;
}

// Generate vertices for a triangle
std::vector<float> createTriangle(float side) {
    std::vector<float> vertices = {
        // Position
         0.0f,     side/2, 0.0f,    // top
        -side/2,  -side/2, 0.0f,    // bottom left
         side/2,  -side/2, 0.0f     // bottom right
    };
    return vertices;
}

// Generate vertices for a circle with specified number of segments
std::vector<float> createCircle(float radius, int segments) {
    std::vector<float> vertices;
    
    for (int i = 0; i < segments; i++) {
        float angle1 = 2.0f * M_PI * i / segments;
        float angle2 = 2.0f * M_PI * (i+1) / segments;
        
        // Center vertex
        vertices.push_back(0.0f);
        vertices.push_back(0.0f);
        vertices.push_back(0.0f);
        
        // First edge vertex
        vertices.push_back(radius * cos(angle1));
        vertices.push_back(radius * sin(angle1));
        vertices.push_back(0.0f);
        
        // Second edge vertex
        vertices.push_back(radius * cos(angle2));
        vertices.push_back(radius * sin(angle2));
        vertices.push_back(0.0f);
    }
    
    return vertices;
}

// Create a pastel version of a color for selection
Vector<3> createPastelColor(const Vector<3>& originalColor) {
    Vector<3> pastelColor;
    pastelColor[0] = originalColor[0] * 0.6f + 0.4f; // More white mixed in
    pastelColor[1] = originalColor[1] * 0.6f + 0.4f;
    pastelColor[2] = originalColor[2] * 0.6f + 0.4f;
    return pastelColor;
}

// Generate wireframe vertices from shape vertices
std::vector<float> createWireframe(const std::vector<float>& shapeVertices, int vertexCount) {
    std::vector<float> wireframe;
    //wireframe.reserve(vertexCount * 6);  // Each edge is 2 vertices (6 floats per edge)

    for (int i = 0; i < vertexCount; i ++) {
        // Triangle vertex indices
        int v0 = i * 3;
        int v1 = (i + 1) * 3;
        int v2 = (i + 2) * 3;

        // First edge: v0 → v1
        wireframe.insert(wireframe.end(), {shapeVertices[v0], shapeVertices[v0+1], shapeVertices[v0+2]});
        wireframe.insert(wireframe.end(), {shapeVertices[v1], shapeVertices[v1+1], shapeVertices[v1+2]});

        // Second edge: v1 → v2
        wireframe.insert(wireframe.end(), {shapeVertices[v1], shapeVertices[v1+1], shapeVertices[v1+2]});
        wireframe.insert(wireframe.end(), {shapeVertices[v2], shapeVertices[v2+1], shapeVertices[v2+2]});

        // Third edge: v2 → v0
        wireframe.insert(wireframe.end(), {shapeVertices[v2], shapeVertices[v2+1], shapeVertices[v2+2]});
        wireframe.insert(wireframe.end(), {shapeVertices[v0], shapeVertices[v0+1], shapeVertices[v0+2]});
    }

    return wireframe;
}

// Function to create an object
Object createObject(std::string type, std::vector<float> vertices, int vertexCount, Vector<3> color) {
    Object obj;
    obj.transform = IdentityMatrix<4>(); // Start with identity matrix
    obj.color = color;
    obj.selectedColor = createPastelColor(color);
    obj.vertices = vertices;
    obj.wireframeVertices = createWireframe(vertices, vertexCount);
    obj.selected = false;
    obj.type = type;
    obj.vertexCount = vertexCount;
    return obj;
}

std::vector<int> selectedObjectIndices; // Stores indices of selected objects
std::vector<Object> objects;
bool wireframeMode = false;
int selectedObjectIndex = -1;
unsigned int VAO, VBO;
GLuint programID;
GLuint transformID;
GLuint colorID;


// Forward declarations
void initialize();
void render();
void processInput(GLFWwindow *window);
void selectObject(int index);
void applyTransformation(Matrix<4, 4> transformMatrix);
void key_callback(GLFWwindow* window, int key, int scancode, int action, int mods);

float* matrixToArray(const Matrix<4, 4>& matrix) {
    static float array[16];
    int index = 0;
    
    for (int j = 0; j < 4; j++) {
        for (int i = 0; i < 4; i++) {
            array[index++] = matrix[i][j]; // Note: OpenGL uses column-major order
        }
    }
    
    return array;
}

const char *getError()
{
    const char *errorDescription;
    glfwGetError(&errorDescription);
    return errorDescription;
}

inline void startUpGLFW()
{
    glewExperimental = true; // Needed for core profile
    if (!glfwInit())
    {
        throw getError();
    }
}

inline void startUpGLEW()
{
    glewExperimental = true; // Needed in core profile
    if (glewInit() != GLEW_OK)
    {
        glfwTerminate();
        throw getError();
    }
}



inline GLFWwindow *setUp()
{
    startUpGLFW();
    glfwWindowHint(GLFW_SAMPLES, 4);               // 4x antialiasing
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); // We want OpenGL 3.3
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);           // To make MacOS happy; should not be needed
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); // We don't want the old OpenGL
    GLFWwindow *window;                                            // (In the accompanying source code, this variable is global for simplicity)
    window = glfwCreateWindow(1000, 1000, "u23536013", NULL, NULL);
    if (window == NULL)
    {
        cout << getError() << endl;
        glfwTerminate();
        throw "Failed to open GLFW window. If you have an Intel GPU, they are not 3.3 compatible. Try the 2.1 version of the tutorials.\n";
    }
    glfwMakeContextCurrent(window); // Initialize GLEW
    startUpGLEW();
    return window;
}

void createObjects() {
    // Background color is set with glClearColor in render function
    
    
    // Create doors (two rectangles)
    Vector<3> doorColor;
    doorColor[0] = 0.5f; doorColor[1] = 0.5f; doorColor[2] = 0.5f; // Gray color
    
    // First door
    std::vector<float> door1Vertices = createRectangle(0.1f, 0.028f);
    Object door1 = createObject("door", door1Vertices, 4, doorColor);
    Matrix<4, 4> door1Transform = IdentityMatrix<4>();
    door1Transform[0][3] = -0.3f; // Translate X
    door1Transform[1][3] = -0.89f;  // Translate Y
    door1.transform = door1Transform;
    objects.push_back(door1);
    
    // Second door
    std::vector<float> door2Vertices = createRectangle(0.1f, 0.028f);
    Object door2 = createObject("door", door2Vertices, 4, doorColor);
    Matrix<4, 4> door2Transform = IdentityMatrix<4>();
    door2Transform[0][3] = 0.3f;  // Translate X
    door2Transform[1][3] = -0.89f;  // Translate Y
    door2.transform = door2Transform;
    objects.push_back(door2);
    
    // Create counter (rectangle)
    Vector<3> counterColor;
    counterColor[0] = 0.0f; counterColor[1] = 0.0f; counterColor[2] = 1.0f; // Blue color
    std::vector<float> counterVertices = createRectangle(0.6f, 0.1f);
    Object counter = createObject("counter", counterVertices, 4, counterColor);
    Matrix<4, 4> counterTransform = IdentityMatrix<4>();
    counterTransform[1][3] = 0.8f;  // Translate Y
    counter.transform = counterTransform;
    objects.push_back(counter);
    
    // Create seating type 1 (high polygon circles)
    Vector<3> seating1Color;
    seating1Color[0] = 0.5f; seating1Color[1] = 0.0f; seating1Color[2] = 0.5f; // Purple color
    std::vector<float> seating1Vertices = createCircle(0.07f, 50); // High polygon circle
    
    // First seating of type 1
    Object seating1a = createObject("seating1", seating1Vertices, 150, seating1Color);
    Matrix<4, 4> seating1aTransform = IdentityMatrix<4>();
    seating1aTransform[0][3] = -0.2f;  // Translate X
    seating1aTransform[1][3] = -0.2f;  // Translate Y
    seating1a.transform = seating1aTransform;
    objects.push_back(seating1a);
    
    // Second seating of type 1
    Object seating1b = createObject("seating1", seating1Vertices, 150, seating1Color);
    Matrix<4, 4> seating1bTransform = IdentityMatrix<4>();
    seating1bTransform[0][3] = 0.2f;   // Translate X
    seating1bTransform[1][3] = -0.2f;  // Translate Y
    seating1b.transform = seating1bTransform;
    objects.push_back(seating1b);
    
    // Create seating type 2 (low polygon circles)
    Vector<3> seating2Color;
    seating2Color[0] = 0.0f; seating2Color[1] = 0.5f; seating2Color[2] = 0.5f; // Teal color
    std::vector<float> seating2Vertices = createCircle(0.06f, 8); // Low polygon circle
    
    // First seating of type 2
    Object seating2a = createObject("seating2", seating2Vertices, 24, seating2Color);
    Matrix<4, 4> seating2aTransform = IdentityMatrix<4>();
    seating2aTransform[0][3] = -0.3f;  // Translate X
    seating2aTransform[1][3] = 0.0f;   // Translate Y
    seating2a.transform = seating2aTransform;
    objects.push_back(seating2a);
    
    // Second seating of type 2
    Object seating2b = createObject("seating2", seating2Vertices, 24, seating2Color);
    Matrix<4, 4> seating2bTransform = IdentityMatrix<4>();
    seating2bTransform[0][3] = 0.3f;   // Translate X
    seating2bTransform[1][3] = 0.0f;   // Translate Y
    seating2b.transform = seating2bTransform;
    objects.push_back(seating2b);
    
    // Create seating type 3 (triangles)
    Vector<3> seating3Color;
    seating3Color[0] = 0.8f; seating3Color[1] = 0.3f; seating3Color[2] = 0.3f; // Red-ish color
    std::vector<float> seating3Vertices = createTriangle(0.08f);
    
    // First seating of type 3
    Object seating3 = createObject("seating3", seating3Vertices, 1, seating3Color);
    Matrix<4, 4> seating3Transform = IdentityMatrix<4>();
    seating3Transform[0][3] = 0.0f;    // Translate X
    seating3Transform[1][3] = 0.0f;    // Translate Y
    seating3.transform = seating3Transform;
    objects.push_back(seating3);
    
    // Create decor type 1 (triangles for plants)
    Vector<3> decor1Color;
    decor1Color[0] = 0.0f; decor1Color[1] = 0.8f; decor1Color[2] = 0.0f; // Green color
    std::vector<float> decor1Vertices = createTriangle(0.05f);
    
    // First decor of type 1
    Object decor1a = createObject("decor1", decor1Vertices, 1, decor1Color);
    Matrix<4, 4> decor1aTransform = IdentityMatrix<4>();
    decor1aTransform[0][3] = -0.35f;  // Translate X
    decor1aTransform[1][3] = 0.35f;   // Translate Y
    decor1a.transform = decor1aTransform;
    objects.push_back(decor1a);
    
    // Second decor of type 1
    Object decor1b = createObject("decor1", decor1Vertices, 1, decor1Color);
    Matrix<4, 4> decor1bTransform = IdentityMatrix<4>();
    decor1bTransform[0][3] = 0.35f;   // Translate X
    decor1bTransform[1][3] = 0.35f;   // Translate Y
    decor1b.transform = decor1bTransform;
    objects.push_back(decor1b);
    
    // Create decor type 2 (small rectangles for tables)
    Vector<3> decor2Color;
    decor2Color[0] = 0.5f; decor2Color[1] = 0.3f; decor2Color[2] = 0.0f; // Brown color
    std::vector<float> decor2Vertices = createRectangle(0.1f, 0.05f);
    
    // First decor of type 2
    Object decor2 = createObject("decor2", decor2Vertices, 4, decor2Color);
    Matrix<4, 4> decor2Transform = IdentityMatrix<4>();
    decor2Transform[0][3] = 0.0f;     // Translate X
    decor2Transform[1][3] = -0.3f;    // Translate Y
    decor2.transform = decor2Transform;
    objects.push_back(decor2);

    // Create floor (large rectangle)
    Vector<3> floorColor;
    floorColor[0] = 1.0f; floorColor[1] = 0.5f; floorColor[2] = 0.0f; // Orange color
    std::vector<float> floorVertices = createRectangle(1.0f, 1.8f);    
    Object floor = createObject("floor", floorVertices, 4, floorColor); // 2 triangles
    objects.push_back(floor);
}





void initialize() {
    // Generate and bind VAO
    glGenVertexArrays(1, &VAO);
    glBindVertexArray(VAO);
    
    // Generate VBO
    glGenBuffers(1, &VBO);
    
    // Create shader program
    programID = LoadShaders("Vertex.glsl", "Fragment.glsl");
    
    // Get uniform locations
    transformID = glGetUniformLocation(programID, "transform");
    colorID = glGetUniformLocation(programID, "objectColor");
    
    // Create all objects in the scene
    createObjects();
    
    // Enable depth testing
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LESS);
}

void selectObject(int keyNumber) {
    std::string typeToSelect;
    switch(keyNumber) {
        case 1: typeToSelect = "seating1"; break;
        case 2: typeToSelect = "seating2"; break;
        case 3: typeToSelect = "decor1"; break;
        case 4: typeToSelect = "decor2"; break;
        case 0: // Deselect all
            selectedObjectIndices.clear();
            for (auto& obj : objects) {
                obj.selected = false;
            }
            return;
        default: return;
    }




    // Find the first object of the specified type that is not already selected
    for (int i = 0; i < objects.size(); i++) {
        if (objects[i].type == typeToSelect && !objects[i].selected) {
            objects[i].selected = true;
            selectedObjectIndices.push_back(i);
            break;
        }
    }
}

Matrix<4, 4> createTranslationMatrix(float tx, float ty, float tz) {
    Matrix<4, 4> translationMatrix = IdentityMatrix<4>();
    translationMatrix[0][3] = tx;
    translationMatrix[1][3] = ty;
    translationMatrix[2][3] = tz;
    return translationMatrix;
}

Matrix<4, 4> createScaleMatrix(float sx, float sy, float sz) {
    Matrix<4, 4> scaleMatrix = IdentityMatrix<4>();
    scaleMatrix[0][0] = sx;
    scaleMatrix[1][1] = sy;
    scaleMatrix[2][2] = sz;
    return scaleMatrix;
}

Matrix<4, 4> createRotationMatrixZ(float angle) {
    Matrix<4, 4> rotationMatrix = IdentityMatrix<4>();
    float cosTheta = cos(angle);
    float sinTheta = sin(angle);
    rotationMatrix[0][0] = cosTheta;
    rotationMatrix[0][1] = -sinTheta;
    rotationMatrix[1][0] = sinTheta;
    rotationMatrix[1][1] = cosTheta;
    return rotationMatrix;
}

Matrix<4, 4> rotateObjectAroundCenter(Object& obj, float angle) {
    // Step 1: Get the object's center (stored in the transform matrix)
    float centerX = obj.transform[0][3]; // X translation
    float centerY = obj.transform[1][3]; // Y translation

    // Step 2: Create a translation matrix to move the object to the origin
    Matrix<4, 4> translateToOrigin = createTranslationMatrix(-centerX, -centerY, 0.0f);

    // Step 3: Create a rotation matrix
    Matrix<4, 4> rotationMatrix = createRotationMatrixZ(angle);

    // Step 4: Create a translation matrix to move the object back to its original position
    Matrix<4, 4> translateBack = createTranslationMatrix(centerX, centerY, 0.0f);

    // Step 5: Combine the transformations
    // The order is: translate back → rotate → translate to origin → current transform
    return (translateBack * rotationMatrix * translateToOrigin);
}

Matrix<4, 4> scaleObjectAroundCenter(Object& obj, float sx, float sy, float sz) {
    // Step 1: Get the object's center (stored in the transform matrix)
    float centerX = obj.transform[0][3]; // X translation
    float centerY = obj.transform[1][3]; // Y translation

    // Step 2: Create a translation matrix to move the object to the origin
    Matrix<4, 4> translateToOrigin = createTranslationMatrix(-centerX, -centerY, 0.0f);

    // Step 3: Create a scaling matrix
    Matrix<4, 4> scaleMatrix = createScaleMatrix(sx, sy, sz);

    // Step 4: Create a translation matrix to move the object back to its original position
    Matrix<4, 4> translateBack = createTranslationMatrix(centerX, centerY, 0.0f);

    // Step 5: Combine the transformations
    // The order is: translate back → scale → translate to origin
    return (translateBack * scaleMatrix * translateToOrigin);
}




void applyTransformation(char key) {
    if (selectedObjectIndices.empty()) return; // No objects selected
    
    // Define transformation amount
    float translateAmount = 0.05f;
    float scaleAmount = 1.1f;
    float rotateAmount = 0.1f;
    int AmountSelected = selectedObjectIndices.size();
    Matrix<4, 4> transformMatrix;
    
    switch(key) {
        case 'W': // Move up
            transformMatrix = createTranslationMatrix(0.0f, translateAmount, 0.0f);
            break;
        case 'S': // Move down
            transformMatrix = createTranslationMatrix(0.0f, -translateAmount, 0.0f);
            break;
        case 'A': // Move left
            transformMatrix = createTranslationMatrix(-translateAmount, 0.0f, 0.0f);
            break;
        case 'D': // Move right
            transformMatrix = createTranslationMatrix(translateAmount, 0.0f, 0.0f);
            break;
        case '+': // Scale up
            if(AmountSelected > 1){
                for(int i = 0; i < AmountSelected; i++){
                    transformMatrix = scaleObjectAroundCenter(objects[selectedObjectIndices[i]], scaleAmount, scaleAmount, scaleAmount);
                    objects[selectedObjectIndices[i]].transform = transformMatrix * objects[selectedObjectIndices[i]].transform;
                }
                return;
            }
            else{
                transformMatrix = scaleObjectAroundCenter(objects[selectedObjectIndices[0]],scaleAmount,scaleAmount,scaleAmount);
            }
            break;
        case '-': // Scale down
            if(AmountSelected > 1){
                for(int i = 0; i < AmountSelected; i++){
                    transformMatrix = scaleObjectAroundCenter(objects[selectedObjectIndices[i]], 1.0f/scaleAmount, 1.0f/scaleAmount, 1.0f/scaleAmount);
                    objects[selectedObjectIndices[i]].transform = transformMatrix * objects[selectedObjectIndices[i]].transform;
                }
                return;
            }
            else{
                transformMatrix = scaleObjectAroundCenter(objects[selectedObjectIndices[0]], 1.0f/scaleAmount, 1.0f/scaleAmount, 1.0f/scaleAmount);
            }
            break;
        case 'E': // Rotate clockwise
            if(AmountSelected > 1){
                for(int i = 0; i < AmountSelected; i++){
                    transformMatrix = rotateObjectAroundCenter(objects[selectedObjectIndices[i]], -rotateAmount);
                    objects[selectedObjectIndices[i]].transform = transformMatrix * objects[selectedObjectIndices[i]].transform;
                }
                return;
            }
            else{
                transformMatrix = rotateObjectAroundCenter(objects[selectedObjectIndices[0]], -rotateAmount);
            }
            break;
        case 'Q': // Rotate counter-clockwise
            if(AmountSelected > 1){
                for(int i = 0; i < AmountSelected; i++){
                    transformMatrix = rotateObjectAroundCenter(objects[selectedObjectIndices[i]], rotateAmount);
                    objects[selectedObjectIndices[i]].transform = transformMatrix * objects[selectedObjectIndices[i]].transform;
                }
                return;
            }
            else{
                transformMatrix = rotateObjectAroundCenter(objects[selectedObjectIndices[0]], rotateAmount);
            }
            break;
        default:
            return;
    }
    
    // Apply transformation to all selected objects
    for (int index : selectedObjectIndices) {
        
        // For translation and scaling, multiply the transformation matrix
        objects[index].transform = transformMatrix * objects[index].transform;
        
    }
}

void key_callback(GLFWwindow* window, int key, int scancode, int action, int mods) {
    static bool enterKeyPressed = false;
    
    if (action == GLFW_PRESS || action == GLFW_REPEAT) {
        switch (key) {
            case GLFW_KEY_1:
            case GLFW_KEY_2:
            case GLFW_KEY_3:
            case GLFW_KEY_4:
                selectObject(key - GLFW_KEY_0);
                break;
                
            case GLFW_KEY_0:
                selectObject(0); // Deselect
                break;
                
            case GLFW_KEY_W:
                applyTransformation('W');
                break;
                
            case GLFW_KEY_S:
                applyTransformation('S');
                break;
                
            case GLFW_KEY_A:
                applyTransformation('A');
                break;
                
            case GLFW_KEY_D:
                applyTransformation('D');
                break;
                
            case GLFW_KEY_EQUAL: // + key
                if (mods & GLFW_MOD_SHIFT) {
                    applyTransformation('+');
                }
                break;
                
            case GLFW_KEY_MINUS: // - key
                applyTransformation('-');
                break;
                
            case GLFW_KEY_E:
                applyTransformation('E');
                break;
                
            case GLFW_KEY_Q:
                applyTransformation('Q');
                break;
                
            case GLFW_KEY_ENTER:
                // Add a time delay to prevent toggling too fast
                if (!enterKeyPressed) {
                    wireframeMode = !wireframeMode;
                    enterKeyPressed = true;
                    // Reset after a short delay
                    std::thread([&]() {
                        std::this_thread::sleep_for(std::chrono::milliseconds(200));
                        enterKeyPressed = false;
                    }).detach();
                }
                break;
                
            case GLFW_KEY_ESCAPE:
                glfwSetWindowShouldClose(window, GL_TRUE);
                break;
        }
    }
}

Vector<3> transformVertex(const Matrix<4, 4>& transform, const Vector<3>& vertex) {
    // Convert the vertex to homogeneous coordinates (Vector<4>)
    Vector<4> homogeneousVertex;
    homogeneousVertex[0] = vertex[0];
    homogeneousVertex[1] = vertex[1];
    homogeneousVertex[2] = vertex[2];
    homogeneousVertex[3] = 1.0f; // Homogeneous coordinate

    // Multiply the vertex by the transformation matrix
    Vector<4> transformedVertex = transform * homogeneousVertex;

    // Convert back to 3D space (divide by the homogeneous coordinate)
    Vector<3> result;
    result[0] = transformedVertex[0] / transformedVertex[3];
    result[1] = transformedVertex[1] / transformedVertex[3];
    result[2] = transformedVertex[2] / transformedVertex[3];

    return result;
}


void mouse_button_callback(GLFWwindow* window, int button, int action, int mods) {
    if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
        double xpos, ypos;
        glfwGetCursorPos(window, &xpos, &ypos);

        // Convert screen coordinates to normalized device coordinates (NDC)
        int width, height;
        glfwGetWindowSize(window, &width, &height);
        float ndcX = (2.0f * xpos) / width - 1.0f;
        float ndcY = 1.0f - (2.0f * ypos) / height;

        // Check which object was clicked
        for (int i = 0; i < objects.size(); i++) {
            Object& obj = objects[i];

            // Transform the object's vertices to world space
            std::vector<float> transformedVertices;
            for (int j = 0; j < obj.vertices.size(); j += 3) {
                // Create a Vector<3> for the vertex
                Vector<3> vertex;
                vertex[0] = obj.vertices[j];
                vertex[1] = obj.vertices[j+1];
                vertex[2] = obj.vertices[j+2];

                // Transform the vertex using the object's transformation matrix
                Vector<3> transformedVertex = transformVertex(obj.transform, vertex);

                // Store the transformed vertex
                transformedVertices.push_back(transformedVertex[0]);
                transformedVertices.push_back(transformedVertex[1]);
                transformedVertices.push_back(transformedVertex[2]);
            }

            // Check if the click is inside the object's bounding box
            float minX = transformedVertices[0], maxX = transformedVertices[0];
            float minY = transformedVertices[1], maxY = transformedVertices[1];
            for (int j = 0; j < transformedVertices.size(); j += 3) {
                if (transformedVertices[j] < minX) minX = transformedVertices[j];
                if (transformedVertices[j] > maxX) maxX = transformedVertices[j];
                if (transformedVertices[j+1] < minY) minY = transformedVertices[j+1];
                if (transformedVertices[j+1] > maxY) maxY = transformedVertices[j+1];
            }

            if (ndcX >= minX && ndcX <= maxX && ndcY >= minY && ndcY <= maxY) {
                // Deselect all objects
                
                // Select the clicked object
                obj.selected = true;
                selectedObjectIndices.push_back(i);
                break;
            }
        }
    }
}
void render() {
    // Clear the screen to background color
    glClearColor(0.2f, 0.2f, 0.2f, 1.0f); // Dark gray background
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    
    // Use our shader program
    glUseProgram(programID);
    
    // Render each object
    for (int i = 0; i < objects.size(); i++) {
        Object& obj = objects[i];
        
        // Bind VBO and set data
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        
        // Choose between normal vertices and wireframe vertices
        if (wireframeMode) {
            glBufferData(GL_ARRAY_BUFFER, obj.wireframeVertices.size() * sizeof(float), 
                        &obj.wireframeVertices[0], GL_DYNAMIC_DRAW);
        } else {
            glBufferData(GL_ARRAY_BUFFER, obj.vertices.size() * sizeof(float), 
                        &obj.vertices[0], GL_DYNAMIC_DRAW);
        }
        
        // Position attribute
        glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
        glEnableVertexAttribArray(0);
        
        // Set transformation matrix
        glUniformMatrix4fv(transformID, 1, GL_FALSE, matrixToArray(obj.transform));
        
        // Set color (change if selected)
        if (obj.selected) {
            glUniform3f(colorID, obj.selectedColor[0], obj.selectedColor[1], obj.selectedColor[2]);
        } else {
            glUniform3f(colorID, obj.color[0], obj.color[1], obj.color[2]);
        }
        
        // Draw the object
        if (wireframeMode) {
            glDrawArrays(GL_LINES, 0, obj.wireframeVertices.size() / 3);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, obj.vertices.size() / 3);
        }
    }
}

int main()
{
    GLFWwindow *window;
    try
    {
        window = setUp();
    }
    catch (const char *e)
    {
        cout << e << endl;
        throw;
    }

    // Set key callback
    glfwSetKeyCallback(window, key_callback);

    // Set mouse button callback
    glfwSetMouseButtonCallback(window, mouse_button_callback);
    
    // Initialize the application
    initialize();
    
    // Main loop
    while (!glfwWindowShouldClose(window)) {
        // Render the scene
        render();
        
        // Swap buffers
        glfwSwapBuffers(window);
        
        // Check for events
        glfwPollEvents();
    }
    
    // Cleanup
    glDeleteVertexArrays(1, &VAO);
    glDeleteBuffers(1, &VBO);
    glDeleteProgram(programID);
    
    glfwTerminate();
    return 0;
}
