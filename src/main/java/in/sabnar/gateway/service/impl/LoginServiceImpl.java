//package in.sabnar.gateway.service.impl;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import in.sabnar.gateway.bean.Role;
//import in.sabnar.gateway.bean.User;
//import in.sabnar.gateway.exception.CustomException;
//import in.sabnar.gateway.repository.RoleRepository;
//import in.sabnar.gateway.repository.UserRepository;
//import in.sabnar.gateway.service.LoginService;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private RoleRepository roleRepository;
//	
//
//	@Override
//	public UserDetails login(String username, String password) {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//			User user = userRepository.findByEmail(username);
//			if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
//				throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
//			}
//			return toUserDetails(user);
//
//		} catch (AuthenticationException e) {
//			throw new CustomException("Invalid username or password.", org.springframework.http.HttpStatus.UNAUTHORIZED);
//		}
//	}
//	
//	private UserDetails toUserDetails(User userObject) {
//	    return org.springframework.security.core.userdetails.User.withUsername(userObject.getEmail())
//	            .password(userObject.getPassword())
//	            .roles(userObject.getRole().stream().map(Role::getRole).collect(Collectors.toList())).build();
//	}
//
//	@Override
//	public User saveUser(User user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		Set<Role> role = new HashSet<Role>();
//		
//		String givenRole = ((Role) user.getRole().toArray()[0]).getRole();
//		role.add(roleRepository.findByRole(givenRole).get());
//		user.setRole(role);
//        return userRepository.save(user);
//	}
//
//}
