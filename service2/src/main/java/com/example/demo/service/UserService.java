package com.example.demo.service;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserException;
import com.example.demo.external.AddressResolver;
import com.example.demo.mapper.UserMapper;
import com.example.demo.oth.Address;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Retrofit;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    //private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private AddressResolver addressResolver;
    private Retrofit retrofit;

    @Autowired
    public UserService(Retrofit retrofit, UserMapper userMapper, RestTemplate restTemplate, UserRepository userRepository) {
        this.userRepository = userRepository;
        addressResolver = new AddressResolver(retrofit);
        this.userMapper = userMapper;
        //this.restTemplate = restTemplate;
    }

    public UserDTO createUser(UserDTO userDTO) throws Exception{

        //Address address = saveAddress(userDTO.getAddress());
        Address address = addressResolver.saveAddress(userDTO.getAddress());

        log.info("ADDRESS SAVED "+ address.getId());

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddressID(address.getId());

        User savedUser = userRepository.save(user);
        log.info("USER SAVED WITH ADDRESS" + savedUser.getAddressID());
        address.setId(savedUser.getId());

        return userMapper.toDTO(user, address);

    }

    public UserDTO getUser(UUID id) throws Exception{
        log.info("FETCHING USER DETAILS");
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserException("USER DOES NOT EXIST"));

        log.info("FETCHING ADDRESS DETAILS");
        //Address address = getAddress(existingUser.getAddressID());

        Address address = addressResolver.getAddress(existingUser.getAddressID());

        log.info("ADDRESS DETAILS FETCHED");
        return userMapper.toDTO(existingUser, address);
    }

    // ------------------------------------- EXTERNAL SERVICE ACCESS USING REST TEMPLATE --------------------------------------- //

//    private Address saveAddress(Address address) {
//        log.info("SAVING USER LINKED ADDRESS");
//        Address savedAddress = restTemplate.postForObject("http://localhost:8081/api/v1/address", address, Address.class);
//        return  savedAddress;
//    }
//
//    private Address getAddress(UUID id){
//        log.info("ACCESSING ADDRESS SERVICE");
//        Address existingAddress = restTemplate.getForObject("http://localhost:8081/api/v1/address/"+id, Address.class);
//        return existingAddress;
//    }


}


