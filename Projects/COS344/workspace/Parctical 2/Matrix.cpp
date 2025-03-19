#include "Matrix.h"

template <int n, int m>
Matrix<n, m>::Matrix()
{
    arr = new double*[n];  // Allocate memory for rows
        for (int i = 0; i < n; i++) {
            arr[i] = new double[m];  // Allocate memory for columns in each row
            for (int j = 0; j < m; j++) {
                arr[i][j] = 0.0;  // Initialize all elements to 0
            }
        }
    
}

template <int n, int m>
Matrix<n, m>::Matrix(double **otherArr)
{
    
    arr = otherArr;
}

template <int n, int m>
Matrix<n, m>::Matrix(const Matrix &other)
{
    arr = new double*[n];  
    for (int i = 0; i < n; i++) {
        arr[i] = new double[m];  
    }

    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            arr[i][j] = other[i][j];
        }
    }
}

template <int n, int m>
Matrix<n, m>::~Matrix()
{
    if(arr != NULL){
        for (int i = 0; i < n; ++i) {
            if(arr[i] != NULL){
                delete[] arr[i];  
            }
            
        }
        delete[] arr;  
    }
}

template <int n, int m>
template <int a>
Matrix<n, a> Matrix<n, m>::operator*(const Matrix<m, a> rhs) const
{
    Matrix<n,a> resMatrix ;
    for (int i = 0; i < n; i++) {          
        for (int j = 0; j < a; j++) {      
            resMatrix[i][j] = 0;              
            for (int k = 0; k < m; k++) {  
                resMatrix[i][j] += this->arr[i][k] * rhs[k][j];
            }
        }
    }
    return resMatrix;
}

template <int n, int m>
Matrix<n, m> Matrix<n, m>::operator*(const double val) const
{
    Matrix<n,m> resMatrix ;
    for(int i = 0 ; i < n ; i ++){
        for(int j = 0 ; j < m ; j++){
            resMatrix[i][j] = arr[i][j]*val ;
        }
    }
    return resMatrix;
}

template <int n, int m>
Vector<n> Matrix<n,m>::operator*(const Vector<n> rhs) const
{
    Vector<n> result ;
    for (int i = 0; i < n; i++) {
        for(int j = 0 ; j < m ; j++){
            result[i] += this->arr[i][j] * rhs[j];
        }   
    }
    return result; 
}

template <int n, int m>
Matrix<n, m> Matrix<n, m>::operator+(const Matrix<n, m> rhs) const
{
    Matrix<n,m> resMatrix ;
    for(int i = 0 ; i < n ; i ++){
        for(int j = 0 ; j < m ; j++){
            resMatrix[i][j] = arr[i][j] + rhs[i][j] ;
        }
    }
    return resMatrix;
}

template <int n>
SquareMatrix<n> SquareMatrix<n>::operator!() const
{
    double det = this->determinant();
    
    if (det == 0) {
        throw "Inverse_does_not_exist";
    }
    // Create a deep copy of the matrix
    SquareMatrix<n> InvTemp;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            InvTemp.arr[i][j] = this->arr[i][j];  // Copy matrix elements
        }
    }

    // Create identity matrix
    SquareMatrix<n> inv;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            inv.arr[i][j] = (i == j) ? 1.0 : 0.0;  // Identity matrix
        }
    }

    // Perform Gaussian elimination
    for (int i = 0; i < n; i++) {
        // Find the pivot
        int pivot = i;
        for (int j = i + 1; j < n; j++) {
            if (std::abs(InvTemp.arr[j][i]) > std::abs(InvTemp.arr[pivot][i])) {
                pivot = j;
            }
        }

        // Swap rows in both matrices if needed
        if (pivot != i) {
            std::swap(InvTemp.arr[i], InvTemp.arr[pivot]);
            std::swap(inv.arr[i], inv.arr[pivot]);
        }

        // Scale the pivot row to make the diagonal element 1
        double diagValue = InvTemp.arr[i][i];
        for (int j = 0; j < n; j++) {
            InvTemp.arr[i][j] /= diagValue;
            inv.arr[i][j] /= diagValue;
        }

        // Make other column entries 0
        for (int j = 0; j < n; j++) {
            if (j == i) continue;
            double factor = InvTemp.arr[j][i];
            for (int k = 0; k < n; k++) {
                InvTemp.arr[j][k] -= factor * InvTemp.arr[i][k];
                inv.arr[j][k] -= factor * inv.arr[i][k];
            }
        }
    }

    return inv;  
}



template <int n, int m>
Matrix<m, n> Matrix<n, m>::operator~() const
{
    Matrix<m,n> resMatrix ;
    for(int i = 0 ; i < n ; i ++){
        for(int j = 0 ; j < m ; j++){
            resMatrix[j][i] = arr[i][j] ;
        }
    }
    return resMatrix;
}

template <int n, int m>
int Matrix<n, m>::getM() const
{
    return m;
}

template <int n, int m>
int Matrix<n, m>::getN() const
{
   return n ;
}

template <int n>
SquareMatrix<n>::SquareMatrix():Matrix<n,n>()
{
    
}

template <int n>
SquareMatrix<n>::SquareMatrix(double **arr):Matrix<n,n>(arr)
{
    
}


template <int n>
SquareMatrix<n>::~SquareMatrix()
{
    //Cleanup by Matrix destructor
}

template <int n>
Vector<n> SquareMatrix<n>::solve(const Vector<n> vec) const
{

        double augmented[n][n + 1];  
       
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                augmented[i][j] = this->arr[i][j];
            }
            augmented[i][n] = vec[i];  
        }
    
        
        for (int i = 0; i < n; ++i) {
            
            int maxRow = i;
            for (int j = i + 1; j < n; ++j) {
                if (std::abs(augmented[j][i]) > std::abs(augmented[maxRow][i])) {
                    maxRow = j;
                }
            }
    
           
            if (maxRow != i) {
                for (int j = 0; j < n + 1; ++j) {
                    std::swap(augmented[i][j], augmented[maxRow][j]);
                }
            }
    
           
            for (int j = i + 1; j < n; ++j) {
                double factor = augmented[j][i] / augmented[i][i];
                for (int k = i; k < n + 1; ++k) {
                    augmented[j][k] -= factor * augmented[i][k];
                }
            }
        }
    
        
        Vector<n> solution; 
        for (int i = n - 1; i >= 0; --i) {
            solution[i] = augmented[i][n] / augmented[i][i];  
            for (int j = i - 1; j >= 0; --j) {
                augmented[j][n] -= augmented[j][i] * solution[i];  
            }
        }
    
        
        return solution;
}

template <int n>
double SquareMatrix<n>::determinant() const
{
    SquareMatrix<n> detMatr;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            detMatr.arr[i][j] = this->arr[i][j]; 
        }
    }

    double det = 1.0;

    for (int i = 0; i < n; i++) {
        int pivot = i;
        for (int j = i + 1; j < n; j++) {
            if (std::abs(detMatr.arr[j][i]) > std::abs(detMatr.arr[pivot][i])) {
                pivot = j;
            }
        }

        if (pivot != i) {
            std::swap(detMatr.arr[i], detMatr.arr[pivot]); // Swap rows
            det *= -1; // Swapping rows changes determinant sign
        }

        if (detMatr.arr[i][i] == 0) {
            return 0; // Determinant is 0 if there's a zero pivot
        }

        det *= detMatr.arr[i][i]; // Multiply diagonal element to determinant

        for (int j = i + 1; j < n; j++) {
            double factor = detMatr.arr[j][i] / detMatr.arr[i][i];
            for (int k = i + 1; k < n; k++) {
                detMatr.arr[j][k] -= factor * detMatr.arr[i][k];
            }
        }
    }

    if(det == 0){
        throw "Unsolvable_set_of_linear_equations";
    }

    return det;
}

template <int n>
IdentityMatrix<n>::IdentityMatrix():SquareMatrix<n>()
{
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            this->arr[i][j] = (i == j) ? 1.0 : 0.0;  // Identity matrix
        }
    }
}

template <int n>
IdentityMatrix<n>::~IdentityMatrix()
{
    
}

template <int n, int m>
inline Matrix<n, m> &Matrix<n, m>::operator=(const Matrix<n, m> &other)
{
    if (this != &other) {                   
        
        if (arr != NULL) {
            for (int i = 0; i < n; ++i) {
                delete[] arr[i];  
            }
            delete[] arr;  
        }

      
        arr = new double*[n];  
        for (int i = 0; i < n; ++i) {
            arr[i] = new double[m];  
            for (int j = 0; j < m; ++j) {
                arr[i][j] = other[i][j];  
            }
        }
    }
    return *this; 
}


