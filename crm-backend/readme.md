# CRM Backend

A robust Node.js backend for a Customer Relationship Management (CRM) system, providing secure APIs for managing customers, leads, sales activities, reporting, and user authentication. Built with modular architecture for scalability and maintainability.

---

## Features

- User authentication and role-based access control
- Lead and customer management
- Sales activity tracking
- Automated report generation
- Admin and sales representative dashboards
- Notification system
- RESTful API design with error handling

---

## Directory Structure

```
crm-backend/
├── config/           # Configuration files (app settings, DB connection)
├── controllers/      # Request handlers for each route group
├── middlewares/      # Express middlewares (auth, validation, error handling)
├── models/           # Mongoose models/entities
├── routes/           # Express route definitions
├── utils/            # Utility functions (token, reports)
├── .env              # Environment variables
├── .gitignore        # Git ignore rules
├── package.json      # Project metadata and dependencies
├── server.js         # Application entry point
```

### Folder/File Descriptions

- **config/**: Application and database configuration (`config.js`, `db.js`).
- **controllers/**: Business logic for each API group (admin, auth, report, salesRep).
- **middlewares/**: Authentication, error handling, and request validation.
- **models/**: Mongoose schemas for entities (User, Customer, Lead, etc.).
- **routes/**: API endpoints grouped by functionality.
- **utils/**: Helper utilities (JWT token generation, report creation).
- **server.js**: Initializes Express app and connects to MongoDB.

---

## Setup Instructions

### Prerequisites

- Node.js (v14+)
- MongoDB instance

### Installation

```bash
git clone https://github.com/your-org/crm-backend.git
cd crm-backend
npm install
```

### Environment Variables

Create a `.env` file in the root directory:

```
PORT=5000
MONGO_URI=mongodb://localhost:27017/crm
JWT_SECRET=your_jwt_secret
```

### Running the Server

```bash
npm start
```

---

## API Overview

| Route Group   | Path Prefix      | Purpose                                               |
|---------------|------------------|-------------------------------------------------------|
| Admin         | `/api/admin`     | Manage users, view system stats, admin actions        |
| Auth          | `/api/auth`      | User registration, login, authentication              |
| Lead          | `/api/leads`     | CRUD operations for leads                             |
| Report        | `/api/reports`   | Generate and fetch reports                            |
| SalesRep      | `/api/salesreps` | Manage sales reps, assign leads, track performance    |

---

## Authentication & Middleware

- **authMiddleware.js**: Protects routes using JWT-based authentication.
- **validateUser.js**: Validates user input for registration and login.
- **errorMiddleware.js**: Centralized error handling for API responses.

Role-based access ensures only authorized users can access sensitive endpoints.

---

## Models / Entities

- **User**: System users (admin, sales rep) with roles and credentials.
- **Customer**: Customer profiles and contact information.
- **Lead**: Potential customers and their status.
- **Sales**: Sales records and transactions.
- **Activity**: Logs of user and sales activities.
- **Notification**: System notifications for users.

---

## Utilities

- **generateToken.js**: Creates JWT tokens for authenticated sessions.
- **reportGenerator.js**: Generates sales and activity reports (PDF/CSV).

---

## Example API Requests

### Register

```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "Jane Doe",
    "email": "jane@example.com",
    "password": "securePassword123",
    "role": "salesRep"
}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "jane@example.com",
    "password": "securePassword123"
}
```

### Create Lead

```http
POST /api/leads
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
    "customerId": "60f7c2...",
    "source": "Website",
    "status": "New"
}
```

---

## Contribution

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request. Ensure code style and tests are followed.

---


