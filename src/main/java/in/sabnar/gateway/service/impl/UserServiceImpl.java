package in.sabnar.gateway.service.impl;

import in.sabnar.gateway.bean.Role;
import in.sabnar.gateway.bean.User;
import in.sabnar.gateway.dto.AdditionalInfo;
import in.sabnar.gateway.repository.RoleRepository;
import in.sabnar.gateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdditionalInfo additionalInfo = null;
        List authorities = new ArrayList();

        if (!"".equals(username)) {
            User user = userDao.findByUsername(username);
            if (null != user){
                Map<String, Object> enhanceDataMap = new HashMap();
                enhanceDataMap.put("name", "Bidesi Gauda");

                additionalInfo = new AdditionalInfo(username,
                        user.getPassword(), true, true, true, true, getAuthorities(user), enhanceDataMap);

            }else{

                //log.error("Invalid username or password.");
                //throw new UsernameNotFoundException("Invalid username or password.");

                additionalInfo = new AdditionalInfo(username, "NA", true,
                        true, true, true, authorities, null);
            }
        }else {
            additionalInfo = new AdditionalInfo(username, "NA", true,
                    true, true, true, authorities, null);
        }

        return additionalInfo;







//        if (user == null) {
//            log.error("Invalid username or password.");
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                grantedAuthorities);
    }

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<Role> roles = user.getRoles();
        final Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString().toUpperCase()))
                .collect(Collectors.toSet());
        return authorities;
    }
}
