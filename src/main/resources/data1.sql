-- Insert Books
INSERT INTO books (title, author, publisher, isbn, genre, average_rating, approved)
VALUES
    ('The Alchemist', 'Paulo Coelho', 'HarperCollins', '9780061122415', 'Fiction', 0.0, false),
    ('Sapiens', 'Yuval Noah Harari', 'Harper', '9780062316097', 'History', 0.0, false),
    ('Clean Code', 'Robert C. Martin', 'Prentice Hall', '9780132350884', 'Programming', 0.0, false);

-- Insert Users
INSERT INTO users (name, age, gender, nationality, email, password, role, auth_provider)
VALUES
    ('test', 22, 'Male', 'Bangladeshi', 'test@gmail.com', 'password', 'USER', 'LOCAL'),
    ('test2', 23, 'Female', 'Bangladeshi', 'test2@gmail.com', 'password', 'USER', 'LOCAL');

-- Insert into Read Books for User test (id 1)
INSERT INTO user_read_books (user_id, book_id)
VALUES
    (1, 1); -- test read "The Alchemist"

-- Insert into Currently Reading Books for User test
INSERT INTO user_currently_reading_books (user_id, book_id)
VALUES
    (1, 2); -- test currently reading "Sapiens"

-- Insert into Plan to Read Books for User test
INSERT INTO user_plan_to_read_books (user_id, book_id)
VALUES
    (1, 3); -- test plans to read "Clean Code"

-- Insert into Read Books for User test2 (id 2)
INSERT INTO user_read_books (user_id, book_id)
VALUES
    (2, 2); -- test2 read "Sapiens"

-- Insert into Currently Reading Books for User test2
INSERT INTO user_currently_reading_books (user_id, book_id)
VALUES
    (2, 1); -- test2 currently reading "The Alchemist"

-- Insert into Plan to Read Books for User test2
INSERT INTO user_plan_to_read_books (user_id, book_id)
VALUES
    (2, 3); -- test2 plans to read "Clean Code");

-- No need to insert anything into UserBook yet.
-- Later, when users rate books via API, UserBook entries will be created automatically.
