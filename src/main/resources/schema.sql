CREATE TABLE api_routes (
  id BIGSERIAL PRIMARY KEY,
  path TEXT NOT NULL,
  method VARCHAR(10) NOT NULL,
  uri TEXT NOT NULL
);
