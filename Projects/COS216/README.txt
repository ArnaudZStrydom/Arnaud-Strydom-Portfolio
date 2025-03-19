README.txt

PHP API README for u23536013

Overview:
This PHP API is designed to handle user registration and listing retrieval functionalities for a web application. 
Files and Directory Structure:
- api.php: Main entry point for handling API requests.
- CacheManager Class for managing caching of API responses.
- config.php: Configuration file containing database connection details.
- README.txt: This file.

Getting Started:
1. Ensure you have PHP installed on your server along with a MySQL database.
2. Import the provided MySQL database dump to set up the necessary tables.
3. Update the database connection details in config.php to match your environment.
4. Place the PHP files (api.php and CacheManager.php) in your web server's root directory.
5. Ensure that proper permissions are set for the files to be executable by the web server.

Usage:
User Registration:
- Endpoint: /api.php
- Method: POST
- Parameters:
  - type: Required. Must be set to "Register".
  - name: Required. User's name.
  - surname: Required. User's surname.
  - email: Required. User's email address.
  - password: Required. User's password.
  - cellphone: Required. User's cellphone number.
- Response: Returns a structured JSON response indicating success or failure of the registration process.

Listing Retrieval:
- Endpoint: /api.php
- Method: POST
- Parameters:
  - type: Required. Must be set to "GetAllListings".
  - apikey: Required. API key for authentication.
  - limit: Optional. Limit the number of results to retrieve.
  - sort: Optional. Sort the results based on a specified field.
  - order: Optional. Order the results in ascending or descending order.
  - fuzzy: Optional. Enable fuzzy search if set to true.
  - search: Optional. JSON object specifying search criteria.
  - return: Required. Specifies the fields to be returned by the API.
- Response: Returns a structured JSON response containing listings data with images.

Security Considerations - Hashing Algorithm Choice:
In this API, the password hashing is performed using the password_hash() function with the PASSWORD_DEFAULT algorithm. This function securely hashes passwords using a strong one-way hashing algorithm, providing protection against brute-force attacks and rainbow table attacks. The PASSWORD_DEFAULT algorithm is chosen for its security and flexibility, as it automatically selects the best algorithm available at the time of execution, ensuring that the hashing mechanism remains robust against evolving cryptographic threats.

Using password_hash() with PASSWORD_DEFAULT offers several advantages:
1. Strong Security: The PASSWORD_DEFAULT algorithm employs a strong and secure hashing algorithm, making it resistant to common cryptographic attacks.
2. Adaptive Algorithm: PASSWORD_DEFAULT automatically selects the best available hashing algorithm, ensuring that the API remains secure even as cryptographic standards evolve.
3. Ease of Use: The password_hash() function is simple to implement and abstracts the complexities of cryptographic hashing, reducing the risk of implementation errors.
4. Future-proofing: By using PASSWORD_DEFAULT, the API is prepared to adapt to changes in cryptographic best practices without requiring code modifications, ensuring long-term security.

Input Validation:
 Input validation ensures that data submitted to the API is accurate, complete, and meets specified criteria. It helps prevent injection attacks, data corruption, and unauthorized access by filtering out invalid or malicious input.
Validating input fields such as email addresses and passwords helps maintain data integrity and protects against common security vulnerabilities. For example, ensuring that email addresses contain an '@' symbol helps prevent incorrect or malformed email addresses from being accepted, reducing the risk of spamming or phishing attacks. Similarly, enforcing strong password requirements (length, uppercase, lowercase, digits, symbols) helps mitigate the risk of password-related security breaches, such as brute-force attacks or unauthorized account access.

Error Handling:
- The API returns appropriate HTTP status codes and error messages for invalid requests or server errors.
- Detailed error messages are provided to assist clients in debugging issues.

Default use :
 username: u23536013@up.ac.za
password: Zander10!

Credits:
This API was developed as part of a practical assignment for COS 216 at University of Pretoria.



