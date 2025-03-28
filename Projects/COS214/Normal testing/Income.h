#ifndef INCOME_H
#define INCOME_H

#include "TaxType.h"
#include <string>

class Income : public TaxType {

private:
	double incomeTax;
	char cType;
	double baseSalary;      // Base salary for the job
    double bonus;           // Monthly bonus (if any)
    double deductions;      // Monthly deductions (if any)

public:
// Constructor to initialize with base salary
    Income(double salary, double rate, const char category) 
        : TaxType(rate, category), baseSalary(salary), bonus(0.0), deductions(0.0),incomeTax(rate) {}

    Income(double salary) : Income(salary, 0.0, 'A') {}

    // Method to calculate monthly income
    double calculateMonthlyIncome() const;

    // Optional methods to adjust income
    void addBonus(double amount);
    void applyDeductions(double amount);
    // In Income.h
    // Income.h
    double payTaxes(TaxType& taxType);



    // Getters
    double getBaseSalary() const { return baseSalary; }
    double getBonus() const { return bonus; }
    double getDeductions() const { return deductions; }
	

	void setTax(double rate) override;

    double calculateTax(double val) override;
};

#endif
