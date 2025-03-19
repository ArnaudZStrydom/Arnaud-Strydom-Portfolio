#include "Matrix.cpp"
#include "Vector.cpp"

#include <iostream>
#include <sstream>

using namespace std;

int main()
{
    // Test 1: Basic Matrix and Vector Initialization
    std::cout << "Test 1: Matrix and Vector Initialization\n";
    SquareMatrix<3> A;
    Vector<3> b;
    Vector<3> c;
    


    // Fill A with values
    double matValues[3][3] = {
        {2, -1, 1},
        {3, 3, 9},
        {3, 3, 5}
    };

    double vecValues[3] = {3, 42, 12};
    double vecValues2[3] = {6, 5, 5};

    Vector<3> d(vecValues2);

    for (int i = 0; i < 3; ++i) {
        c[i] = vecValues2[i];
    }
    // Assign values to the matrix and vector
    for (int i = 0; i < 3; ++i) {
        b[i] = vecValues[i];
        for (int j = 0; j < 3; ++j) {
            A[i][j] = matValues[i][j];
        }
    }


    

    // Print matrix
    std::cout << "Matrix A:\n";
    A.print();  // Assuming you have a print() method

    std::cout << "Vector b:\n";
    b.print();  // Assuming you have a print() method
    std::cout << "-----------------------------\n";

    // Test 2: Assignment Operator
    std::cout << "Test 2: Assignment Operator\n";
    SquareMatrix<3> B;
    B = A;  // Copy matrix
    std::cout << "Copied Matrix B:\n";
    B.print();

    b= d ;
    std::cout << "Copied Vector b:\n";
    b.print();
    std::cout << "-----------------------------\n";

    // Test 3: Solving a system of equations
    std::cout << "Test 3: Solving Ax = b\n";
    Vector<3> x = A.solve(b);
    std::cout << "Solution Vector x:\n";
    x.print();
    std::cout << "-----------------------------\n";

    // Test 4: Identity Matrix (Solution should be b itself)
    std::cout << "Test 4: Solving with an Identity Matrix\n";
    SquareMatrix<3> I;
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            I[i][j] = (i == j) ? 1 : 0;  // Identity matrix
        }
    }
    Vector<3> xIdentity = I.solve(b);
    std::cout << "Solution (should be same as b):\n";
    xIdentity.print();
    std::cout << "-----------------------------\n";

    // Test 5: Nearly Singular Matrix (Checking numerical stability)
    std::cout << "Test 5: Nearly Singular Matrix\n";
    SquareMatrix<3> S;
    double nearSingular[3][3] = {
        {1, 1, 1},
        {2, 2.0001, 2},
        {3, 3, 3.0001}
    };

    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            S[i][j] = nearSingular[i][j];
        }
    }



    return 0;
}