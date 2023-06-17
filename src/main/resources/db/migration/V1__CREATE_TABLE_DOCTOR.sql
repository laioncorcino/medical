CREATE TABLE doctor (
   doctor_id BIGINT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL UNIQUE,
   crm VARCHAR(6) NOT NULL UNIQUE,
   expertise VARCHAR(50) NOT NULL,
   address_line VARCHAR(100) NOT NULL,
   cep VARCHAR(9) NOT NULL,
   number VARCHAR(20),
   complement VARCHAR(100) NOT NULL,
   district VARCHAR(100) NOT NULL,
   city VARCHAR(100) NOT NULL,
   state CHAR(2) NOT NULL,

   PRIMARY KEY(doctor_id)
);
