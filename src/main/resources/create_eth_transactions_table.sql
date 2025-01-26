CREATE TABLE eth_transactions
(
    id                 UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    block_hash         VARCHAR(255),
    block_number       BIGINT,
    contract_address   VARCHAR(255),
    from_address       VARCHAR(255),
    input              VARCHAR(255),
    logs_count         INTEGER,
    to_address         VARCHAR(255),
    transaction_hash   VARCHAR(255),
    transaction_status INTEGER,
    value              VARCHAR(255)
);
