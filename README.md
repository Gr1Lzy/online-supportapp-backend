# Online Support App Backend

## Quick Start

To run all services:

```bash
make up
```

To stop all services:

```bash
make down
```

## Startup Sequence

To properly start the application, follow these steps in order:

1. Start the Docker containers with the infrastructure components
2. Start the Service Registry application first
3. After Service Registry is up and running, start the remaining services

## Components

The application consists of the following components:

### 1. MongoDB (Ticket Management)
- Database: `supportAppTicketDB`
- Credentials: admin/user

### 2. PostgreSQL (User Management)
- Database: `supportAppUserDB`
- Credentials: admin/root

### 3. Keycloak (Authentication)
- Admin credentials: admin/root
- Realm: `support-application-user`
- Server URL: http://localhost:8080

## Keycloak Configuration

After starting the services, you need to set up Keycloak properly:

1. Log in to the Keycloak admin console at http://localhost:8080
2. Navigate to "Clients" → "admin-cli" → "Service Accounts Roles"
3. Assign the "manage-users" role to the admin-cli client

## Environment Variables

The application uses the following environment variables:

```
# MongoDB Ticket
MONGODB_DATABASE=supportAppTicketDB
MONGODB_USERNAME=admin
MONGODB_PASSWORD=user

# Keycloak
KEYCLOAK_USERNAME=admin
KEYCLOAK_PASSWORD=root
REALM_NAME=support-application-user
SERVER_URL=http://localhost:8080

# Client Authentication
CLIENT_ID=authentication-client-id
CLIENT_SECRET=486oEFv2drJAXo6j4XWHXgHpiw0o4Omt

# Admin Authentication
ADMIN_ID=admin-cli
ADMIN_SECRET=J5qKuyPLZoakRJuGPMZ2SUqwW2hiX1es

# PostgreSQL User
POSTGRESQL_DATABASE=supportAppUserDB
POSTGRESQL_USERNAME=admin
POSTGRESQL_PASSWORD=root
```

## API Documentation

(Add API endpoints and documentation here)

## Troubleshooting

If you encounter issues starting the services, try the following:

1. Check if any of the required ports are already in use
2. Ensure Docker is running
3. Try stopping all containers with `make down` and starting again