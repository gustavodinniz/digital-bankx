CREATE TABLE transaction
(
    id                     VARCHAR(36) PRIMARY KEY,
    transaction_type       VARCHAR(20)    NOT NULL,
    amount                 DECIMAL(10, 2) NOT NULL,
    date_time              DATETIME,
    status                 VARCHAR(20)    NOT NULL,
    source_account_id      VARCHAR(36)    NOT NULL,
    destination_account_id VARCHAR(36),
    FOREIGN KEY (source_account_id) REFERENCES account (id),
    FOREIGN KEY (destination_account_id) REFERENCES account (id)
);
