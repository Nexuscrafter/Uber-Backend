# 🚗 Uber Backend System

### **Overview**  
This project is a backend system for an Uber-like ride-hailing application built using **Spring Boot** and designed with a **microservices architecture**. It ensures scalability, fault tolerance, and efficient real-time processing to manage critical functionalities like user authentication, ride booking, driver tracking, and notifications.  

---

## 🛠️ Technology Stack  
- **Backend**: Java, Spring Boot, Spring Data JPA  
- **Database**: MySQL  
- **Caching**: Redis  
- **Messaging**: Kafka  
- **Real-Time Communication**: STOMP over WebSockets  
- **Authentication**: JWT  
- **Tools**: Gradle, Eureka Service Discovery, Flyway  

---

## 🚀 Key Features  
### 1. **Microservices Architecture**  
- Divides functionalities into independent services for better scalability and fault isolation.  

#### Services:  
- **Booking Service**: Matches users with drivers and manages ride statuses.  
- **Auth Service**: Handles user registration, login, and authentication.  
- **Driver Location Service**: Tracks driver locations in real-time using Redis.  
- **Review Service**: Manages user reviews and ratings for drivers.  
- **Notification Service**: Sends real-time updates via WebSockets.  

---

### 2. **Secure Authentication with JWT**  
- Uses **Spring Security** for role-based access (driver, passenger).  
- Validates JWT tokens for each request, ensuring stateless authentication.  
- Ensures token expiration to enhance security.  

---

### 3. **Real-Time Driver Location Management**  
- **Redis**:  
  - Stores driver IDs and geospatial data for fast retrieval.  
  - Supports geospatial queries to find nearby drivers.  
- Optimized ride allocation with low-latency data retrieval.  

---

### 4. **Asynchronous Communication Between Services**  
- **Kafka** for interservice messaging:  
  - Booking Service emits ride requests.  
  - Notification Service consumes ride updates.  
- Ensures decoupled and fault-tolerant communication.  

---

### 5. **Real-Time Updates**  
- **STOMP over WebSockets**:  
  - Delivers real-time ride confirmations, driver arrival notifications, and ride completion updates.  
  - Secured WebSocket endpoints using JWT.  

---

### 6. **Database Management**  
- **Spring Data JPA** simplifies MySQL interactions for core entities like `Users`, `Rides`, `Drivers`, and `Reviews`.  
- **Flyway** handles database schema migrations seamlessly during deployment.  

---

### 7. **Service Discovery**  
- Configured **Eureka** for dynamic service registration and discovery.  
- Eliminates hardcoded endpoints, enabling dynamic scaling.  

---

## 💡 Challenges and Solutions  
| **Challenge**                | **Solution**                                                                                     |  
|-------------------------------|-------------------------------------------------------------------------------------------------|  
| Real-time driver location updates | Used Redis for fast geospatial queries and low-latency updates.                                 |  
| Scalability                  | Adopted microservices architecture and Kafka for distributed processing.                        |  
| Data consistency             | Leveraged Kafka for event-driven communication with idempotent message consumption.            |  
| Fault tolerance              | Utilized Kafka's message retention and Redis's in-memory persistence to ensure availability.    |  

---

## 🛠️ Key Design Decisions  
1. **Microservices Architecture**: Independent services improve scalability and fault isolation.  
2. **Event-Driven Communication**: Kafka ensures asynchronous, decoupled communication.  
3. **In-Memory Caching**: Redis optimizes read/write operations for real-time data.  
4. **WebSockets**: Enables instant notifications to improve user experience.  

---

## 🧭 How to Run the Project  

### **Prerequisites**  
- Java 17  
- MySQL  
- Redis  
- Kafka  
- Gradle  

### **Steps**  
1. Clone the repository:  
   ```bash  
   git clone <repository-url>  
   cd uber-backend  


### **Key API Endpoints**
## Auth Service
POST /register - Register a new user.
POST /login - Authenticate and retrieve a JWT.
# Booking Service
POST /request-ride - Request a ride.
Driver Location Service
POST /update-location - Update driver location.
GET /nearby-drivers - Retrieve nearby drivers based on a given location.
Notification Service
WebSocket /notifications - Subscribe to real-time updates.
💡 Advantages of Design
1. Scalability
The use of Redis and Kafka ensures the system can handle a growing user base with high performance.
2. Low Latency
Redis enables quick driver location updates and ride allocations.
3. Fault Tolerance
Kafka’s message retention and Redis persistence ensure high availability even during failures.
📈 Future Enhancements
Implement dynamic surge pricing.
Introduce multi-language support for global users.
Integrate a payment gateway for secure and seamless transactions.
