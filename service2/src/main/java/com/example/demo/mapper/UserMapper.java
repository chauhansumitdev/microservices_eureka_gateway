package com.example.demo.mapper;


import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.oth.Address;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public User toDAO(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddressID(userDTO.getAddress().getId());
        return user;
    }

    public UserDTO toDTO(User user, Address address){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(address.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(address);
        return  userDTO;
    }

}
