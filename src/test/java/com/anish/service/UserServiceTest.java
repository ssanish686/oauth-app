package com.anish.service;

import com.anish.dao.AuthorityRepository;
import com.anish.dao.UserDetailsRepository;
import com.anish.dto.request.UserCreationRequestDto;
import com.anish.entity.UserAuthorities;
import com.anish.entity.UserDetails;
import com.anish.exception.UnknownAuthorityException;
import com.anish.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EnableTransactionManagement
public class UserServiceTest {
    @Autowired
    private UserService test;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @Test
    @Transactional
    public void testCreateUser()
    {
        final String ROLE = "USER_ROLE";
        UserCreationRequestDto stub = createStub(ROLE);
        test.createUser(stub);
        UserDetails userDetails = userDetailsRepository.findByUserName(stub.getUserName()).orElseThrow();
        assertTrue(userDetails.getUserName().equals(stub.getUserName()),"Input username should be same");
        assertTrue(!userDetails.getPassword().equals(stub.getPassword()),"Input password and password stored should not be same. " +
                "Password stored should ne encrypted.");
        List<UserAuthorities> authorities = userDetails.getUserAuthorities();
        assertTrue(authorities.size() == 1,"One authority expected");
        assertTrue(authorities.get(0).getId().getAuthorityId().equals(authorityRepository.findByName(ROLE).get().getId()),"Role Id should be matching");

    }

    @Test
    @Transactional
    public void testCreateUserWithInvalidRole()
    {
        final String ROLE = "XXXX";
        UserCreationRequestDto stub = createStub(ROLE);
        Exception exception = assertThrows(UnknownAuthorityException.class, () -> {
            test.createUser(stub);
        });
        assertEquals(String.format("Invalid role name : %s", ROLE),exception.getMessage());
    }

    private UserCreationRequestDto createStub(String role)
    {
        UserCreationRequestDto dto = new UserCreationRequestDto();
        dto.setPassword("password");
        dto.setUserName("username");
        dto.setRoles(List.of(role));
        return dto;
    }
}
