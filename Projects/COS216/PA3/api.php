<?php

class API {
    private static $instance;

    public static function getInstance() {
        if (!isset(self::$instance)) {
            self::$instance = new API();
        }
        return self::$instance;
    }

    private function __construct() {
        require_once('COS216/PA3/config.php');
    }

    public function handleRequest() {
        if ($_SERVER["REQUEST_METHOD"] !== "POST") {
            $this->sendResponse(405, "Method Not Allowed - Only POST method is allowed");
            return;
        }


        $json = file_get_contents('php://input');
        $data = json_decode($json, true);



       
        if (!isset($data['type'])) {
            $this->sendResponse(400, "Bad Request - 'type' field is required");
            return;
        }

        
        switch ($data['type']) {
            case 'Register':
                $this->handleRegistration($data);
                break;
            case 'GetAllLisitngs':
                $this->handleGetAllListings($data);
                break;
            default:
                $this->sendResponse(400, "Bad Request - Invalid 'type' field");
                break;
        }
    }

    private function handleRegistration($data) {
        if (!$this->validateRequestBody($data)) {
            $this->sendResponse(400, "Bad Request - Invalid JSON POST body");
            return;
        }

        if (empty($data)) {
            $this->sendResponse(400 ,"Post parameters are missing");
            return;
        }

        $validationErrors = $this->validateUserData($data);
        if (!empty($validationErrors)) {
            
            $this->sendResponse(400, "Bad Request", ["errors" => $validationErrors]);
            return;
        }

        
        if ($this->userExists($data['email'], $data['cellphone'])) {
            $this->sendResponse(409, "Conflict - User already exists");
            return;
        }

        
        $apiKey = $this->generateAPIKey();
        $success = $this->insertUser($data, $apiKey);

        
        if ($success) {
            
            $this->sendResponse(201, "success", ["apikey" => $apiKey]);
        } else {
            
            $this->sendResponse(500, "Internal Server Error - Failed to insert user into database");
        }
    }

    private function handleGetAllListings($data) {

        if (empty($data)) {
            $this->sendResponse(400 ,"Post parameters are missing");
            return;
        }
        
    
        if (!$this->validateAPIKey($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Invalid API key");
            return;
        }

        
        if (!$this->validateLimit($data['limit'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'limit' parameter");
            return;
        }

        if (!$this->validateSort($data['sort'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'sort' parameter");
            return;
        }

        if (!$this->validateOrder($data['order'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'order' parameter");
            return;
        }

      

        if (!$this->validateFuzzy($data['fuzzy'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'fuzzy' parameter");
            return;
        }

        if (!$this->validateSearch($data['search'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'search' parameter");
            return;
        }

        if ( !isset($data['return'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }
        
        $sql = "SELECT ";
        if (in_array('*', $data['return'])) {
            $sql .= "*";
        } else {
            $sql .= implode(', ', $data['return']);
        }
        $sql .= " FROM listings";
    
        
        if (isset($data['search']) && !empty($data['search']) && $data['fuzzy'] == false) {
            $sql .= " WHERE ";
            $searchConditions = [];
            foreach ($data['search'] as $column => $value) {
                $searchConditions[] = "$column = '$value'";
            }
            $sql .= implode(" AND ", $searchConditions);
        }
    
        if (isset($data['search']) && !empty($data['search']) && $data['fuzzy'] == true) {
            $sql .= " WHERE ";
            $searchConditions = [];
            foreach ($data['search'] as $column => $value) {
                $searchConditions[] = "$column LIKE '%$value%'";
            }
            $sql .= implode(" AND ", $searchConditions);
        }
    
    
        
        if (isset($data['sort']) && isset($data['order'])) {
            $sql .= " ORDER BY {$data['sort']} {$data['order']}";
        }
    
        
        if (isset($data['limit'])) {
            $sql .= " LIMIT ?";
        }
    
        
        require_once('COS216/PA3/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        $stmt = $mysqli->prepare($sql);
    
        if (!$stmt) {
            $this->sendResponse(500, "Internal Server Error - Failed to prepare SQL statement");
            return;
        }
    
        

        if (isset($data['limit'])) {
            $stmt->bind_param("i", $data['limit']);
        }
    
        
        $stmt->execute();
    
        
        $result = $stmt->get_result();
        $listings = $result->fetch_all(MYSQLI_ASSOC);
    
        
        $stmt->close();
        $mysqli->close();
    
        $listingsWithImages = [];
    foreach ($listings as $listing) {
        $imageUrls = $this->fetchImage($listing['id']);
        $listing['images'] = $imageUrls;
        $listingsWithImages[] = $listing;
    }
    
        

        
        $this->sendResponse(200, "Success", $listingsWithImages);
    }
    
    private function fetchImage($listingId) {
        $imageUrl = "https://wheatley.cs.up.ac.za/api/getimage?listing_id=$listingId";
    
        $curl = curl_init();
    
        curl_setopt_array($curl, [
            CURLOPT_URL => $imageUrl,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HEADER => false,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_SSL_VERIFYPEER => false, 
            CURLOPT_SSL_VERIFYHOST => false, 
        ]);
    
        $response = curl_exec($curl);
    
        if (curl_errno($curl)) {
            $error = curl_error($curl);
            
        }
    
        
        curl_close($curl);
    
        
        $imageUrls = json_decode($response, true);
    
        
        return $imageUrls;
    }

    private function validateRequestBody($data) {
        return isset($data['type']) && isset($data['name']) && isset($data['surname']) && isset($data['email']) && isset($data['password']) && isset($data['cellphone']);
    }

    private function validateUserData($data) {
        $errors = [];
    
        $emailRegex = '/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/';
        $passwordRegex = '/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/';
        $nameRegex = '/^[a-zA-Z\s]+$/';
        $phoneRegex = '/^\+(?:\d{11})$|^0(?:\d{9})$/';
    
        if (!preg_match($emailRegex, $data['email'])) {
            $errors[] = "Invalid email address";
        }
    
        if (!preg_match($passwordRegex, $data['password'])) {
            $errors[] = "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long";
        }
    
        if (!preg_match($nameRegex, $data['name'])) {
            $errors[] = "Invalid name";
        }
    
        if (!preg_match($nameRegex, $data['surname'])) {
            $errors[] = "Invalid surname";
        }
    
        if (!preg_match($phoneRegex, $data['cellphone'])) {
            $errors[] = "Invalid cellphone number";
        }
    
        return $errors;
    }

    private function userExists($email, $cellphone) {
        require_once('COS216/PA3/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        
        $stmtEmail = $mysqli->prepare("SELECT * FROM users WHERE email = ?");
        $stmtEmail->bind_param("s", $email);
        $stmtEmail->execute();
        $resultEmail = $stmtEmail->get_result();
        $stmtEmail->close();
    
        
        $stmtCellphone = $mysqli->prepare("SELECT * FROM users WHERE cellphone = ?");
        $stmtCellphone->bind_param("s", $cellphone);
        $stmtCellphone->execute();
        $resultCellphone = $stmtCellphone->get_result();
        $stmtCellphone->close();
    
        $mysqli->close();
    
        
        return ($resultEmail->num_rows > 0 || $resultCellphone->num_rows > 0);
    }

    private function insertUser($data , $apiKey) {
        
        $hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);

        require_once('COS216/PA3/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }

        $stmt = $mysqli->prepare("INSERT INTO users (name, surname, email, password, cellphone, api_key) VALUES (?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssss", $data['name'], $data['surname'], $data['email'], $hashedPassword, $data['cellphone'], $apiKey);
        $success = $stmt->execute();
        $stmt->close();
        $mysqli->close();

        return $success;
    }

    private function generateAPIKey() {
        $length = rand(10, 32);
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $apiKey = '';
        $max = strlen($characters) - 1;
    
        for ($i = 0; $i < $length; $i++) {
            $apiKey .= $characters[rand(0, $max)];
        }
    
        return $apiKey;
    }

    private function sendResponse($status, $message, $data = []) {
        http_response_code($status);
        echo json_encode(['status' => $message, 'timestamp' => time(), 'data' => $data]);
    }

    private function validateApiKey($apiKey) {
        
        require_once('COS216/PA3/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        
        $stmt = $mysqli->prepare("SELECT * FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $apiKey);
        $stmt->execute();
    
       
        $result = $stmt->get_result();
    
        $isValid = $result->num_rows > 0;
    
        $stmt->close();
        $mysqli->close();
    
        return $isValid;
    }

    private function validateLimit($limit) {
        
        return !isset($limit) || is_numeric($limit) && $limit > 0 && $limit < 500;
    }
    
    private function validateSort($sort) {
        
        $allowedSortOptions = ['id', 'title', 'location', 'price', 'bedrooms', 'bathrooms', 'parking_spaces'];
    
        return !isset($limit) || in_array($sort, $allowedSortOptions);
    }
    
    private function validateOrder($order) {
        
        return !isset($limit) || in_array($order, ['ASC', 'DESC']);
    }
    
    private function validateFuzzy($fuzzy) {
        return !isset($limit) || is_bool($fuzzy);
    }

    private function validateTypeRorS($type) {
        return ($type == 'rent' || $type == 'sale');
    }

    
    private function validateSearch($search) {

        
        if ($search === null) {
            return false; 
        }
    
        
        $allowedColumns = ['id', 'title', 'location', 'price_min', 'price_max', 'bedrooms', 'bathrooms', 'parking_spaces', 'amenities', 'type'];
    
        
        foreach ($search as $column => $value) {
            if (!in_array($column, $allowedColumns)) {
                return false; 
            }
        }
    
    
        return true; 
    }
}

$api = API::getInstance();
$api->handleRequest();

?>
