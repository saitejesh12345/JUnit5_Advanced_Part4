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
 
