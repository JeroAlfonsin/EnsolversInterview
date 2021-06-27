Login:The application init in a loggin windows, by default the username is user and password is user123
username: user
password: user123

The whole application was developed in Spring Boot 4.4.11, the backend was made in Java and the frontend in
HTML. The database use my-sql 5.7.31 and the conncetor Connector J to connect the app and de BD.
To start the BD and the complete application, there is a script bash. To run this file you must have
user root created in mysql, and also this user must have all the privileges.
After press enter to ./initScipt.sh the console will ask you to enter the password of root, after that
the script will create the DB, and then it will start runing the server of the application.
To run the app, is necessary to have jdk version 11
