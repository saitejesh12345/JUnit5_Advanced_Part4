# JUnit5_Advanced_Part4
Here we build an Microservice application from Start to end with Proper UnitTesting and Integration Testing with All layers will be tested.CheckOut ReadmeFile for Proper explanation of Code.This is Complete SpringBoot with Junit5 &amp; Integration Test

# Code Explanation Starts

They are Three Layers 

**1.** **UsersControllers**[@RestController]

This is where we have our RestController Class with methods that handles Http requests,where we write many Api's such as **Update(),Delete(),Getall(),Create().**

**2.** **UsersService**[@Service]

This Service Class contains methods that performs main Business logic,any complex business logic like Sorting and Filtering will be done in Service Layer.
**Service Layer** Communicates with Database called **RepositoryLayer** or **Data Layer**

**3.** **UsersRepository**[@Repository]

This DataLayer has **Data access Objects which as read and write Operations in Database**.

# Unit Testing

**->** Let's Assume i need to do Unit Test In **RestController** **GetUsersMethod().** This means i want to **isolate the Business logic that is inside of this & get use method from all external Dependencies.** This means i want to isolate the GetUsersMethod(),from **Executing Real Business Logic ie Service or Data access layer.**

**->** To do that **i need to mock UserService ,if i mock UserService then UserRepository will also not be Invoked to test My GetUsersMethod() in Isolation.** I will also
not start WebServer,becoz when **i am Unit Testing i am not sending Real Http Requests.** This means Annotation like **@RestCOtroller & @GetMapping will also not be Involved.**

**->** My production Application code will have Annotations,but my unit test will not use Them.Infact in **Unit Testing Code in SpringBoot Application,we will not even
load Spring Application Context.** None of these annotation will **never been used in UnitTesting,Including @Service annotation in ServiceLayer.**


# Integration Testing

**->** If we need to test for **GetUsersMethod()**  method is Properly Integrated with SpringFramework, & **if it works well in SpringWeb Layer,we do not want to isolate or getUsers Method Completely** ,we do want some of Sprint Framework features to be available to us but only through Controller Layer/WebLayer.

**->** For Example we do want to test and Make Sure that **GetUsersMethod()** can be Triggered by **Http get request and that it can be read Http  request parameters by Controller Layer** ,In this case we want annotations like **@RestController or @GetMapping or @Requestmapping** to be enabled and to work. Spring Framework Allows to test Each layer Seperately ,If we want to test COntroller,**Service and DAO will not be needed**,Spring Framework will create Spring application COntext for Only Controller Layer.

**->** Those beans which are related to webLayer will be Created and loaded into Spring Application Context.**Beans that are related to DAO layer and Service layer will not be Created and Loaded into Application COntext**.Becoz of other layer not tested and not loaded or involved Each Single layer will get tested/run fast.

**->** With all Layer Integrated,we will write a test Method that is called **Integration test or Acceptance Test**,And for this test to work,we will need all three layers
Integrated and we will not use any Test Doubles.**We will not create Any Mock Objects and We will not create any Fake Objects**

**->** The WebLayer which we write test ,**Http request will be handled and Bean validation will be Performed.** And the Service layer,a real production version of our code will be executed and in data layer Actual Communication of database will be Performed. **So NO MOCKING of any Layer or Any Objects will be done**.

**->** Even though our Real web Server will not be Started by default,we Can still test Our code Integrated with all layers.But **if needed we can load our SpringBoot application with Embedded Server we can make it run on random SPecific PORT Number.**


# Code Explanation is below before writing testing flow of Code is explained below 

# In IO Package Code has UserEntity and RepositoryLayer

**->** This code defines an Entity class to represent a user in a database table. Some key points:

**->** It is annotated with @Entity, indicating it maps to a database table.

**->** The table name is specified as "users" using the @Table annotation.

**->** The id field is the primary key, annotated with @Id and @GeneratedValue to auto-generate the ID.

The other fields map to columns in the users table:

**1.** userId: A string user ID

**2.** firstName, lastName: The user's name

**3.** email: The user's email address

**4.** encryptedPassword: The user's hashed password

**5.** The serialVersionUID is used for serialization compatibility.

Getters and setters are defined for all fields.
So in summary, this UserEntity class defines a Java object to represent a user record in a database table. It uses JPA annotations to map the fields to columns. This allows persisting UserEntity objects to a database.

 
# In shared Package Code has  UserDto ,Spring Application Context

Check the code you can understand

# In service Package Code has Service class & ,ServiceImpl class


This code defines a service implementation class for managing users. Some highlights:

**1.** It's annotated with @Service to mark it as a Spring service bean.

**2.** It injects a UsersRepository which is used to persist and retrieve users from the database.

**3.** It uses a BCryptPasswordEncoder to encrypt user passwords before saving.

**4.** The createUser() method:

**->** Maps the DTO to an entity

**->** Generates a UUID for the user ID

**->** Encrypts the password

**->** Saves the entity

**->** Maps the entity back to a DTO and returns it

**5.** The getUsers() method retrieves a page of users from the repository and maps them to DTOs.

**6.** The getUser() method retrieves a user by email and maps to a DTO.

**7.** The loadUserByUsername() method loads a user by email and returns a Spring Security UserDetails.

**8.** It uses ModelMapper to map between the UserEntity and UserDto classes.

**9.** It injects the UsersRepository using the @Autowired annotation. This allows the service to call repository methods to persist and retrieve data.

**10.** It injects the BCryptPasswordEncoder using constructor injection. This allows the service to encrypt passwords before saving users.

**11.** It uses ModelMapper to map between the UserEntity and UserDto classes. This allows decoupling the data transfer objects from the entity classes.

**12.** It handles exceptions when users cannot be found, throwing custom UsersServiceException or UsernameNotFoundException.

**13.** It uses Spring Data JPA's Pageable to retrieve users in pages, allowing pagination.

**14.** The loadUserByUsername() method implements the UserDetailsService interface, which is required by Spring Security. This allows loading a user by email for authentication.

**15.** It's annotated with @Service to mark it as a Spring bean and make it eligible for component scanning and auto-wiring.

**16.** It uses constructor injection to inject the UsersRepository and BCryptPasswordEncoder dependencies. This makes the class easier to test.

**17.** It handles pagination by accepting the page and limit parameters and creating a Pageable request. It then retrieves a Page of results from the repository and extracts the content as a List.

**18.** It maps the List of entities to a List of DTOs using ModelMapper's TypeToken, allowing it to return the generic List type.

**19.** It handles exceptions when users cannot be found, throwing custom UsersServiceException or UsernameNotFoundException. This allows callers to catch specific exceptions.

**20.** The loadUserByUsername() method implements Spring Security's UserDetailsService by loading a user by email and returning a UserDetails. This allows integrating the service with Spring Security.

So in summary, this service class provides the main functionality for managing users: creating, retrieving, and loading user details. It works with the UsersRepository and UserEntity classes to persist data to the database, and uses DTOs to expose the data. this service class provides a full implementation for managing users in a Spring Boot + Spring Data JPA + Spring Security application. The key aspects are:

**->** Encrypting passwords

**->** Mapping to/from DTOs

**->** Handling exceptions

**->** Pagination of results

**->** Implementing Spring Security's UserDetailsService

#  In Ui[COntroller] Package Code has various files but i am explaining on Rest Enpoints


This code defines a REST controller for managing users. Some highlights:

**1.** It's annotated with @RestController to mark it as a RESTful web service.

**2.** It injects the UsersService which it uses to delegate create and retrieve users.

**3.** The /users endpoint is mapped using @RequestMapping.

**4.** The createUser() method:

**->** Maps the request DTO to an entity DTO

**->** Calls the service to create the user

**->** Maps the returned entity DTO to a response DTO

**->** Returns the response DTO

**5.** It uses ModelMapper to map between the different DTOs.

**6.** The getUsers() method:

**->**  Calls the service to get users, passing page and limit parameters

**->**  Maps the list of entity DTOs to a list of response DTOs

**->**  Returns the list of response DTOs

**7.** It's annotated with @Valid to validate the request DTO using JSR 303.

**8.** It uses @RequestParam to bind the page and limit query parameters.

So in summary, this controller exposes REST endpoints for:
Creating a new user Retrieving a list of users, with pagination
By delegating to the UsersService, it follows the Controller-Service separation of concerns. The mapping between request/response and entity DTOs decouples
