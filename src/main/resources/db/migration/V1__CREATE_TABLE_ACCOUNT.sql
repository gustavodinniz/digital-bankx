CREATE TABLE account
(
    id             VARCHAR(36) PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance        DECIMAL(10, 2)     NOT NULL,
    customer_name  VARCHAR(100)       NOT NULL,
    document       VARCHAR(11) UNIQUE NOT NULL,
    account_type   VARCHAR(20)
);
