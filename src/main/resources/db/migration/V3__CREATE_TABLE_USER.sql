CREATE TABLE user (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY(user_id)
);
