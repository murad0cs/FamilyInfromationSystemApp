# Family Information System App
## Project Summary
Create an app that stores user data. Data is composed of first name, last name, and address (stree, city, state and zip) - The app should create the following User types (Parent, Child) The child cannot have an address and must belong to a parent. - The app should have API to: Delete user data Create user data Update user data.
<br>
For the database here I have used the java-embedded [H2](https://www.h2database.com/html/main.html) database for simplicity. All the user data are stored in a single table as per the logic.
## Versions
- Java 17 : 17.
- Spring Boot: (v3.2.0)
- H2 Database: H2VERSION() 2.2.224 
## Add your files
First Clone the project. You can use multiple options
- To clone the repository using HTTPS, under "HTTPS".
- To clone the repository using an SSH key, including a certificate issued by your organization's SSH certificate authority, click SSH.
- To clone a repository using GitHub CLI, click GitHub CLI.
  Clone using HTTPS-
```
git clone https://github.com/murad0cs/FamilyInfromationSystemApp.git
```
## Application Setup and Run Documentation
### Prerequisites
1. **Java Development Kit (JDK):** Ensure that you have Java JDK installed on your machine. You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://www.oracle.com/java/technologies/downloads/).
2. **Integrated Development Environment (IDE):** Install an IDE of your choice, such as [Eclipse](https://www.eclipse.org/ide/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/). ( IntelliJ IDEA preferable)
3. **Maven:** Make sure Maven is installed. You can download it from [Maven official website](https://maven.apache.org/).

## Application Configuration
1. **Database Configuration**: Open application.properties file in your project and configure the database connection properties. Example:
   ```
    spring.datasource.url=jdbc:h2:mem:userappdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL;NON_KEYWORDS=USER
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
    spring.jpa.hibernate.ddl-auto= update
    spring.h2.console.enabled=true
    spring.jpa.properties.hibernate.globally_quoted_identifiers=true
    # default path: h2-console
    spring.h2.console.path=/h2-ui
   ```
   Adjust these settings based on your database.
## Running the Application Locally
1. **Using IDE:**
- Open your IDE and import the project.
- Run the `FamilyInformationSystemAppApplication` class.
2. **Using Maven:**
    - Open a terminal.
    - Navigate to your project's root directory.
    - Run the following command:
    ```
      mvn spring-boot:run
    ```
   This will build and run your application.
## Testing the Application
1. **API Endpoints:**
    - Use tools like Postman to test your API endpoints.
Example API endpoints:
    - `POST /api/users`: Create a new user.
    - `GET /api/users/{id}`: Get user details by ID.
    - `PUT /api/users/{id}`: Update user details by ID.
    - `DELETE /api/users/{id}`: Delete user by ID.
2. **H2 Database Console:**
   - Access the H2 database console at http://localhost:8085/h2-console. (Here put the port number as per your preference)
   - Use the JDBC URL, username, and password configured in `application.properties`
