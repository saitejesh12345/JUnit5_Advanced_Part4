package com.example.Mobile_app_QuickStart.integration;


import com.example.Mobile_app_QuickStart.dao.UserRepository;
import com.example.Mobile_app_QuickStart.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//springboot provides this annoattaion for Integrate Testing.
//Configured web Environment attribute and Value and select RandomPort
//It starts Embedded server in RANDOM PORT.
@AutoConfigureMockMvc //we use this annotation to call REST API's we need to congigure it
public class UserControllerIntegrateTest {

    @Autowired
    private MockMvc mockMvc; //we use MockMvc class to perfrom different MVC Request.

    @Autowired
    private UserRepository userRepository; //Injecting UserRepository
    //to use its's method to perform different Operations on database.

//we need to keep clean setUp for each every Junit test case in this class.
    //to keep clean we need to delete recordes from database.

@Autowired
    private ObjectMapper objectMapper;
//we gona use Object Mapper for serializataion and deSerialization
   //Object Mapper class should be from Jackson Library.


    @BeforeEach
void Setup(){
    userRepository.deleteAll();
}
@DisplayName("Integration test for create User rest API")
@Test
    public void givenUserObject_whenAddSingleUser_thenReturnSavedUser() throws Exception {

        //given - PreCondition or Setup
    //User class Object
   User user = User.builder()

        .firstName("Ramesh")
        .lastName("Thammali")
        .email("ramesh@gmail.com")
        .build();


        //when - action or behaviour we are gng to test
//we have made post RestAPi call ,we have passed the JSON asObject,In body that we pass JSON
        //ie userJSON Object get results of this method into ResultAction object
        String userJson =objectMapper.writeValueAsString(user); //covert Object to json
      //Mock Mvc to call create User Rest API,we need to pass Content Type and JSOn in request Body.
    //once we get the response we will check in then-verify section.
        ResultActions response=mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        //then - verify output .............................
        // we are going to test whether the response of the REST API has a correct JSON
        //Values or not and HTTP Status

        response.andDo(print()).
                andExpect(status()
                        .isCreated()) //verify https status code
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(user.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())));
    }

    @DisplayName("Integration test for getAllUsers() RestAPI")
    @Test
    public void givenListOfUSers_whenGetAllUsers_thenReturnsUsersList() throws Exception {

        //given - PreCondition or Setup
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(User.builder().firstName("Ramesh").lastName("Thammali").email("ramesh@gmail.com").build());
        listOfUsers.add(User.builder().firstName("Rajesh").lastName("stark").email("stark@gmail.com").build());

        userRepository.saveAll(listOfUsers);//this line saves two records in DB

        //when - action or behaviour we are gng to test
        //when section makes rest api call,so this will fetch two records from database.
        ResultActions response = mockMvc.perform(get("/users/getusers"));

        //then - verify output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUsers.size())));
    }



    //Positive Scenario - valid User id

    @DisplayName("integration test for getUserById RestAPI ")
    @Test
    public void  givenUserId_whenGetUserByID_thenReturnUserObject() throws Exception {

        //given - PreCondition or Setup

        User user = User.builder()

                .firstName("Ramesh")
                .lastName("Thammali")
                .email("ramesh@gmail.com")
                .build();
  userRepository.save(user);

        //when - action or behaviour we are gng to test
        ResultActions response = mockMvc.perform(get("/users/getuser/{id}",user.getUserid()));

        //then - verify output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));



    }

    //negative Scenario - Invalid User id

    @DisplayName("Integration test for getUserById RestAPI ")
    @Test
    public void  givenInvalidUserId_whenGetUserByID_thenReturnEmpty() throws Exception {

        //given - PreCondition or Setup
        int userid=2;
        User user= User.builder()

                .firstName("Ramesh")
                .lastName("Thammali")
                .email("ramesh@gmail.com")
                .build();

        userRepository.save(user);
        System.out.println(userRepository);

        ResultActions response = mockMvc.perform(get("/users/getuser/{id}", userid));

        response.andExpect(status().isNotFound()).andDo(print());
        System.out.println(response);

    }

    @DisplayName("Integrate test for updateUser postive Scenario")
    @Test
    public void givenUpdateUser_whenUpdateUser_thenReturnUpdatedUserObject() throws Exception {

        //given - PreCondition or Setup
        //save object first
        User user= User.builder()

                .firstName("Ramesh")
                .lastName("Thammali")
                .email("ramesh@gmail.com")
                .build();
        userRepository.save(user);

        //update previous object
        User updatedUser = User.builder()
                .firstName("Ram")
                .lastName("Yadav")
                .email("Ram@gmail.com")
                .build();

        userRepository.save(updatedUser);

        //when - action or behaviour we are gng to test
        String userJson =objectMapper.writeValueAsString(updatedUser); //covert Object to json
        ResultActions response=mockMvc.perform(put("/users/updateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        //then - verify output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName",is(updatedUser.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(updatedUser.getLastName())))
                .andExpect(jsonPath("$.email",is(updatedUser.getEmail())));

    }


    @DisplayName("Integrate test for updateUserById negative Scenario")
    @Test
    public void givenUpdateUserId_whenUpdateUserNotFound_thenReturnNotFound() throws Exception {

        int userid = 3;

        User user= User.builder()

                .firstName("Ramesh")
                .lastName("Thammali")
                .email("ramesh@gmail.com")
                .build();
        userRepository.save(user);

        //update previous object
        User updatedUser = User.builder()
                .firstName("Ram")
                .lastName("Yadav")
                .email("Ram@gmail.com")
                .build();

        userRepository.save(updatedUser);

        String userJson =objectMapper.writeValueAsString(updatedUser);
        ResultActions response = mockMvc.perform(put("/users/{id}", userid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        //then - verify output
        response.andExpect(status().isNotFound()).andDo(print());

        System.out.println(response);
    }

//        ResultActions response = mockMvc.perform(
//                put("/users/user/999") // Invalid user ID // Replace with actual user ID
//                        .param("email", "test@example.com"));


    @DisplayName("Junit test for delete User RestAPI ")
    @Test
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception {

        //given - PreCondition or Setup

        User user= User.builder()

                .firstName("Ramesh")
                .lastName("Thammali")
                .email("ramesh@gmail.com")
                .build();
        userRepository.save(user);

        //when - action or behaviour we are gng to test
        ResultActions response = mockMvc.perform(delete("/users/deleteUser/{id}",user.getUserid()));

        //then - verify output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
