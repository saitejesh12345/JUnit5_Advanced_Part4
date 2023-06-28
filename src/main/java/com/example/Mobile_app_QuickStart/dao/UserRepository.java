package com.example.Mobile_app_QuickStart.dao;

import com.example.Mobile_app_QuickStart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

//custom query it will return UserObject By email
    Optional<User> findByEmail(String email);


    //define custom  using JPQL(Java persistenc query lang with index parameter)
    @Query("select u from User u where u.firstName = ?1 and u.lastName = ?2")
    User findByJPQL(String  firstName, String lastName);


    //define custom  using JPQL(Java persistenc query lang with named parameter)
    @Query("select u from User u where u.firstName =:firstName  and u.lastName = :lastName")
    User findByJPQLNamedParams(@Param("firstName") String  firstName, @Param("lastName") String lastName);

//SQL QUery
//define custom  using ( SQL lang with index parameter)
//    @Query(value = "select * from user u where u.first_name=?1 and u.last_name =?2",nativeQuery = true)
//    User findByNativeSql(String firstName,String lastName);

}
