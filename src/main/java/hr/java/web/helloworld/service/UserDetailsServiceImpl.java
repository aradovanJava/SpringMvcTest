package hr.java.web.helloworld.service;

import hr.java.web.helloworld.domain.ApplicationUser;
import hr.java.web.helloworld.domain.UserRole;
import hr.java.web.helloworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = this.repository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Unknown user " + username);
        }

        List<UserRole> userRoleList = user.getRoles();

        String[] roles = new String[userRoleList.size()];

        for(int i = 0; i < userRoleList.size(); i++) {
            roles[i] = userRoleList.get(i).getName();
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
