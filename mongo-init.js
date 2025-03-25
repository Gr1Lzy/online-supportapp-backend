db = db.getSiblingDB("supportAppBD");

db.users.insertOne({
    email: 'admin@example.com',
    password: '$2a$12$M4rPcVjVADFktrrmgArNM.mn6OwUhBZ/4JNiIg22pwdwuD6sgldnO',
    first_name: 'admin',
    last_name: 'admin',
    role: 'ROLE_ADMIN',
    created_at: new Date(),
    updated_at: new Date(),
});

db.users.insertOne({
    email: 'user@example.com',
    password: '$2a$12$/tdsCkZKxO9RHkJtjpxA6urLfCBuXOo/Gh18djljNUKqH3R03Mq6O',
    first_name: 'user',
    last_name: 'user',
    role: 'ROLE_USER',
    created_at: new Date(),
    updated_at: new Date(),
});
