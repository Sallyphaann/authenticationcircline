-- CREATE TABLE account (
--                          id         INT          NOT NULL,
--                          userId     INT          NOT NULL,
--                          email      VARCHAR(200) NOT NULL,
--                          password   VARCHAR(100) NOT NULL,
--                          role_name  VARCHAR(50)  NOT NULL,
--                          PRIMARY KEY (id),
--                          UNIQUE (email)
-- );
CREATE TABLE account (
                         id INT NOT NULL IDENTITY(1,1),
                         userId INT NOT NULL,
                         email VARCHAR(200) NOT NULL,
                         password VARCHAR(100) NOT NULL,
                         role_name VARCHAR(50) NOT NULL,
                         PRIMARY KEY (id),
                         CONSTRAINT UC_Email UNIQUE (email)
);



