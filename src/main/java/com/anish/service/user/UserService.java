package com.anish.service.user;

import com.anish.dao.AuthorityRepository;
import com.anish.dao.UserDetailsRepository;
import com.anish.dto.request.UserCreationRequestDto;
import com.anish.entity.Authority;
import com.anish.entity.UserAuthorities;
import com.anish.entity.UserAuthoritiesPk;
import com.anish.entity.UserDetails;
import com.anish.exception.UnknownAuthorityException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserCreationRequestDto requestDto)
    {
        UserDetails userDetails = userDetailsRepository.save(mapUserDetails(requestDto));
        userDetails.setUserAuthorities(mapUserAuthorities(requestDto, userDetails));
    }

    private UserDetails mapUserDetails(UserCreationRequestDto requestDto)
    {
        UserDetails userDetails = modelMapper.map(requestDto, UserDetails.class);
        userDetails.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userDetails.setEnabled(true);
        userDetails.setAccountExpired(false);
        userDetails.setAccountLocked(false);
        userDetails.setAccountExpired(false);
        userDetails.setCredentialsExpired(false);
        userDetails.setCreatedBy("SYSTEM");
        userDetails.setCreatedTime(new Date());
        return userDetails;

    }

    private List<UserAuthorities> mapUserAuthorities(UserCreationRequestDto requestDto, UserDetails userDetails )
    {
        return requestDto.getRoles().stream().map(
                role -> {
                    Authority authority = authorityRepository.findByName(role).orElseThrow(() -> new UnknownAuthorityException(String.format("Invalid role name : %s", role)));
                    UserAuthorities u = new UserAuthorities();
                    UserAuthoritiesPk pk = new UserAuthoritiesPk();
                    pk.setAuthorityId(authority.getId());
                    pk.setUserId(userDetails.getId());
                    u.setId(pk);
                    u.setCreatedBy("SYSTEM");
                    u.setCreatedTime(new Date());
                    return u;
                }
        ).toList();
    }

}
