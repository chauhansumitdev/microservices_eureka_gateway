package com.example.demo.external;

import com.example.demo.exceptions.UserException;
import com.example.demo.oth.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.UUID;

@Slf4j
public class AddressResolver {

    AddressInterface addressInterface;
    Retrofit retrofit;

    @Autowired
    public AddressResolver(Retrofit retrofit){
        this.retrofit = retrofit;
        addressInterface = retrofit.create(AddressInterface.class);
    }

    public Address getAddress(UUID id) throws  Exception{
        log.info(String.valueOf(id));
        Call<Address> address = addressInterface.getAddress(id);
        Response<Address> response = address.execute();


        log.warn(String.valueOf(response.isSuccessful()));

        if(response.isSuccessful()){
            return response.body();
        }else{
            throw new UserException("INVALID USER CREDENTIALS");
        }
    }

    public Address saveAddress(Address address) throws Exception{
        Call<Address> addressSaved = addressInterface.postAddress(address);
        Response<Address> response = addressSaved.execute();

        if(response.isSuccessful()){
            return response.body();
        }else{
            throw  new UserException("ERROR SAVING USER ADDRESS");
        }
    }
}
