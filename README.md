* To Customize the JWT Time change it in SecurityConstants file
* Add JPA Data dependence to use Repositories, Entities and Hibernate.
*  Add MySQL Connector dependence
* Create a new schema in your MySQL Workbench and do following changes in application.properties to run on your local machine.
```
spring.datasource.url=jdbc:mysql://localhost:3306/${SCHEMA_NAME}
spring.datasource.username=${USERNAME}
spring.datasource.password=${PASSWORD}
```
* ADMIN DETAILS
```
http://localhost:8080/api/auth/user/login
```
```
{
    "userName":"dpradeepspam@gmail.com",
    "password":"Adminhere@123"
}
```
