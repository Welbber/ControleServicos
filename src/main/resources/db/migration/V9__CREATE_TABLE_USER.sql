CREATE TABLE IF NOT EXISTS users (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS users_index_email_username ON users (username, email);

CREATE TABLE IF NOT EXISTS roles (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS permissions (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID,
    role_id UUID,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS  role_permissions (
    role_id UUID,
    permission_id UUID,
    CONSTRAINT pk_role_permissions PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);
