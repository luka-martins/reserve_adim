-- Drop dependent table first (reservations has foreign keys)
DROP TABLE reservations CASCADE CONSTRAINTS;

-- Then drop independent tables
DROP TABLE tables CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;