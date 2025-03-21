#ifndef TAXTYPE_H
#define TAXTYPE_H

class TaxType {

private:
	double taxRate;
	char cType;

public:
	TaxType(double rate, char type);
	
    virtual ~TaxType() {} // Virtual Destructor
	
	virtual double calculateTax(double val);
	virtual void setTax(double rate);
	virtual double getTaxRate();
	char getTaxType();
};

#endif
