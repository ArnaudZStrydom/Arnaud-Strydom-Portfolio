<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
ini_set('log_errors', 1);
ini_set('error_log', 'php-error.log'); // Change this to your desired log file location

class API {
    private static $instance;

    public static function getInstance() {
        if (!isset(self::$instance)) {
            self::$instance = new API();
        }
        return self::$instance;
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
            case 'RegisterAdmin':
                $this->handleRegistrationAdmin($data);
                break;
            case 'GetMovies':
                $this->handleGetMovies($data);
                break;
            case 'GetSeries':
                $this->handleGetSeries($data);
                break;

            case 'Login':
                $this->handleLogin($data);
                break;
            case 'getRecommendations':              /*#added this one*/
                $this->handleGetRecommendations($data);
                break;
            case 'getProfiles':              /*#added this one too*/
                $this->handleGetProfiles($data);
                break;
            case 'updateProfile':              /*#added this one too*/
                $this->handleUpdateProfile($data);
                break;
            case 'addProfile':              /*#added this one too*/
                $this->handleAddProfile($data);
                break;
            case 'removeProfile':              /*#added this one too*/
                $this->handleRemoveProfile($data);
                break;
            case 'GetDetails':
                $this->getDetails($data);
                break;
            case 'GetReviews':
                $this->getReviews($data);
                break;
            case 'SubmitReview':
                $this->submitReview($data);
                break;
            default:
                $this->sendResponse(400, "Bad Request - Invalid 'type' field");
                break;
        }
    }

    private function sendResponse($status, $message, $data = []) {
        http_response_code($status);
        echo json_encode(['status' => $message, 'timestamp' => time(), 'data' => $data]);
    }

    private function validateRequestBody($data) {
        return isset($data['type']) && isset($data['name']) && isset($data['surname']) && isset($data['email']) && isset($data['password']) && isset($data['DOB']);
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

        
        if ($this->userExists($data['email'])) {
            $this->sendResponse(409, "Conflict - User already exists");
            return;
        }

        
        
        $success = $this->insertUser($data);

        
        if ($success) {
            
            $this->sendResponse(200, "Success");
        } else {
            
            $this->sendResponse(500, "Internal Server Error - Failed to insert user into database");
        }
    }


    private function handleRegistrationAdmin($data) {
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

        
        if ($this->userExists($data['email'])) {
            $this->sendResponse(409, "Conflict - User already exists");
            return;
        }

        
        
        $success = $this->insertUserAdmin($data);

        
        if ($success) {
            
            $this->sendResponse(200, "Success");
        } else {
            
            $this->sendResponse(500, "Internal Server Error - Failed to insert user into database");
        }
    }

    private function validateUserData($data) {
        $errors = [];
    
        $emailRegex = '/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/';
        $passwordRegex = '/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/';
        $nameRegex = '/^[a-zA-Z\s]+$/';
        
    
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

    private function userExists($email) {
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }
    
        
        $stmtEmail = $mysqli->prepare("SELECT * FROM user_account WHERE Email_Address = ?");
        $stmtEmail->bind_param("s", $email);
        $stmtEmail->execute();
        $resultEmail = $stmtEmail->get_result();
        $stmtEmail->close();
    
    
        $mysqli->close();
    
        
        return ($resultEmail->num_rows > 0 );
    }

    

    
    private function insertUser($data) {
        
        $hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);
        $admin = 0 ;
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }

        $stmt = $mysqli->prepare("INSERT INTO user_account (First_Name, Last_Name, Email_Address, user_password, Date_of_Birth, Admin_privileges) VALUES (?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("sssssi", $data['name'], $data['surname'], $data['email'], $hashedPassword, $data['DOB'], $admin);
        $success = $stmt->execute();
        $stmt->close();
        $mysqli->close();

        return $success;
    }

    private function insertUserAdmin($data) {
        
        $hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);
        $admin = 1 ;
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

        if ($mysqli->connect_error) {
            die("Connection failed: " . $mysqli->connect_error);
        }

        $stmt = $mysqli->prepare("INSERT INTO user_account (First_Name, Last_Name, Email_Address, user_password, Date_of_Birth, Admin_privileges) VALUES (?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("sssssi", $data['name'], $data['surname'], $data['email'], $hashedPassword, $data['DOB'], $admin);
        $success = $stmt->execute();
        $stmt->close();
        $mysqli->close();

        return $success;
    }

    private function handleLogin($data) {
        if (!isset($data['email']) || !isset($data['password'])) {
            $this->sendResponse(400, "Bad Request - Email and password are required");
            return;
        }
        require_once('config.php');
    
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        $stmt = $mysqli->prepare("SELECT User_ID, user_password FROM user_account WHERE Email_Address = ?");
        $stmt->bind_param("s", $data['email']);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows !== 1) {
            $this->sendResponse(401, "Unauthorized access - Incorrect email or password");
            return;
        }
    
        $row = $result->fetch_assoc();
        if (!password_verify($data['password'], $row['user_password']) && $data['password'] !== $row['user_password']) {
            $this->sendResponse(401, "Unauthorized access - Incorrect email / password");
            return;

        } 
    

        $user_id = $row['User_ID'];
        setcookie("user_id", $user_id, time() + (86400 * 30), "/");
    
        $stmt->close();
        $mysqli->close();
    
        $this->sendResponse(200, "Success" , $user_id );
    }


    public function getDetails($data) {
        if (!isset($data['title_ID']) || !isset($data['Profile_ID'])) {
            $this->sendResponse(400, "Bad Request - Title ID and Profile ID are required");
            return;
        }
    
        require_once('config.php');
    
        // Initialize the title ID and profile ID
        $titleId = $data['title_ID'];
        $profileId = $data['Profile_ID'];
        $currentDate = date('Y-m-d H:i:s');
    
        // Create a new mysqli instance
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
    
        if ($mysqli->connect_error) {
            $this->sendResponse(500, "Database connection failed: " . $mysqli->connect_error);
            return;
        }
    
        // Prepare the SQL statement to get title details
        $stmt = $mysqli->prepare("SELECT Title_ID, Title_Name, IFNULL(Content_Rating_ID, 0) AS Content_Rating_ID, Review_Rating, Release_Date, Plot_Summary, Image, IFNULL(Language_ID, 1) AS Language_ID FROM title WHERE Title_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
    
        // Bind the titleId parameter
        $stmt->bind_param("i", $titleId);
        $stmt->execute();
        $result = $stmt->get_result();
    
        // Fetch the movie details
        $movieDetails = $result->fetch_assoc();
        if (!$movieDetails) {
            $this->sendResponse(404, "Title not found");
            return;
        }
    
        // Get the language name
        $stmt = $mysqli->prepare("SELECT Language_Name FROM available_language WHERE Language_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
    
        // Bind the languageId parameter
        $stmt->bind_param("i", $movieDetails['Language_ID']);
        $stmt->execute();
        $result = $stmt->get_result();
    
        // Fetch the language name
        $language = $result->fetch_assoc();
        if ($language) {
            // Replace the Language_ID with the Language_Name in the movie details
            $movieDetails['Language_ID'] = $language['Language_Name'];
        } else {
            $movieDetails['Language_ID'] = "Unknown"; // or handle it as you see fit
        }
    
        // Get the content rating
        $stmt = $mysqli->prepare("SELECT Rating FROM content_rating WHERE Content_Rating_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
    
        // Bind the contentRatingId parameter
        $stmt->bind_param("i", $movieDetails['Content_Rating_ID']);
        $stmt->execute();
        $result = $stmt->get_result();
    
        // Fetch the content rating
        $contentRating = $result->fetch_assoc();
        if ($contentRating) {
            // Replace the Content_Rating_ID with the Rating in the movie details
            $movieDetails['Content_Rating_ID'] = $contentRating['Rating'];
        } else {
            $movieDetails['Content_Rating_ID'] = "Not Rated"; // or handle it as you see fit
        }
    
        // Get genres
        $stmt = $mysqli->prepare("SELECT g.Genre_Name FROM genre g JOIN title_genre tg ON g.Genre_ID = tg.Genre_ID WHERE tg.Title_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
        $stmt->bind_param("i", $titleId);
        $stmt->execute();
        $result = $stmt->get_result();
        $genres = [];
        while ($genre = $result->fetch_assoc()) {
            $genres[] = $genre['Genre_Name'];
        }
        $movieDetails['Genres'] = $genres;
    
        // Get cast
        $stmt = $mysqli->prepare("SELECT a.Name, si.Roles FROM cast_ a JOIN stars_in si ON a.Actor_ID = si.Actor_ID WHERE si.Title_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
        $stmt->bind_param("i", $titleId);
        $stmt->execute();
        $result = $stmt->get_result();
        $cast = [];
        while ($actor = $result->fetch_assoc()) {
            $cast[] = ['Name' => $actor['Name'], 'Roles' => $actor['Roles']];
        }
        $movieDetails['Cast'] = $cast;
    
        // Get director
        $stmt = $mysqli->prepare("SELECT d.Name FROM director d JOIN directored_by db ON d.Director_ID = db.Director_ID WHERE db.Title_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
        $stmt->bind_param("i", $titleId);
        $stmt->execute();
        $result = $stmt->get_result();
        $directors = [];
        while ($director = $result->fetch_assoc()) {
            $directors[] = $director['Name'];
        }
        $movieDetails['Directors'] = $directors;
    
        // Get production companies
        $stmt = $mysqli->prepare("SELECT pc.Company_Name FROM production_company pc JOIN produced p ON pc.Company_ID = p.Company_ID WHERE p.Title_ID = ?");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
        $stmt->bind_param("i", $titleId);
        $stmt->execute();
        $result = $stmt->get_result();
        $productionCompanies = [];
        while ($company = $result->fetch_assoc()) {
            $productionCompanies[] = $company['Company_Name'];
        }
        $movieDetails['Production_Companies'] = $productionCompanies;
    
        // Insert the Title_ID, Profile_ID, and current date into the watched table
        $stmt = $mysqli->prepare("INSERT INTO watched (Title_ID, Profile_ID, Watch_Date) VALUES (?, ?, ?)");
        if ($stmt === false) {
            $this->sendResponse(500, "Database query failed: " . htmlspecialchars($mysqli->error));
            return;
        }
    
        // Bind the parameters
        $stmt->bind_param("iis", $titleId, $profileId, $currentDate);
        $stmt->execute();
    
        // Close the statement and the database connection
        $stmt->close();
        $mysqli->close();
    
        $this->sendResponse(200, "Success", $movieDetails);
    }
    
    


    private function handleGetMovies($data) {
        if (empty($data)) {
            $this->sendResponse(400, "Post parameters are missing");
            return;
        }
    
        if (isset($data['limit']) && !$this->validateLimit($data['limit'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'limit' parameter");
            return;
        }
    
        if (isset($data['sort']) && !$this->validateSort($data['sort'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'sort' parameter");
            return;
        }
    
        if (isset($data['order']) && !$this->validateOrder($data['order'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'order' parameter");
            return;
        }
    
        if (isset($data['fuzzy']) && !$this->validateFuzzy($data['fuzzy'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'fuzzy' parameter");
            return;
        }
    
        if (isset($data['search']) && !$this->validateSearch($data['search'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'search' parameter");
            return;
        }
    
        if (!isset($data['return'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }
    
        if (!isset($data['isChild'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }
    
        $sql = "
        SELECT 
            m.*, t.*, GROUP_CONCAT(DISTINCT g.Genre_Name) AS Genres, l.* , Rating , Child_Safe
        FROM 
            title t 
            INNER JOIN movie m ON t.Title_ID = m.Title_ID
            INNER JOIN title_genre tg ON t.Title_ID = tg.Title_ID
            INNER JOIN genre g ON tg.Genre_ID = g.Genre_ID
            INNER JOIN available_language l ON t.Language_ID = l.Language_ID
            INNER JOIN content_rating c ON t.Content_Rating_ID = c.Content_Rating_ID
        ";
    
        $searchConditions = [];
    
        if (isset($data['search']) && !empty($data['search'])) {
            $sql .= " WHERE ";
            foreach ($data['search'] as $column => $value) {
                if ($data['fuzzy'] == true) {
                    $searchConditions[] = "$column LIKE '%$value%'";
                } else {
                    $searchConditions[] = "$column = '$value'";
                }
            }
            $sql .= implode(" AND ", $searchConditions);
        }
    
        if (isset($data['isChild']) && $data['isChild']) {
            $sql .= " AND Child_Safe = true ";
        }
    
        $sql .= " GROUP BY t.Title_ID ";
    
        if (isset($data['sort'])) {
            $sort = is_array($data['sort']) ? implode(', ', $data['sort']) : $data['sort'];
            $order = isset($data['order']) ? $data['order'] : '';
            $sql .= " ORDER BY $sort $order";
        }
    
        if (isset($data['limit'])) {
            $sql .= " LIMIT ?";
        }
    
        require_once('config.php');
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
        $Movies = $result->fetch_all(MYSQLI_ASSOC);
        $stmt->close();
        $mysqli->close();
    
        $MoviesWithImages = [];
        foreach ($Movies as $Movie) {
            $imageUrls = $this->fetchImage($Movie['Title_ID']);
            $Movie['image'] = $imageUrls;
            $MoviesWithImages[] = $Movie;
        }
    
        $this->sendResponse(200, "Success", $MoviesWithImages);
    }

    private function handleGetSeries($data) {
        if (empty($data)) {
            $this->sendResponse(400, "Post parameters are missing");
            return;
        }
    
        if (isset($data['limit']) && !$this->validateLimit($data['limit'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'limit' parameter");
            return;
        }
    
        if (isset($data['sort']) && !$this->validateSort($data['sort'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'sort' parameter");
            return;
        }
    
        if (isset($data['order']) && !$this->validateOrder($data['order'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'order' parameter");
            return;
        }
    
        if (isset($data['fuzzy']) && !$this->validateFuzzy($data['fuzzy'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'fuzzy' parameter");
            return;
        }
    
        if (isset($data['search']) && !$this->validateSearch($data['search'])) {
            $this->sendResponse(400, "Bad Request - Invalid 'search' parameter");
            return;
        }
    
        if (!isset($data['return'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }
    
        if (!isset($data['isChild'])) {
            $this->sendResponse(400, "Bad Request - Missing required parameters");
            return;
        }
    
        $sql = "
        SELECT 
            s.*, t.*, GROUP_CONCAT(DISTINCT g.Genre_Name) AS Genres, l.* , Rating , Child_Safe
        FROM 
            title t 
            INNER JOIN series s ON t.Title_ID = s.Title_ID 
            INNER JOIN title_genre tg ON t.Title_ID = tg.Title_ID
            INNER JOIN genre g ON tg.Genre_ID = g.Genre_ID
            INNER JOIN available_language l ON t.Language_ID = l.Language_ID
            INNER JOIN content_rating c ON t.Content_Rating_ID = c.Content_Rating_ID
        ";
    
        $searchConditions = [];
    
        if (isset($data['search']) && !empty($data['search'])) {
            $sql .= " WHERE ";
            foreach ($data['search'] as $column => $value) {
                if ($data['fuzzy'] == true) {
                    $searchConditions[] = "$column LIKE '%$value%'";
                } else {
                    $searchConditions[] = "$column = '$value'";
                }
            }
            $sql .= implode(" AND ", $searchConditions);
        }
    
        if (isset($data['isChild']) && $data['isChild']) {
            $sql .= " AND Child_Safe = true ";
        }
    
        $sql .= " GROUP BY t.Title_ID ";
    
        if (isset($data['sort'])) {
            $sort = is_array($data['sort']) ? implode(', ', $data['sort']) : $data['sort'];
            $order = isset($data['order']) ? $data['order'] : '';
            $sql .= " ORDER BY $sort $order";
        }
    
        if (isset($data['limit'])) {
            $sql .= " LIMIT ?";
        }
    
        require_once('config.php');
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
        $Series = $result->fetch_all(MYSQLI_ASSOC);
        $stmt->close();
        $mysqli->close();
    
        $SeriesWithImages = [];
        foreach ($Series as $Serie) {
            $imageUrls = $this->fetchImage($Serie['Title_ID']);
            $Serie['image'] = $imageUrls;
            $SeriesWithImages[] = $Serie;
        }
    
        $this->sendResponse(200, "Success", $SeriesWithImages);
    }
    
    
    private function fetchImage($titleid) {
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        $stmt = $mysqli->prepare("SELECT Image FROM title WHERE Title_ID = ?");
        $stmt->bind_param("i", $titleid);
        $stmt->execute();
        $result = $stmt->get_result();
    
        if ($result->num_rows !== 1) {
            $this->sendResponse(401, "Unauthorized access - Incorrect email or password");
            return;
        }
    
        $row = $result->fetch_assoc();
        
        $imageUrl = $row['Image'];
    
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


    private function validateLimit($limit) {
        
        return !isset($limit) || is_numeric($limit) && $limit > 0 && $limit < 500;
    }
    
    private function validateSort($sort) {
        
        $allowedSortOptions = ['Title_ID', 'Title_Name', 'Content_Rating_ID', 'Review_Rating', 'Release_Date', 'Plot_Summary', 'Language_ID'];
    
        return !isset($limit) || in_array($sort, $allowedSortOptions);
    }
    
    private function validateOrder($order) {
        
        return !isset($limit) || in_array($order, ['ASC', 'DESC']);
    }
    
    private function validateFuzzy($fuzzy) {
        return !isset($limit) || is_bool($fuzzy);
    }
    
    private function validateSearch($search) {

        
        if ($search === null) {
            return true; 
        }
    
        
        $allowedColumns = ['Title_ID', 'Title_Name', 'Content_Rating_ID', 'Review_Rating', 'Release_Date', 'Plot_Summary', 'Language_ID', 'Genre_ID', 'Genre_Name','Language_Name','Language_ID'];
    
        
        foreach ($search as $column => $value) {
            if (!in_array($column, $allowedColumns)) {
                return false; 
            }
        }
    
    
        return true; 
    }

    /*********************************************************************************************
     #get Series or Movies Recommendations
    **********************************************************************************************/ 
    
    private function handleGetRecommendations($data) {
        // Check if required parameters are provided
        if (empty($data)) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Post parameters are missing"
            ]);
            return;
        }
    
        // Validate the profileID parameter
        if (!isset($data["profileID"]) || !is_numeric($data["profileID"])) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Bad Request - Invalid or missing 'profileID' parameter"
            ]);
            return;
        }
    
        // Validate the limit parameter
        if (!$this->validateLimit($data['limit'])) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Bad Request - Invalid 'limit' parameter"
            ]);
            return;
        }
    
        $profileID = $data["profileID"];
        $limit = isset($data['limit']) ? $data['limit'] : 35;
    
        // Combined SQL query for movies and series with UNION
        $sql = "
        SELECT t.Title_ID, t.Title_Name, t.Release_Date, t.Language_ID
            FROM (
                SELECT t.Title_ID, t.Title_Name, t.Release_Date, t.Language_ID
                FROM title t
                JOIN title_genre tg ON t.Title_ID = tg.Title_ID
                JOIN available_language al ON t.Language_ID = al.Language_ID
                LEFT JOIN (
                    SELECT w.Title_ID
                    FROM watched w
                    JOIN title_genre tg ON w.Title_ID = tg.Title_ID
                    WHERE w.Profile_ID = ?
                ) AS watched_titles ON t.Title_ID = watched_titles.Title_ID
                WHERE t.Language_ID = (
                    SELECT Language_ID
                    FROM title
                    JOIN watched ON watched.Title_ID = title.Title_ID
                    WHERE watched.Profile_ID = ?
                    LIMIT 1
                )
                AND tg.Genre_ID IN (
                    SELECT Genre_ID
                    FROM title_genre
                    WHERE Title_ID IN (
                        SELECT Title_ID
                        FROM watched
                        WHERE Profile_ID = ?
                    )
                )
                AND watched_titles.Title_ID IS NULL
                AND t.Title_ID IN (
                    SELECT Title_ID
                    FROM movie
                    UNION ALL
                    SELECT Title_ID
                    FROM series
                )
            ) AS t
            GROUP BY t.Title_ID, t.Title_Name, t.Release_Date, t.Language_ID
            LIMIT ?;

        ";
    
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        if ($mysqli->connect_error) {
            echo json_encode([
                "status" => 500,
                "timestamp" => time(),
                "message" => "Internal Server Error - Database connection failed"
            ]);
            return;
        }
    
        // Prepare and execute the combined query
        $stmt = $mysqli->prepare($sql);
        if (!$stmt) {
            echo json_encode([
                "status" => 500,
                "timestamp" => time(),
                "message" => "Internal Server Error - Failed to prepare SQL statement"
            ]);
            return;
        }
        $stmt->bind_param("iiii", $profileID, $profileID, $profileID, $limit);
        $stmt->execute();
        $result = $stmt->get_result();
        $Movies = $result->fetch_all(MYSQLI_ASSOC);
        $stmt->close();
        $mysqli->close();
    
        $MoviesWithImages = [];
        foreach ($Movies as $Movie) {
            $imageUrls = $this->fetchImage($Movie['Title_ID']);
            $Movie['Image'] = $imageUrls;
            $MoviesWithImages[] = $Movie;
        }
    
        $this->sendResponse(200, "Success", $MoviesWithImages);
    
    

    }
    

    /*********************************************************************************************
     #get Profiles
    **********************************************************************************************/ 
    
    private function handleGetProfiles($data){
        // Check if required parameters are provided
        if (empty($data)) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Post parameters are missing"
            ]);
            return;
        }

        //validate the profileID parameter
        if (!isset($data["userID"]) || !is_numeric($data["userID"])) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Bad Request - Invalid or missing 'userID' parameter"
            ]);
            return;
        }

        //the return parameter tells us that this returns movie recommendations
        if (isset($data["userID"])) {
            $sql = "
                SELECT *
                FROM profile_account WHERE
                User_ID = ?;
            ";

            require_once('config.php');
            $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
            $stmt = $mysqli->prepare($sql);     //prepare statement
            if (!$stmt) {
                echo json_encode([
                    "status" => 500,
                    "timestamp" => time(),
                    "message" => "Internal Server Error - Failed to prepare SQL statement"
                ]);
                return;
            }

            // Bind parameters and execute the query
            $userID = $data["userID"];
            $stmt->bind_param("i", $userID);
            $stmt->execute();

            // Get the result
            $result = $stmt->get_result();
            $profiles= $result->fetch_all(MYSQLI_ASSOC);

            // Close statement and database connection
            $stmt->close();
            $mysqli->close();

            // Send the response
            echo json_encode([
                "status" => 200,
                "timestamp" => time(),
                "message" => "Success",
                "data" => $profiles
            ]);
            return;
        }
    }

    /*********************************************************************************************
     #Update profile
    **********************************************************************************************/ 
    private function handleUpdateProfile($data) {
        // Check if required parameters are provided
        if (empty($data)) {
            return $this->sendErrorResponse(400, "Post parameters are missing");
        }
    
        // Validate the profileID parameter
        if (!isset($data["profileID"])) {
            return $this->sendErrorResponse(400, "Bad Request - Invalid or missing 'profileID' parameter");
        }
    
        // Validate the profileName parameter
        if (!isset($data["profileName"])) {
            return $this->sendErrorResponse(400, "Bad Request - Missing 'profileName' parameter");
        }
    
        // Validate the childProfile parameter
        if (!isset($data["childProfile"])) {
            return $this->sendErrorResponse(400, "Bad Request - Missing 'childProfile' parameter");
        }
    
        // Validate the childProfile parameter value
        if ($data["childProfile"] != "0" && $data["childProfile"] != "1") {
            return $this->sendErrorResponse(400, "Bad Request - Invalid 'childProfile' parameter. Only '0' or '1' is allowed");
        }
    
        // Validate the languageID parameter
        if (!isset($data["language"])) {
            return $this->sendErrorResponse(400, "Bad Request - Missing or invalid 'languageID' parameter");
        }
    
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        
        // Check database connection
        if ($mysqli->connect_error) {
            return $this->sendErrorResponse(500, "Internal Server Error - Failed to connect to database");
        }
    
        // Retrieve Language_ID
        $languageName = $data["language"];
        $stmt = $mysqli->prepare("SELECT Language_ID FROM available_language WHERE Language_Name = ?");
        $stmt->bind_param("s", $languageName);
        $stmt->execute();
        $result = $stmt->get_result();
        
        // Check if the SELECT query was successful and returned a row
        if ($result->num_rows === 0) {
            $stmt->close();
            $mysqli->close();
            return $this->sendErrorResponse(400, "Bad Request - Language not found");
        }
    
        // Fetch Language_ID
        $row = $result->fetch_assoc();
        $languageID = $row["Language_ID"];
    
        // Prepare SQL statement
        $sql = "UPDATE profile_account SET Profile_Name = ?, Child_Profile = ?, Language_ID = ? WHERE Profile_ID = ?";
        $stmt = $mysqli->prepare($sql);
        if (!$stmt) {
            $mysqli->close();
            return $this->sendErrorResponse(500, "Internal Server Error - Failed to prepare SQL statement");
        }
    
        // Bind parameters and execute the query
        $profileID = $data["profileID"];
        $profileName = $data["profileName"];
        $childProfile = $data["childProfile"];
        $stmt->bind_param("sisi", $profileName, $childProfile, $languageID, $profileID);
        $stmt->execute();

        // Close statement and database connection
        $stmt->close();
        $mysqli->close();
    
        // Send success response
        return $this->sendSuccessResponse(200, "Successfully updated profile");
    }
    /*******************************
     * Add Profile 
     * ******************************/
    private function handleAddProfile($data) {
        // Check if required parameters are provided
        if (empty($data)) {
            return $this->sendErrorResponse(400, "Post parameters are missing");
        }
    
        // Validate the profileName parameter
        if (!isset($data["profileName"])) {
            return $this->sendErrorResponse(400, "Bad Request - Missing 'profileName' parameter");
        }
    
        // Validate the userID parameter
        if (!isset($data["userID"]) || !is_numeric($data["userID"])) {
            return $this->sendErrorResponse(400, "Bad Request - Invalid or missing 'userID' parameter");
        }
    
        // Validate the childProfile parameter
        if (!isset($data["childProfile"])) {
            return $this->sendErrorResponse(400, "Bad Request - Missing 'childProfile' parameter");
        }
    
        // Validate the languageID parameter
        if (!isset($data["languageID"])) {
            return $this->sendErrorResponse(400, "Bad Request - Missing or invalid 'languageID' parameter");
        }
    
        // Validate the childProfile parameter value
        if ($data["childProfile"] != "0" && $data["childProfile"] != "1") {
            return $this->sendErrorResponse(400, "Bad Request - Invalid 'childProfile' parameter. Only '0' or '1' is allowed");
        }
    
        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        
        // Check database connection
        if ($mysqli->connect_error) {
            return $this->sendErrorResponse(500, "Internal Server Error - Failed to connect to database");
        }
    
        // Retrieve Language_ID
        $languageName = $data["languageID"];
        $stmt = $mysqli->prepare("SELECT Language_ID FROM available_language WHERE Language_Name = ?");
        $stmt->bind_param("s", $languageName);
        $stmt->execute();
        $result = $stmt->get_result();
        
        // Check if the SELECT query was successful and returned a row
        if ($result->num_rows === 0) {
            $stmt->close();
            $mysqli->close();
            return $this->sendErrorResponse(400, "Bad Request - Language not found");
        }
    
        // Fetch Language_ID
        $row = $result->fetch_assoc();
        $languageID = $row["Language_ID"];
        
        // Prepare INSERT query
        $sql = "INSERT INTO profile_account (Profile_Name, User_ID, Child_Profile, Language_ID) VALUES (?, ?, ?, ?)";
        $stmt = $mysqli->prepare($sql);
        
        // Check if the INSERT query preparation was successful
        if (!$stmt) {
            $stmt->close();
            $mysqli->close();
            return $this->sendErrorResponse(500, "Internal Server Error - Failed to prepare SQL statement");
        }
    
        // Bind parameters and execute the query
        $stmt->bind_param("siii", $data["profileName"], $data["userID"], $data["childProfile"], $languageID);
        $stmt->execute();
        
        // Check if the INSERT query execution was successful
        if ($stmt->affected_rows === 0) {
            $stmt->close();
            $mysqli->close();
            return $this->sendErrorResponse(500, "Internal Server Error - Failed to insert profile");
        }
    
        // Close statement and database connection
        $stmt->close();
        $mysqli->close();
    
        // Send success response
        return $this->sendSuccessResponse(200, "Successfully added profile");
    }
    
    private function sendErrorResponse($status, $message) {
        echo json_encode([
            "status" => $status,
            "timestamp" => time(),
            "message" => $message
        ]);
        return;
    }
    
    private function sendSuccessResponse($status, $message) {
        echo json_encode([
            "status" => $status,
            "timestamp" => time(),
            "message" => $message
        ]);
        return;
    }
    /*******************************
     * Add Profile 
     * ******************************/
    private function handleRemoveProfile($data){
        // Check if required parameters are provided
        if (empty($data)) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Post parameters are missing"
            ]);
            return;
        }

        //validate the profileID parameter
        if (!isset($data["profileID"]) || !is_numeric($data["profileID"])) {
            echo json_encode([
                "status" => 400,
                "timestamp" => time(),
                "message" => "Bad Request - Invalid or missing 'profileID' parameter"
            ]);
            return;
        }




        require_once('config.php');
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        if ($mysqli->connect_error) {
            echo json_encode([
                "status" => 500,
                "timestamp" => time(),
                "message" => "Internal Server Error - Failed to connect to database"
            ]);
            return;
        }

        // Prepare SQL statement
        $sql = " DELETE FROM profile_account WHERE Profile_ID = ?;";

        $stmt = $mysqli->prepare($sql);
        if (!$stmt) {
            echo json_encode([
                "status" => 500,
                "timestamp" => time(),
                "message" => "Internal Server Error - Failed to prepare SQL statement"
            ]);
            $mysqli->close();
            return;
        }

        // Bind parameters and execute the query
        $profileID = $data["profileID"];
        $stmt->bind_param("i", $profileID);

        $stmt->execute();

        // Close statement and database connection
        $stmt->close();
        $mysqli->close();

        // Send the response
        echo json_encode([
            "status" => 200,
            "timestamp" => time(),
            "message" => "Successfully removed profile"
        ]);
    }

    function getReviews($data) {
        error_log("getReviews called with data: " . print_r($data, true));
    
        if (!isset($data['title_ID'])) {
            $this->sendResponse(400, "Bad Request - Title ID is required");
            return;
        }
    
        require_once('config.php');
        
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        if ($mysqli->connect_error) {
            error_log("Database connection failed: " . $mysqli->connect_error);
            $this->sendResponse(500, 'Database connection failed: ' . $mysqli->connect_error);
            return;
        }
        
        $stmt = $mysqli->prepare("SELECT Review_Content, Rating FROM review WHERE Title_ID = ?");
        if ($stmt === false) {
            error_log("Prepare statement failed: " . $mysqli->error);
            $this->sendResponse(500, 'Database Error: ' . htmlspecialchars($mysqli->error));
            return;
        }
        
        $stmt->bind_param("i", $data['title_ID']);
        $stmt->execute();
        $result = $stmt->get_result();
        $reviews = $result->fetch_all(MYSQLI_ASSOC);
        
        $stmt->close();
        $mysqli->close();
        
        $this->sendResponse(200, "Success", $reviews);
    }
    
    function submitReview($data) {
        error_log("submitReview called with data: " . print_r($data, true));
    
        if (!isset($data['title_ID']) || !isset($data['profile_ID']) || !isset($data['review_text']) || !isset($data['rating'])) {
            $this->sendResponse(400, "Bad Request - All fields are required");
            return;
        }
    
        require_once('config.php');
        
        $mysqli = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
        if ($mysqli->connect_error) {
            error_log("Database connection failed: " . $mysqli->connect_error);
            $this->sendResponse(500, 'Database connection failed: ' . $mysqli->connect_error);
            return;
        }
        
        $stmt = $mysqli->prepare("INSERT INTO review (Title_ID, Profile_ID, Review_Content, Rating) VALUES (?, ?, ?, ?)");
        if ($stmt === false) {
            error_log("Prepare statement failed: " . $mysqli->error);
            $this->sendResponse(500, 'Database Error: ' . htmlspecialchars($mysqli->error));
            return;
        }
        
        $stmt->bind_param("iisi", $data['title_ID'], $data['profile_ID'], $data['review_text'], $data['rating']);
        $stmt->execute();
        if ($stmt->affected_rows > 0) {
            $this->sendResponse(200, "Review submitted successfully");
        } else {
            $this->sendResponse(500, "Failed to submit review");
        }
        
        $stmt->close();
        $mysqli->close();
    }

}

$api = API::getInstance();
$api->handleRequest();

/*
_____________________
TEST RECOMMENDATIONS
____________________
POST |https://wheatley.cs.up.ac.za/PATH TO THE API

{
    "type": "getRecommendations",
    "profileID": 1,
    "limit": 5,
    "return":"series"
}

*/

?>