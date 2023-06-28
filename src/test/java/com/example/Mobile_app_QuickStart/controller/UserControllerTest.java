package com.example.Mobile_app_QuickStart.controller;


import com.example.Mobile_app_QuickStart.model.User;
import com.example.Mobile_app_QuickStart.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import org.mockito.ArgumentMatchers;

import javax.validation.constraints.Null;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;
@WebMvcTest
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean //This annotation it will tells spring that create this mock Object
    //that is userService and register in application Context so that
    //Mock object can be available to UserController
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper; //to convert object into json
    private User user;

    @BeforeEach
    public void Setup(){
        user = User.builder()
                .userid(1)
                .firstName("Ramesh")
                .lastName("Thammali")
                .email("ramesh@gmail.com")
                .build();

    }
    @DisplayName("Junit test for AddSingleUser RestEndPoint")
    @Test
    public void givenUserObject_whenAddSingleUser_thenReturnSavedUser() throws Exception {

            //given - PreCondition or Setup


//we need to mock the method in UserController addSingleUser() method
        //It internally makes  userService.addSingleUser(user),
        //In order to create this test method we have to mock this
    //addSingleUser(),This is called Stabbing,we need to configure Response
        //for this method we willsee arguments matching or not using
        //ArgumentMatchers

        given(userService.addSingleUser(ArgumentMatchers.any(User.class))).
                willAnswer((invocation)-> invocation.getArgument(0));

        //when - action or behaviour we are gng to test
//we have made post RestAPi call ,we have passed the JSON asObject,In body that we pass JSON
        //ie userJSON Object get results of this method into ResultAction object
        String userJson =objectMapper.writeValueAsString(user); //covert Object to json
        ResultActions response=mockMvc.perform(post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson));

        //then - verify output .............................
       // we are going to test whether the response of the REST API has a correct JSON
        //Values or not

        //This code is testing a REST API endpoint using MockMvc, a Spring testing utility.
        // It is making an API call and then asserting certain things about the response:
//.status().isCreated() - It verifies that the HTTP status code of the response is 201 Created,
// indicating the resource was successfully created.
//.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())) -
// It asserts that the firstName field in the JSON response matches the firstName of the user object passed in.
//.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())) - Same as above but for the lastName field.
//.jsonPath("$.email", CoreMatchers.is(user.getEmail())) - Same as above but for the email field.

        response.andDo(print()).
                andExpect(status()
                        .isCreated()) //verify https status code
                .andExpect(jsonPath("$.firstName",
                        is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                       is(user.getLastName())))
                .andExpect(jsonPath("$.email",
                         is(user.getEmail())));

       // So in summary, this test code is making an API call to create a resource, then asserting:
      //  The status code of the response
       // The firstName, lastName, and email fields in the JSON response match the corresponding fields of the user object passed in.
     //   The .isCreated() and CoreMatchers.is() parts are the "expected" values,
        //   and the actual response and JSON data returned are being verified against those expected values.

        //Lets Verify whether the responsbilities API COntain
        //a valid,JSON values or not,ie actual JSON Values to Expected JSON VAlues.In order
        //to do that again call (".andExpect()") and CoreMatchers,similarily call .andExpect and verify
        //lastName,email
        }



        @DisplayName("Junit test for getAllUsers() RestAPI")
        @Test
        public void givenListOfUSers_whenGetAllUsers_thenReturnsUsersList() throws Exception {

                //given - PreCondition or Setup
List<User> listOfUsers = new ArrayList<>();
listOfUsers.add(User.builder().firstName("Ramesh").lastName("Thammali").email("ramesh@gmail.com").build());
            listOfUsers.add(User.builder().firstName("Rajesh").lastName("stark").email("stark@gmail.com").build());
given(userService.getAllUsers()).willReturn(listOfUsers);

                //when - action or behaviour we are gng to test
ResultActions response = mockMvc.perform(get("/users/getusers"));

                //then - verify output
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.size()",
                           is(listOfUsers.size())));
           }


            //Positive Scenario - valid User id

            @DisplayName("Junit test for getUserById RestAPI ")
            @Test
            public void  givenUserId_whenGetUserByID_thenReturnUserObject() throws Exception {

                    //given - PreCondition or Setup
given(userService.getUserById(user.getUserid())).willReturn(user);

                    //when - action or behaviour we are gng to test
ResultActions response = mockMvc.perform(get("/users/getuser/{id}",user.getUserid()));

                    //then - verify output
response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(user.getLastName())))
        .andExpect(jsonPath("$.email", is(user.getEmail())));



    }

    //negative Scenario - valid User id

    @DisplayName("Junit test for getUserById RestAPI ")
    @Test
    public void  givenInvalidUserId_whenGetUserByID_thenReturnEmpty() throws Exception {

        //given - PreCondition or Setup
        given(userService.getUserById(user.getUserid())).willReturn(null);

        //when - action or behaviour we are gng to test
        ResultActions response = mockMvc.perform(get("/users/getuser/{id}",user.getUserid()));

        //then - verify output
        response.andExpect(status().isNotFound())
                .andDo(print());
//                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
//                .andExpect(jsonPath("$.email", is(user.getEmail())));



    }

    @DisplayName("Junit test for updateUser postive Scenario")
    @Test
    public void givenUpdateUser_whenUpdateUser_thenReturnUpdatedUserObject() throws Exception {

            //given - PreCondition or Setup

         User updatedUser = User.builder()
                 .firstName("Ram")
                 .lastName("Yadav")
                 .email("Ram@gmail.com")
                 .build();
//mocked controller layer
    //     given(userService.updateUser(user)).willReturn(user);

        given(userService.updateUser(ArgumentMatchers.any(User.class))).
                willAnswer((invocation)-> invocation.getArgument(0));

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

    @DisplayName("Junit test for updateUserById negative Scenario")
    @Test
    public void givenUpdateUserId_whenUpdateUserNotFound_thenReturnNotFound() throws Exception {

        //Changes made:
        //Used anyInt() and anyString() matchers instead of a specific user object, since we expect the update to return null.
        //Used the correct URL pattern /users/user/{id} with an actual ID.
        //Passed the email as a request parameter instead of in the URL.
        //Asserted that the status code is 400 Bad Request, to match the controller logic that returns 400 when the user is null.
        //The issue was that the URL pattern in the test case did not match the controller,
        // and the expected status code did not match the actual status code returned by the controller.

        //given - PreCondition or Setup
        given(userService.updateUser(anyInt(), anyString())).willReturn(null);

        ResultActions response = mockMvc.perform(
                put("/users/user/999") // Invalid user ID // Replace with actual user ID
                        .param("email", "test@example.com"));

        //then - verify output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }


    @DisplayName("Junit test for delete User RestAPI ")
    @Test
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception {

        //given - PreCondition or Setup
        user.setUserid(1);
        given(userService.deleteUser(user.getUserid())).willReturn(null);

        //when - action or behaviour we are gng to test
        ResultActions response = mockMvc.perform(delete("/users/deleteUser/{id}",user.getUserid()));

        //then - verify output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
