## Overview

This project sets up a backend, frontend, and MySQL database using Docker Compose. The application uses Spring Boot for the backend and can dynamically switch configurations based on the active Spring profile.

## Prerequisites

- Docker
- Docker Compose

## Quick Start

To run the application, simply use the following command:

```bash
docker-compose up --build
```

This command will build and start all the necessary services defined in the `docker-compose.yml` file.

### Services and Ports

- **Backend** (Spring Boot): Accessible at [http://localhost:8080](http://localhost:8080)
- **Frontend**: Accessible at [http://localhost:3000](http://localhost:3000)
- **MySQL Database**: Accessible at `localhost:3306`

### Configuration

The backend service uses a profile-specific configuration file `application-docker.properties` to connect to the MySQL database running in the Docker container. The relevant section in your `docker-compose.yml` ensures the correct Spring profile (`docker`) is active during startup.


## Development Prerequisites

- Java SDK 17
- Maven 3.6.0 or later

## Getting Started

### Import the Project into IntelliJ IDEA

1. **Open IntelliJ IDEA:**
   - Open IntelliJ IDEA and select **File** -> **Open**.
   - Navigate to the directory where you cloned the repository and select the project folder.

2. **Import as a Maven Project:**
   - IntelliJ will detect the `pom.xml` file and prompt you to import the project as a Maven project.
   - Follow the prompts to complete the import process.

3. **Build the Project:**
   - Open the terminal within IntelliJ (you can use `Alt+F12`) and run the following command to build the project and download necessary dependencies:
     ```sh
     mvn clean install
     ```

# Running the Chinook Database with Docker Desktop

This project uses a MySQL database with the Chinook schema. Follow the instructions below to set up and run the database using Docker.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Docker Desktop
- IntelliJ IDEA

## Steps to Run the Database

1. **Open Docker Desktop**: Make sure Docker Desktop is running on your machine.

2. **Open the Project in IntelliJ IDEA**: Launch IntelliJ IDEA and open the project directory where the `docker-compose.yml` file is located.

3. **Run Docker Compose**:
   - Locate the `docker-compose.yml` file in the project directory.
   - Right-click on the `docker-compose.yml` file and select **"Run 'docker-compose up'"** from the context menu. Alternatively, you can use the **play button** (green triangle) in the top-right corner to start the service.

   IntelliJ IDEA will use Docker Compose to start the MySQL container.

4. **Verify the Setup**:
   - MySQL will run on the default port `3306`.
   - The container will automatically set up the `Chinook_AutoIncrement` database using the provided SQL initialization script.

### Running the Application

1. **Run the Spring Boot Application:**
   - Locate the main class `DemoApplication.java` (or similar) in the `src/main/java/com/example/demo` directory.
   - Right-click on the main class and select **Run 'Application'**. This starts the Spring Boot application.

### Running the Frontend

This project includes a React frontend application using the `react-admin` library, created with Vite.
Before you begin, ensure you have the following installed on your system:

- Node.js 18 or later

#### Steps to Run the Frontend

1. **Open the Frontend Directory**:
   - Navigate to the `frontend` directory within the project.

2. **Install Dependencies**:
   - Open a terminal in the `frontend` directory and run the following command to install the required dependencies:
     ```sh
     npm install
     ```

3. **Run the Frontend Application**:
   - After the dependencies are installed, start the development server using the following command:
     ```sh
     npm run dev
     ```
   - The application will run on `http://localhost:3000` by default.

4. **Access the Frontend**:
   - Open your web browser and navigate to:
     ```sh
     http://localhost:3000
     ```

By following these steps, you will have the React frontend running with the `react-admin` library.

### Frontend HTTP Client Interaction

The frontend application is built using `react-admin` and communicates with the backend API to handle authentication, manage access tokens, and refresh tokens when necessary. Here’s how the HTTP client is configured and operates within this framework.

#### 1. Authentication Request

When a user logs in, the frontend sends a POST request to the `/api/authenticate` endpoint with the user’s credentials. If the login is successful, the backend responds with an access token and, if applicable, a refresh token.

#### 2. Using the Access Token

The frontend includes the access token in the `Authorization` header of all authenticated requests to the backend. This token is used to verify the user's identity and permissions.

#### 3. Handling Token Expiry

If the access token is about to expire (i.e., within 60 seconds), the frontend refreshes the token using the refresh token. If the refresh token is also expired or missing (for non-manager users), it will redirect the user to log in again.

#### 4. Example Usage in `react-admin`

The custom HTTP client and data provider (`dataProvider`) are used in the `react-admin` framework to handle API requests, ensuring that the access token is attached and refreshed when needed.

### Accessing the Swagger UI

Swagger UI provides a user-friendly interface for testing and interacting with the API.

1. **Start the Application:**
   - Ensure the Spring Boot application is running.

2. **Open Swagger UI:**
   - Open your web browser and navigate to:
     ```sh
     http://localhost:8080/swagger-ui.html
     ```

## Accessing the Database

The database credentials are already configured in the `application.properties` file:

- **User**: `chinook`
- **Password**: `chinook`
- **Database**: `Chinook_AutoIncrement`
- **Root Password**: `chinook`


## Configuring IntelliJ IDEA Data Source

To configure a data source for the Chinook database in IntelliJ IDEA:

1. **Open Database Tool Window**:
    - Open IntelliJ IDEA.
    - Select **View** > **Tool Windows** > **Database** to open the Database tool window.

2. **Add a New Data Source**:
    - Click the **"+"** icon in the Database tool window.
    - Select **Data Source** > **MySQL**.

3. **Configure the Data Source**:
    - **Driver**: Ensure the MySQL driver is selected.
    - **Host**: `localhost`
    - **Port**: `3306`
    - **Database**: `Chinook_AutoIncrement`
    - **User**: `chinook`
    - **Password**: `chinook`
    - Click **Test Connection** to verify the configuration.

4. **Finish Configuration**:
    - Click **OK** to save the data source configuration.
    - The Chinook database should now appear in the Database tool window.

   

## Access the MySQL command line
Access the MySQL command line interface using the following command:

```sh
docker exec -it chinook_mysql_1 mysql -u chinook -pch -D Chinook_AutoIncrement
```

Replace `chinook_mysql_1` with the actual container name if it differs.

## Stopping the Database

To stop and remove the running container, use:

```sh
docker-compose down
```

## Notes

- The database schema is initialized using `Chinook_MySql_AutoIncrementPKs.sql` located in the `ChinookDatabase` folder.
- Ensure any changes to the database schema are reflected in the SQL file to be reinitialized on subsequent runs.

By following these steps, you'll have a fully functioning Chinook database running in a Docker container.