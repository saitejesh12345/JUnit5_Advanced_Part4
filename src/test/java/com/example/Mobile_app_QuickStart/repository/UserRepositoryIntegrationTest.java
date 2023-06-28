package com.example.Mobile_app_QuickStart.repository;


import com.example.Mobile_app_QuickStart.dao.UserRepository;
import com.example.Mobile_app_QuickStart.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest //It will test DAO layer and that will
// auto configure in-memory embedded database for testing.It will wont load other layer components.



//This below annotation makes Repository Intergation jst same as Unit testing Repository
//jst add this "@AutoConfigureTestDatabase" annotation and change the properties of Mysql DB/SQL DB
//in application properties by disabeling H2 in memory datatbase as of now
//we dont have Real databases in our machine so we cannot configure.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User user,user1;
@BeforeEach
public void Setup(){
    user =  User.builder()
            .firstName("Ramesh")
            .lastName("thammali")
            .email("ramesh@gmail.com")
            .build();
    user1 = User.builder()
            .firstName("Rajesh")
            .lastName("thami")
            .email("rajesh@gmail.com")
            .build();
}

    @DisplayName("Junit test for save User Operation")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUserObject(){

        //given - PreCondition or Setup
//        User user = User.builder()
//                .firstName("Ramesh")
//                .lastName("thammali")
//                .email("ramesh@gmail.com")
//                .build();
        //when - action or behaviour we are gng to test
        User savedUser = userRepository.save(user); //actual output

        //then - verify output
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUserid()).isGreaterThan(0);
    }


    @DisplayName("Junit test for getAll User Operation")
    @Test
    public void givenUsersObjects_whenFindAll_thenUsersList(){

        //given - PreCondition or Setup
//        User user = User.builder()
//                .firstName("Ramesh")
//                .lastName("thammali")
//                .email("ramesh@gmail.com")
//                .build();
//
//        User user1 = User.builder()
//                .firstName("Rajesh")
//                .lastName("thami")
//                .email("rajesh@gmail.com")
//                .build();
        userRepository.save(user);
        userRepository.save(user1);

        //when - action or behaviour we are gng to test

List<User> userList = userRepository.findAll();

        //then - verify output
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }


    @DisplayName("Junit test for get User By id")
    @Test
    public void givenUserObject_whenFindById_thenReturnUserObject(){

            //given - PreCondition or Setup
//        User user = User.builder()
//                .firstName("Ramesh")
//                .lastName("thammali")
//                .email("ramesh@gmail.com")
//                .build();
//
//        User user1 = User.builder()
//                .firstName("Rajesh")
//                .lastName("thami")
//                .email("rajesh@gmail.com")
//                .build();
        userRepository.save(user);
        userRepository.save(user1);

            //when - action or behaviour we are gng to test

        User  getuser = userRepository.findById(user.getUserid()).get();

            //then - verify output

       Assertions.assertThat(getuser).isNotNull();
    }

    @DisplayName("Junit test for get User  by email ")
    @Test
    public void givenUserEmail_whenFindByEmail_thenReturnUserObject(){


            //given - PreCondition or Setup
        User user = User.builder()
                .firstName("Suresh")
                .lastName("thammali")
                .email("Suresh@gmail.com")
                .build();
        userRepository.save(user);


            //when - action or behaviour we are gng to test
User userDb = userRepository.findByEmail(user.getEmail()).get();

            //then - verify output

        Assertions.assertThat(userDb).isNotNull();
        }



        @DisplayName("Junit test for UpdateUser Operation ")
        @Test
        public void givenUserObject_whenUpdateUser_thenReturnUserObject(){

                //given - PreCondition or Setup
            User user = User.builder()
                    .firstName("Suresh")
                    .lastName("thammali")
                    .email("Suresh@gmail.com")
                    .build();
            userRepository.save(user);


                //when - action or behaviour we are gng to test
User updateUser = userRepository.findById(user.getUserid()).get();

updateUser.setEmail("ram@gmail.com");
updateUser.setFirstName("ram");
     User updatedUser = userRepository.save(updateUser);

            //then - verify output
            Assertions.assertThat(updatedUser.getEmail()).isEqualTo("ram@gmail.com");
            Assertions.assertThat(updatedUser.getFirstName()).isEqualTo("ram");

            }

            @DisplayName("Junit test for Delete User operation")
            @Test
            public void givenUserObject_whenDelete_thenRemoveUserObject(){

                    //given - PreCondition or Setup
                User user = User.builder()
                        .firstName("Suresh")
                        .lastName("thammali")
                        .email("Suresh@gmail.com")
                        .build();
                userRepository.save(user);

                    //when - action or behaviour we are gng to test
            userRepository.deleteById(user.getUserid());
                Optional<User> userOptional = userRepository.findById(user.getUserid());


                //then - verify output
                Assertions.assertThat(userOptional).isEmpty();
                }

//custom query

    @DisplayName("Junit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnUserObject(){

            //given - PreCondition or Setup
        User user = User.builder()
                .firstName("Suresh")
                .lastName("thammali")
                .email("Suresh@gmail.com")
                .build();
        userRepository.save(user);

        String firstName ="Suresh";
        String lastName ="thammali";

            //when - action or behaviour we are gng to test
User savedUser = userRepository.findByJPQL(firstName,lastName);
            //then - verify output
        Assertions.assertThat(savedUser).isNotNull();
        }


    @DisplayName("Junit test for custom query using JPQL with NamedParams")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnUserObject(){

        //given - PreCondition or Setup
        User user = User.builder()
                .firstName("Suresh")
                .lastName("thammali")
                .email("Suresh@gmail.com")
                .build();
        userRepository.save(user);

        String firstName ="Suresh";
        String lastName ="thammali";

        //when - action or behaviour we are gng to test
        User savedUser = userRepository.findByJPQLNamedParams(  firstName,lastName);
        //then - verify output
        Assertions.assertThat(savedUser).isNotNull();
    }

}
