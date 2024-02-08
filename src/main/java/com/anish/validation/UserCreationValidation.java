package com.anish.validation;

import com.anish.dao.UserDetailsRepository;
import com.anish.dto.request.UserCreationRequestDto;
import com.anish.validation.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCreationValidation {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public void validate(UserCreationRequestDto requestDto)
    {
        checkUserExists(requestDto.getUserName());
    }

    private void checkUserExists(String userName)
    {
        if(userDetailsRepository.findByUserName(userName).isPresent()) {
            throw new UserAlreadyExistsException(String.format("Username %s already present", userName));
        }
    }
}
