
-- SELECT use to retrieve data tables in a database
SELECT * FROM sqlcommon.posts;

-- With DISTINCT, return data not duplicate entries.
SELECT DISTINCT author_id
FROM posts;

-- INSERT Key word to create 1 row data, parameter include field of table. Because id setup auto, so we enter fields other
INSERT INTO `posts` (`author_id`, `title`, `description`, `content`, `date`)
VALUES (2, 'New Post Title', 'Description of the new post', 'Content of the new post', '2023-11-27');

-- UPDATE key word to update 1 table, SET to set value and WHERE like as condition
UPDATE `posts`
SET `title` = 'Updated Title'
WHERE `id` = 1;

-- Similar UPDATE , DELETE use to delete row data of table with condition (WHERE)
DELETE FROM `posts`
WHERE `id` = 2;

-- The AND operator is used to combine two or more conditions, need all conditions must be true
-- The OR operator is used to combine two or more conditions, need at least 1 condition true
SELECT *
FROM `posts`
WHERE (author_id = 1 OR author_id = 2) AND id > 5;

-- The ORDER BY keyword is used to sort the result-set in ascending or descending order.
-- The GROUP BY statement groups rows that have the same values into 1 row with result are returned by with aggregate functions
-- aggregate functions COUNT(), MAX(), MIN(), SUM(), AVG()
SELECT id, author_id
FROM `posts`
GROUP BY id
ORDER BY author_id DESC;

-- JOIN is used to combine rows from two or more tables, based on a related column between them ( KEY )
SELECT posts.id, authors.last_name, posts.title
FROM `posts`
INNER JOIN `authors` ON posts.author_id=authors.id;


-- View is a virtual table based on the result-set of an SQL statement.
-- The fields in a view are fields from one or more real tables in the database.
CREATE VIEW ViewName AS
SELECT posts.id, authors.last_name, posts.title
FROM `posts`
INNER JOIN `authors` ON posts.author_id=authors.id;


-- CASE conditions and returns a value when the first condition is met (like an if-then-else statement). 
-- So, once a condition is true, it will stop reading and return the result. 
-- If no conditions are true, it returns the value in the ELSE clause.
-- If there is no ELSE part and no conditions are true, it returns NULL.
SELECT id, title,
CASE
    WHEN id < 12 THEN 'The id is under than 12'
    WHEN id = 12 THEN 'The id is 12'
    ELSE 'Unknow'
END AS QuantityText
FROM `posts`;

-- A MySQL subquery is a query nested within another query such as SELECT, INSERT, UPDATE or DELETE. 
-- Also, a subquery can be nested within another subquery.
SELECT id, title FROM `posts`
WHERE author_id IN 
	(SELECT id FROM `authors` WHERE id = '2');