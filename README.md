# Inventory Management System

A Spring Boot application for managing inventory and stock levels.

## Features

- Manage products, stock levels, and orders.
- RESTful APIs for CRUD operations.
- Integrated with a relational database **MySQL**.

## Technologies Used

- **Spring Boot**: Backend framework.
- **Spring Data JPA**: For database operations.
- **MySQL**: Database for storing data.
- **Maven**: Build tool.

## Getting Started

### Prerequisites

- Java 21 or higher.
- Maven 3.x.
- Git (for version control).

---

### How to Use

1. **Clone the Repository**
   ```bash
   git clone https://github.com/GosuCode/my-warehouse.git
   cd my-warehouse
   ```

2. **Configure Database**
    - Update `application.properties` with your MySQL credentials:
      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

3. **Run the Application**
   - Option 1: Open the project in IntelliJ IDEA (or any Java-supported IDE), locate MyWarehouseApplication.java, right-click, and select Run.
   - Option 2: Use the terminal:
   ```bash
   mvn spring-boot:run
   ```

4. **Register a User**
    - Send a `POST` request to `/api/auth/register` with:
      ```json
      {
        "username": "admin",
        "password": "admin123",
        "email": "admin@example.com",
        "role": ["ADMIN"]
      }
      ```
    - Copy the `token` from the response for authenticated routes.
   
5. **Create Category**
    - `POST /api/categories` (with Authorization header `Bearer <token>`)
      ```json
      {
        "name": "Electronics",
        "description": "Electronic items"
      }
      ```

6. **Create Supplier**
    - `POST /api/suppliers`
      ```json
      {
        "name": "ABC Suppliers",
        "phone": "9800000000",
        "email": "abc@suppliers.com",
        "address": "Kathmandu"
      }
      ```

7. **Create Product**
    - `POST /api/products`
      ```json
      {
        "sku": "ELEC001",
        "name": "Smartphone",
        "description": "Latest model smartphone",
        "price": 25000,
        "quantity": 50,
        "categoryId": "<your-category-id>",
        "supplierId": "<your-supplier-id>"
      }
      ```

Replace `<your-category-id>` and `<your-supplier-id>` with the actual UUIDs returned from the category and supplier creation responses.

Yes, Al — that structure works well, but showing both options in the README might look redundant. It's better to choose one. Here's a cleaner and more professional version:

---

### Need Help?

> If you encounter any issues, feel free to [create an issue](https://github.com/GosuCode/my-warehouse/issues) or [reach out to me](https://www.linkedin.com/in/alembershreesh/).

---