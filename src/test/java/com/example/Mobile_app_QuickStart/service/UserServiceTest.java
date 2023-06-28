package com.example.Mobile_app_QuickStart.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.Mobile_app_QuickStart.dao.UserRepository;
import com.example.Mobile_app_QuickStart.exception.ResourceNotFoundException;
import com.example.Mobile_app_QuickStart.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

@Mock
    private UserRepository userRepository;

@InjectMocks
    private UserServiceImpl userService;

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

    @DisplayName("Junit test for addSingleUser() Method ")
    @Test
    public void givenUserObject_whenSaveUser_thenReturnUserObject(){
      //It's testing the addSingleUser() method in UserServiceImpl class.
            //given - PreCondition or Setup
//It mocks the repository calls:
    given(userRepository.findByEmail(user.getEmail()))
            .willReturn(Optional.empty());
    given(userRepository.save(user)).willReturn(user);
    //when - action or behaviour we are gng to test
        // It calls the method under test:
    User saveUser = userService.addSingleUser(user);
    //then - verify output
        Assertions.assertThat(saveUser).isNotNull();
}

@DisplayName("Junit test for addSingleUser() Method which throws custom exception  ")
@Test
public void givenExistingEmail_whenSaveUser_thenThrowsException(){

        //given - PreCondition or Setup
given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

   //    given(userRepository.save(user)).willReturn(user); [Not required stubbing in exception Thrown]


        //when - action or behaviour we are gng to test
   org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
        userService.addSingleUser(user);
    });

      //then
    //After throwing Resource not Found exception,
    // Control wont go to save() method for next statement thats y we commneted  70 line
    verify(userRepository,never()).save(any(User.class));
    }


    @DisplayName("Junit test for getAllUsers()  ")
    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnUsersList(){

            //given - PreCondition or Setup
        User user1 = User.builder()
                .userid(2)
                .firstName("Saiteja")
                .lastName("Sharad")
                .email("saiteja@gmail.com")
                .build();
        //stabbing method which call and return two objects user,user1
    given(userRepository.findAll()).willReturn(List.of(user,user1));


            //when - action or behaviour we are gng to test
List<User> userList = userService.getAllUsers();

                //then - verify output
         Assertions.assertThat(userList).isNotNull();
         Assertions.assertThat(userList.size()).isEqualTo(2);
        }


        @DisplayName("Junit test for getAllUsers() for Negative Scenario ")
        @Test
        public void givenEmptyUsersList_whenGetAllUsers_thenReturnEmptyUsersList(){
//Given in list all users data has been there but fortunately data in list has been deleted
            //Employeers wants to fetch all the users data it will return
            //Empty list


            //given - PreCondition or Setup
            User user1 = User.builder()
                    .userid(2)
                    .firstName("Saiteja")
                    .lastName("Sharad")
                    .email("saiteja@gmail.com")
                    .build();

              //stabbing method which call and return two objects user,user1
            given(userRepository.findAll()).willReturn(Collections.emptyList());

            //when - action or behaviour we are gng to test
            List<User> userList = userService.getAllUsers();


            //then - verify output
            Assertions.assertThat(userList).isEmpty();
            Assertions.assertThat(userList.size()).isEqualTo(0);
        }

        @DisplayName("Junit test for getUser(int id) ")
        @Test
        public void givenUserId_whenGetUserByUserId_thenReturnUserDetails(){

                //given - PreCondition or Setup
        given(userRepository.findById(user.getUserid())).willReturn(Optional.of(user));

                //when - action or behaviour we are gng to test
       User getUserThroughId = userService.getUserById(user.getUserid());
         //   System.out.println("getUserThroughId:" + getUserThroughId);

                //then - verify output

           Assertions.assertThat(getUserThroughId).isEqualTo(user);
            }

            @DisplayName("Junit test for updateUser Method() ")
            @Test
            public void givenUserObject_whenUpdateUserObject_thenReturnUpdatedObject(){


                    //given - PreCondition or Setup
                   given(userRepository.save(user)).willReturn(user);
                   user.setFirstName("Aakash");
                   user.setLastName("Bilakanti");
                   user.setEmail("Aakash@4636@gmail.com");

                    //when - action or behaviour we are gng to test
                        User updateObj =userService.updateUser(user);
                System.out.println(updateObj);
                    //then - verify output
                Assertions.assertThat(updateObj).isSameAs(user);
             Assertions.assertThat(updateObj).isNotNull();
            }

            @DisplayName("Junit test for delete method")
            @Test
            public void givenUserId_whenDeleteUser_thenReturnNothing(){

                    //given - PreCondition or Setup
                willDoNothing().given(userRepository).deleteById(user.getUserid());
                    //when - action or behaviour we are gng to test
                   User empty =  userService.deleteUser(user.getUserid());
                System.out.println(empty);
                    //then - verify output

              Assertions.assertThat(empty).isNull();
              verify(userRepository,times(1)).deleteById(user.getUserid());
                }
       }
