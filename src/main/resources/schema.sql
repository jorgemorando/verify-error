CREATE TABLE searches (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE search_posts (
    search_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expires_on DATE NOT NULL,
    PRIMARY KEY (search_id, created_at),
    FOREIGN KEY (search_id) REFERENCES searches(id) ON DELETE CASCADE
);
