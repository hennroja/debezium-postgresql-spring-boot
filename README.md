# Debezium with PostgreSQL and Spring Boot Consumer

This project demonstrates how to use **Debezium** with a **PostgreSQL** database and a **Spring Boot** consumer. It provides a step-by-step guide to set up the environment and run the application.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [License](#license)

## Prerequisites

Before you begin, ensure you have the following installed:

- Docker
- Docker Compose
- PostgreSQL
- Java (JDK 18 or higher)
- Maven

## Configuration

Refer to the official Debezium documentation for detailed configuration options for the PostgreSQL connector: [Debezium PostgreSQL Connector Properties](https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-connector-properties).

## Setup Instructions

1. **Clone the Repository**

   Clone this repository to your local machine:
   ```bash
   git clone git@github.com:hennroja/debezium-postgresql-spring-boot.git
   cd debezium-postgresql-spring-boot
   ```

2. **Run Docker Compose**

   Start all required containers using Docker Compose:
   ```bash
   docker-compose up
   ```

3. **Start the Spring Boot Application**

   Navigate to the Spring Boot application directory and run:
   ```bash
   mvn spring-boot:run
   ```

4. **Insert Data into PostgreSQL**

   Open a terminal and connect to your PostgreSQL database using `psql`:
   ```bash
   psql postgres://postgres@localhost:5432/postgres
   ```
   Enter the password when prompted (default is `password`).

   Execute the following SQL command to insert a record:
   ```sql
   INSERT INTO student_info (name, subject_area) VALUES ('Franz Josph Waltermann', 'Informatik');
   ```

5. **Access the Consumer**

   Open your web browser and navigate to:
   ```
   http://localhost:8888/
   ```
   Here, you can watch the topic: `my_dbz_topic.public.student_info`.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Citations:
[1] https://debezium.io/documentation/reference/stable/connectors/post