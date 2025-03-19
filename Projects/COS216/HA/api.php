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
            case 'GetAllListings':
                $this->handleGetAllListings($data);
                break;

            case 'Login':
                $this->handleLogin($data);
                break;
            
            case 'SavePreferences':
                $this->handleSavePreferences($data);
                break;

            case 'LoadPreferences': 
                $this->handleLoadPreferences($data);
                break;
            case 'AddToFavorites':
                $this->handleAddToFavorites($data);
                break;

            case 'GetAllFavouriteListings':
                $this->handleGetAllFavoriteListings($data);
                break;

            case'RemoveFromFavorites':
                $this->handleRemoveFromFavorites($data) ;
                break;

            case 'CreateAuction':
                $this->handleCreateAuction($data);
                break;

            case 'GetAllAuctions':
                $this->handleGetAuctions($data);
                break;
    
            case 'UpdateAuction':
                $this->handleUpdateAuction($data);
                break;
    
            case 'GetAuction':
                $this->handleGetAuction($data);
                break;
            case 'GetAllActiveAuctions':
                $this->handleGetAllActiveAuctions($data);
                break;
            case 'GetAllAuctions':
                $this->handleGetAllAuctions($data);
                break;
            case 'getAuctionListings':
                $this->handleGetAuctionListings($data);
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
            
            $this->sendResponse(200, "Success", ["apikey" => $apiKey]);
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
        if ( $data['return'] === "*") {
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
    
        if (isset($data['sort']) && !isset($data['order'])) {
            if(is_array($data['sort'])){
                $ordbydata = implode(', ', $data['sort']);
            }else{
                $ordbydata = $data['sort'];
            }
            
            $sql .= " ORDER BY $ordbydata ";
        }
        
        if (isset($data['sort']) && isset($data['order'])) {
            if(is_array($data['sort'])){
                $ordbydata = implode(', ', $data['sort']);
            }else{
                $ordbydata = $data['sort'];
            }
            $sql .= " ORDER BY $ordbydata {$data['order']}";
        }
    
        
        if (isset($data['limit'])) {
            $sql .= " LIMIT ?";
        }
    
        
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        $stmt = $mysqli->prepare($sql);
    
        if (!$stmt) {
            $this->sendResponse(500, "Internal Server Error - Failed to prepare SQL statement $sql");
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
    
        return $errors;
    }

    private function userExists($email, $cellphone) {
        require_once('COS216/PA4/config.php');
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

        require_once('COS216/PA4/config.php');
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
        
        require_once('COS216/PA4/config.php');
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
            return true; 
        }
    
        
        $allowedColumns = ['id', 'title', 'location', 'price_min', 'price_max', 'bedrooms', 'bathrooms', 'parking_spaces', 'amenities', 'type'];
    
        
        foreach ($search as $column => $value) {
            if (!in_array($column, $allowedColumns)) {
                return false; 
            }
        }
    
    
        return true; 
    }

    private function handleLogin($data) {
        if (!isset($data['email']) || !isset($data['password'])) {
            $this->sendResponse(400, "Bad Request - Email and password are required");
            return;
        }
        require_once('COS216/PA4/config.php');
    
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        $stmt = $mysqli->prepare("SELECT id, password, api_key FROM users WHERE email = ?");
        $stmt->bind_param("s", $data['email']);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows !== 1) {
            $this->sendResponse(401, "Unauthorized access - Incorrect email or password");
            return;
        }
    
        $row = $result->fetch_assoc();
        if (!password_verify($data['password'], $row['password'])) {
            $this->sendResponse(401, "Unauthorized access - Incorrect email or password");
            return;
        }
    

        setcookie("apikey", $row['api_key'], time() + (86400 * 30), "/");
    

        $user_id = $row['id'];
        setcookie("user_id", $user_id, time() + (86400 * 30), "/");
    
        $stmt->close();
        $mysqli->close();
    
        $this->sendResponse(200, "Success", array("apikey" => $row['api_key']));
    }

    private function handleSavePreferences($data) {
        if (!isset($data['apikey']) || !isset($data['filters']) || !isset($data['sorting_option'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }
    
        if (!$this->validateApiKey($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Invalid API key");
            return;
        }
    
        $userId = $this->getUserIdByApiKey($data['apikey']);
    
        if (!$userId) {
            $this->sendResponse(404, "User not found");
            return;
        }
    
        $filters = json_encode($data['filters']);
        $sortingOption = json_encode($data['sorting_option']);
    
        // Clear existing preferences
        $this->clearExistingPreferences($userId);
    
        // Save new preferences to database
        $success = $this->savePreferencesToDatabase($userId, $filters, $sortingOption);
    
        if ($success) {
            $this->sendResponse(200, "Success", ["message" => "Preferences saved successfully"]);
        } else {
            $this->sendResponse(500, "Internal Server Error", ["message" => "Failed to save preferences"]);
        }
    }

    private function getUserIdByApiKey($apiKey) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("SELECT id FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $apiKey);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows === 1) {
            $row = $result->fetch_assoc();
            $userId = $row['id'];
        } else {
            $userId = null;
        }
    
        $stmt->close();
        $mysqli->close();
    
        return $userId;
    }
    
    private function clearExistingPreferences($userId) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("DELETE FROM preferences WHERE user_id = ?");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
    
        $stmt->close();
        $mysqli->close();
    }
    
    private function savePreferencesToDatabase($userId, $filters, $sortingOption) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("INSERT INTO preferences (user_id, filters, sorting_option) VALUES (?, ?, ?)");
        
        
        $stmt->bind_param("iss", $userId, $filters, $sortingOption);
        $result = $stmt->execute();
    
        $stmt->close();
        $mysqli->close();
    
        return $result;
    }

    private function handleLoadPreferences($data) {
        if (!isset($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Missing API key");
            return;
        }
    
        if (!$this->validateApiKey($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Invalid API key");
            return;
        }
    
        $userId = $this->getUserIdByApiKey($data['apikey']);
    
        if (!$userId) {
            $this->sendResponse(404, "User not found");
            return;
        }
    
        $preferences = $this->loadPreferencesFromDatabase($userId);
    
        if ($preferences !== false) {
            $apiKey =$data['apikey'];
    

            $this->handleGetAllListingsWithPreferences($apiKey, $preferences);

        } else {
            $data = [
                'type' => 'GetAllListings',
                'apikey' => $data['apikey'],
                'limit' => 35,
                'return' => "*",
                'fuzzy' => true,
            ];
    
        
    
            $this->handleGetAllListings($data);
        }
    }

    private function loadPreferencesFromDatabase($userId) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("SELECT filters, sorting_option FROM preferences WHERE user_id = ?");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows === 1) {
            $row = $result->fetch_assoc();
            $preferences = [
                "filters" => $row['filters'],
                "sorting_option" => $row['sorting_option']
            ];
        } else {
            $preferences = false;
        }
    
        $stmt->close();
        $mysqli->close();
    
        return $preferences;
    }
    
    private function handleGetAllListingsWithPreferences($apiKey, $preferences) {

        $sortOptionArray = json_decode($preferences['sorting_option'], true);
        $searchFilterArray = json_decode($preferences['filters'], true);

        
        $sortOption = $sortOptionArray['sort']; 


        $data = [
            'type' => 'GetAllListings',
            'apikey' => $apiKey,
            'limit' => 35,
            'return' => "*",
            'fuzzy' => true,
        ];
        

        if (!empty($sortOption)) {
            $data['sort'] = $sortOption;
        }
        

        if (!empty($searchFilterArray)) {
            $data['search'] = $searchFilterArray;
        }

    

        $this->handleGetAllListings($data);
    }

    private function handleAddToFavorites($data) {
        if (empty($data['apikey']) || empty($data['listingId'])) {
            $this->sendResponse(400, "Bad Request - 'apikey' and 'listingId' fields are required");
            return;
        }

        if (!$this->validateApiKey($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Invalid API key");
            return;
        }

        $userId = $this->getUserIdByApiKey($data['apikey']);

        if (!$userId) {
            $this->sendResponse(404, "User not found");
            return;
        }


        $listingId = $data['listingId'];

        if ($this->listingExistsInFavorites($userId, $listingId)) {
            $this->sendResponse(409, "Conflict - Listing already in favorites");
            return;
        }

        if ($this->addToFavorites($userId, $listingId)) {
            $this->sendResponse(200, "Success", ["message" => "Listing added to favorites"]);
        } else {
            $this->sendResponse(500, "Internal Server Error", ["message" => "Failed to add listing to favorites"]);
        }
    }

    private function listingExistsInFavorites($userId, $listingId) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("SELECT * FROM user_favorites WHERE user_id = ? AND listing_id = ?");
        $stmt->bind_param("ii", $userId, $listingId);
        $stmt->execute();
        $result = $stmt->get_result();
    
        $exists = $result->num_rows > 0;
    
        $stmt->close();
        $mysqli->close();
    
        return $exists;
    }

    private function addToFavorites($userId, $listingId) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("INSERT INTO user_favorites (user_id, listing_id) VALUES (?, ?)");
        $stmt->bind_param("ii", $userId, $listingId);
        $success = $stmt->execute();
    
        $stmt->close();
        $mysqli->close();
    
        return $success;
    }

    private function handleGetAllFavoriteListings($data) {
        if (empty($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - 'apikey' field is required");
            return;
        }
    
        if (!$this->validateApiKey($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Invalid API key");
            return;
        }
    
        $userId = $this->getUserIdByApiKey($data['apikey']);
    
        if (!$userId) {
            $this->sendResponse(404, "User not found");
            return;
        }
    
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("SELECT * FROM user_favorites WHERE user_id = ?");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $result = $stmt->get_result();
    
        $favorites = [];
        while ($row = $result->fetch_assoc()) {
            $favorites[] = $row['listing_id'];
        }
    
        $stmt->close();
        $mysqli->close();
    

        $favoritesdata = $this->getListingsDetails($favorites,$data);
        if($favoritesdata != []){
            $this->sendResponse(200, "Success", $favoritesdata);
        }else{
            $this->sendResponse(200, "Success", $favoritesdata);
        }
        

        
    }

    private function getListingsDetails($listingIds,$data) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        if (!$this->validateFuzzy($data['fuzzy'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'fuzzy' parameter");
            return;
        }

        if (!$this->validateSearch($data['search'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'search' parameter");
            return;
        }

        $userId = $this->getUserIdByApiKey($data['apikey']);

        $placeholders = implode(',', array_fill(0, count($listingIds), '?'));
        $sql = "SELECT * FROM listings";
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

        if (!isset($data['search']) && empty($data['search']) && isset($listingIds) && isset($userId)){
            $sql .= " WHERE id IN ($placeholders)";
        }else if(isset($listingIds) && isset($userId)){
            $sql .= " AND id IN ($placeholders)";
        }
       

        $stmt = $mysqli->prepare($sql);
    
        if (!$stmt) {
            $this->sendResponse(500, "Internal Server Error - Failed to prepare SQL statement");
            return [] ;
        }
    
       
        $types = str_repeat('i', count($listingIds)); 
        $stmt->bind_param($types, ...$listingIds);
        $stmt->execute();
        $result = $stmt->get_result();
    
        $listings = [];
        while ($row = $result->fetch_assoc()) {
            $listings[] = $row;
        }
    
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


    private function handleRemoveFromFavorites($data) {
        if (!isset($data['apikey']) || !isset($data['listing_id'])) {
            $this->sendResponse(400, "Bad Request - 'apikey' and 'listingId' fields are required");
            return;
        }
    
        if (!$this->validateApiKey($data['apikey'])) {
            $this->sendResponse(400, "Bad Request - Invalid API key");
            return;
        }
    
        $userId = $this->getUserIdByApiKey($data['apikey']);
    
        if (!$userId) {
            $this->sendResponse(404, "User not found");
            return;
        }
    
        $listingId =$data['listing_id'];
    
        if (!$this->listingExistsInFavorites($userId, $listingId)) {
            $this->sendResponse(404, "Listing not found in favorites");
            return;
        }
    
        if ($this->removeFromFavorites($userId, $listingId)) {
            $this->sendResponse(200, "Success", ["message" => "Listing removed from favorites"]);
        } else {
            $this->sendResponse(500, "Internal Server Error", ["message" => "Failed to remove listing from favorites"]);
        }
    }
    
    private function removeFromFavorites($userId, $listingId) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        $stmt = $mysqli->prepare("DELETE FROM user_favorites WHERE user_id = ? AND listing_id = ?");
        $stmt->bind_param("ii", $userId, $listingId);
        $success = $stmt->execute();
    
        $stmt->close();
        $mysqli->close();
    
        return $success;
    }

    private function handleCreateAuction($data) {

        $requiredParams = ['auction_id', 'auction_name', 'starting_price', 'start_date', 'end_date', 'listing_id'];
        foreach ($requiredParams as $param) {
            if (!isset($data[$param])) {
                $this->sendResponse(400, "Bad Request - Missing required parameter: $param");
                return;
            }
        }
    

        $dateFormat = 'Y-m-d\TH:i'; 


        $startDate = DateTime::createFromFormat("Y-m-d\TH:i", $data['start_date']);
        $endDate = DateTime::createFromFormat("Y-m-d\TH:i", $data['end_date']);

        if (!$startDate || !$endDate) {
            $this->sendResponse(400, "Bad Request - Invalid date format. Use YYYY-MM-DD HH:MM:SS.");
            return;
        }

        if ($startDate >= $endDate) {
            $this->sendResponse(400, "Bad Request - End date must be after start date.");
            return;
        }

        $currentDateTime = new DateTime("now", new DateTimeZone('Africa/Johannesburg'));
        $currentDateTimeFormatted = $currentDateTime->format($dateFormat); 

        if ($currentDateTime < $startDate) {
            $state = "Waiting";
        } else if ($currentDateTime >= $startDate && $currentDateTime <= $endDate) {
            $state = "Ongoing";
        } else if ($currentDateTime > $endDate) {
            $state = "Done";
        }

        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Internal Server Error - Failed to connect to database");
            return;
        }

        $stmt = $mysqli->prepare("INSERT INTO Auctions (auction_id, auction_name, starting_price, start_date, end_date, listing_id, auction_status) VALUES (?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssissss", $data['auction_id'], $data['auction_name'], $data['starting_price'], $data['start_date'], $data['end_date'], $data['listing_id'], $state);
        $success = $stmt->execute();
    
        if ($success) {
            $this->sendResponse(200, "Success", ["message" => "Auction created successfully"]);
        } else {
            $this->sendResponse(500, "Internal Server Error - Failed to create auction: " . $mysqli->error);
        }
    
    
        $stmt->close();
        $mysqli->close();
    }
    private function handleUpdateAuction($data) {
        if (!isset($data['auction_id']) || !isset($data['auction_status'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }

        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Internal Server Error - Failed to connect to database");
            return;
        }
    
        $stmt = $mysqli->prepare("UPDATE Auctions SET auction_status = ? WHERE auction_id = ?");
        $stmt->bind_param("ss" , $data['auction_status'], $data['auction_id']);
        $success = $stmt->execute();
    
        if ($success) {
            $this->sendResponse(200, "Success", ["message" => "Auction updated successfully"]);
        } else {
            $this->sendResponse(500, "Internal Server Error - Failed to update auction");
        }
    
        $stmt->close();
        $mysqli->close();
    }

    private function handleGetAuction($data) {
        if (!isset($data['auction_id'])) {
            $this->sendResponse(400, "Bad Request - Missing auction ID");
            return;
        }

        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Internal Server Error - Failed to connect to database");
            return;
        }
    
        $stmt = $mysqli->prepare("SELECT * FROM Auctions WHERE auction_id = ?");
        $stmt->bind_param("s", $data['auction_id']);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows === 0) {
            $this->sendResponse(404, "Not Found - Auction not found");
            return;
        }
    
        $auction = $result->fetch_assoc();
        $this->sendResponse(200, "Success",  $auction);
    
        $stmt->close();
        $mysqli->close();
    }

    private function handleGetAllActiveAuctions($data) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Internal Server Error - Failed to connect to database");
            return;
        }
    
        $state = "Ongoing";
        $stmt = $mysqli->prepare("SELECT auction_id , auction_name , listing_id FROM Auctions WHERE auction_status = ? ");
        if (!$stmt) {
            $this->sendResponse(500, "Internal Server Error - Failed to prepare statement");
            return;
        }
    
        $stmt->bind_param("s", $state);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if (!$result) {
            $this->sendResponse(500, "Internal Server Error - Failed to execute statement");
            return;
        }
    
        $auctions = [];
        while ($row = $result->fetch_assoc()) {
            $auctions[] = $row;
        }
    
            $this->sendResponse(200, "Success",  $auctions);
        
    
        $stmt->close();
        $mysqli->close();
    }

    private function handleGetAuctions($data) {
        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Internal Server Error - Failed to connect to database");
            return;
        }
    

        $stmt = $mysqli->prepare("SELECT * FROM Auctions ");
        if (!$stmt) {
            $this->sendResponse(500, "Internal Server Error - Failed to prepare statement");
            return;
        }

        $stmt->execute();
        $result = $stmt->get_result();
    
        if (!$result) {
            $this->sendResponse(500, "Internal Server Error - Failed to execute statement");
            return;
        }
    
        $auctions = [];
        while ($row = $result->fetch_assoc()) {
            $auctions[] = $row;
        }
    
            $this->sendResponse(200, "Success",  $auctions);
        
    
        $stmt->close();
        $mysqli->close();
    }

    private function handleGetAuctionListings($data) {
        if (!isset($data['listing_id'])) {
            $this->sendResponse(400, "Bad Request - Missing auction ID");
            return;
        }

        if (!isset($data['auction_id'])) {
            $this->sendResponse(400, "Bad Request - Missing auction ID");
            return;
        }
    

        require_once('COS216/PA4/config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Internal Server Error - Failed to connect to database");
            return;
        }
    
        $stmt = $mysqli->prepare("SELECT * FROM listings WHERE id = ?");
        if (!$stmt) {
            $this->sendResponse(500, "Internal Server Error - Failed to prepare statement");
            return;
        }
    
        $stmt->bind_param("i", $data['listing_id']);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if (!$result) {
            $this->sendResponse(500, "Internal Server Error - Failed to execute SQL statement");
            return;
        }
    
        $listings = [];
        while ($row = $result->fetch_assoc()) {
            $listings[] = $row;
        }
    
        $stmt->close();
        $mysqli->close();
    
        if (empty($listings)) {
            $this->sendResponse(404, "No listings found for the given ID");
            return;
        }
    
        $listingsWithImages = [];
        foreach ($listings as $listing) {
            $imageUrls = $this->fetchImage($listing['id']);
            $listing['images'] = $imageUrls;
            $listingsWithImages[] = $listing;
        }

        $auctionId = $data['auction_id'];
        $listingsWithImagesandid = [];
        foreach ($listingsWithImages as &$listing) {
            $listing['auction_id'] = $auctionId;
            $listingsWithImagesandid[] = $listing ;
        }
        
        $this->sendResponse(200, "Success", $listingsWithImagesandid);
    
        
    }
    



    

    
}

$api = API::getInstance();
$api->handleRequest();

?>
