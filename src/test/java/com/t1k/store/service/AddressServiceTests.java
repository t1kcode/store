package com.t1k.store.service;

import com.t1k.store.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressServiceTests
{
    @Autowired
    IAddressService service;

    @Test
    void addAddress()
    {
//        Address address = new Address();
//        Integer uid = 1;
//        String username = "t1k";
//        address.setUid(uid);
//        address.setName("t1k");
//        service.addAddress(uid, username, address);
    }

    @Test
    void getAddress()
    {
        Address address = new Address();
        Integer uid = 1;
        String username = "t1k";
        address.setUid(uid);
        address.setName("t1k");
        service.getAddressList(uid, username);
    }

    @Test
    void setDefault()
    {
        service.setDefault(6, 1, "t1k");
    }
}
