#include "Vector.h"
#include <iostream>
#include <cmath>
template <int n>
Vector<n>::Vector()
{
    this->arr = new double[n];

    for (int i = 0; i < n; i++) {
        this->arr[i] = 0.0;                 
    }
    

}

template <int n>
Vector<n>::Vector(double *arr)
{
    this->arr = arr ;
}

template <int n>
Vector<n>::~Vector()
{
    if(arr != NULL){
        delete[] arr;
        
    }

    arr = NULL ;
}

template <int n>
Vector<n>::Vector(const Vector<n> &other)
{
    

    arr = new double[n];

    for(int i=0 ; i < n ; i++){
        arr[i] = other[i];
    }
}

template <int n>
Vector<n>::Vector(const Matrix<n, 1> &other)
{
    if (arr != NULL) {            
        delete[] arr;
    }
    arr = new double[n];             

    for (int i = 0; i < n; i++) {
        arr[i] = other[i][0];       
    }
}



template <int n>

Vector<n> &Vector<n>::operator=(const Vector<n> &other)
{
                     
        if (arr != NULL) {
            delete[] arr;                  
        }

        arr = new double[n];               
        for (int i = 0; i < n; i++) {
            arr[i] = other[i];         
        }
    
    return *this;         
    
}

template <int n>
Vector<n> Vector<n>::operator+(const Vector rhs) const
{
    
    if(arr != NULL){
        Vector<n> result;                      
        for (int i = 0; i < n; i++) {
            result[i] = this->arr[i] + rhs[i];  
        }
        return result;     
    }
    else{
        return rhs ;
    }
}

template <int n>
Vector<n> Vector<n>::operator-(const Vector<n> rhs) const
{
    if(arr != NULL){
        Vector<n> result;                      
        for (int i = 0; i < n; i++) {
            result[i] = this->arr[i] - rhs[i];  
        }
        return result;     
    }
    else{
        return rhs ;
    }
}

template <int n>
Vector<n> Vector<n>::operator*(const double val) const
{
    if(arr != NULL){
        Vector<n> result;                      
        for (int i = 0; i < n; i++) {
            result[i] = this->arr[i] * val;  
        }
        return result;     
    }

}

template <int n>
double Vector<n>::operator*(const Vector<n> rhs) const
{
    double result = 0.0 ;
    for (int i = 0; i < n; i++) {
        result += this->arr[i] * rhs[i];   
    }
    return result; 
}

template <int n>
double Vector<n>::magnitude() const
{
    if(arr != NULL){
        double sumsqr = 0.0 ;
        for (int i = 0; i < n; i++) {
            sumsqr += arr[i] * arr[i];   
        }


        return std::sqrt(sumsqr); 
    }

}

template <int n>
Vector<n>::operator Matrix<n, 1>() const
{
    Matrix<n, 1> matrix;    
    for(int i = 0 ; i < n ; i++){
        matrix[i][0] = arr[i];
    } 

    return matrix ;
}

template <>
Vector<3> Vector<3>::crossProduct(const Vector<3> rhs) const
{

    double resultarr[3];
   
   resultarr[0]= this->arr[1] * rhs[2] - this->arr[2] * rhs[1];
   resultarr[1] = this->arr[2] * rhs[0] - this->arr[0] * rhs[2];
   resultarr[2] = this->arr[0] * rhs[1] - this->arr[1] * rhs[0];

   Vector<3> resultvector;

   for (int i = 0; i < 3; i++) {
        resultvector[i] = resultarr[i];  
    }
   return resultvector;

}

template <int n>
Vector<n> Vector<n>::unitVector() const
{
    double unimagnitude = this->magnitude();

        if(unimagnitude == 0){
            throw ("Invalid_unit_vector");
        }

    
        double uniarr[n];
        for(int i = 0 ; i < n ; i++){
            uniarr[i]= arr[i] / unimagnitude ;
        }
        Vector<n> resultVec;

        for (int i = 0; i < n; i++) {
            resultVec[i] = uniarr[i];  
        }
        return resultVec;
    
}

template <int n>
int Vector<n>::getN() const
{
    return n ;
}
