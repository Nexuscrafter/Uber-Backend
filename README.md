🚗 Uber Backend System
Overview
This project is a backend system for an Uber-like ride-hailing application built using Spring Boot and designed with a microservices architecture. It ensures scalability, fault tolerance, and efficient real-time processing to manage critical functionalities like user authentication, ride booking, driver tracking, and notifications.

Technology Stack
Backend: Java, Spring Boot, Spring Data JPA
Database: MySQL
Caching: Redis
Messaging: Kafka
Real-Time Communication: STOMP over WebSockets
Authentication: JWT
Tools: Gradle, Eureka Service Discovery, Flyway
🚀 Key Features
1. Microservices Architecture
Divides functionalities into independent services for better scalability and fault isolation.
Services:
Booking Service: Matches users with drivers and manages ride statuses.
Auth Service: Handles user registration, login, and authentication.
Driver Location Service: Tracks driver locations in real-time using Redis.
Review Service: Manages user reviews and ratings for drivers.
Notification Service: Sends real-time updates via WebSockets.
2. Secure Authentication with JWT
Uses Spring Security for role-based access (driver, passenger).
Validates JWT tokens for each request, ensuring stateless authentication.
Ensures token expiration to enhance security.
3. Real-Time Driver Location Management
Redis:
Stores driver IDs and geospatial data for fast retrieval.
Supports geospatial queries to find nearby drivers.
Optimized ride allocation with low-latency data retrieval.
4. Asynchronous Communication Between Services
Kafka for interservice messaging:
Booking Service emits ride requests.
Notification Service consumes ride updates.
Ensures decoupled and fault-tolerant communication.
5. Real-Time Updates
STOMP over WebSockets:
Delivers real-time ride confirmations, driver arrival notifications, and ride completion updates.
Secured WebSocket endpoints using JWT.
6. Database Management
Spring Data JPA simplifies MySQL interactions for core entities like Users, Rides, Drivers, and Reviews.
Flyway handles database schema migrations seamlessly during deployment.
7. Service Discovery
Configured Eureka for dynamic service registration and discovery.
Eliminates hardcoded endpoints, enabling dynamic scaling.
💡 Challenges and Solutions
Challenge	Solution
Real-time driver location updates	Used Redis for fast geospatial queries and low-latency updates.
Scalability	Adopted microservices architecture and Kafka for distributed processing.
Data consistency	Leveraged Kafka for event-driven communication with idempotent message consumption.
Fault tolerance	Utilized Kafka's message retention and Redis's in-memory persistence to ensure availability.
🛠️ Key Design Decisions
Microservices Architecture: Independent services improve scalability and fault isolation.
Event-Driven Communication: Kafka ensures asynchronous, decoupled communication.
In-Memory Caching: Redis optimizes read/write operations for real-time data.
WebSockets: Enables instant notifications to improve user experience.
🧭 How to Run the Project
Prerequisites
Java 17
MySQL
Redis
Kafka
Gradle
Steps
Clone the repository:
bash
Copy
Edit
git clone <repository-url>  
cd uber-backend  
Start Redis, MySQL, and Kafka servers.
Run database migrations:
bash
Copy
Edit
./gradlew flywayMigrate  
Start Eureka Service Discovery:
bash
Copy
Edit
./gradlew :eureka-service:bootRun  
Start all microservices:
bash
Copy
Edit
./gradlew :<service-name>:bootRun  
Access the application endpoints via Postman or your client app.
🔗 API Endpoints
Here’s a quick overview of the core API endpoints:

Auth Service
POST /register - Register a new user.
POST /login - Authenticate and retrieve a JWT.
Booking Service
POST /request-ride - Request a ride.
GET /ride-status/{rideId} - Check ride status.
Driver Location Service
POST /update-location - Update driver location.
GET /nearby-drivers - Get nearby drivers for a given location.
Review Service
POST /review - Submit a driver review.
GET /reviews/{driverId} - View reviews for a driver.
Notification Service
WebSocket /notifications - Subscribe to real-time updates.
📈 Future Enhancements
Implement dynamic surge pricing.
Add multi-language support.
Integrate a payment gateway for cashless transactions.
Feel free to contribute or provide feedback! 🚀
