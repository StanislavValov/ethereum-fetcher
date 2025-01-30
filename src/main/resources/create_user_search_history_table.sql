CREATE TABLE user_search_history
(
    id               UUID PRIMARY KEY      DEFAULT uuid_generate_v4() NOT NULL, -- Unique identifier for each search history record
    username         VARCHAR(255) NOT NULL,                                     -- Foreign key referencing the users table
    searched_at      TIMESTAMP    NOT NULL DEFAULT NOW()                       -- Timestamp of when the search was performed
);


CREATE TABLE user_search
(
    user_search_history_id UUID REFERENCES user_search_history (id),
    transaction_hash TEXT NOT NULL,
    PRIMARY KEY (user_search_history_id, transaction_hash)
);
