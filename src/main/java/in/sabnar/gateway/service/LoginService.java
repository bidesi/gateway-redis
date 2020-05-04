package in.sabnar.gateway.service;

import in.sabnar.gateway.bean.User;

public interface LoginService {
	
	String login(String username, String password);

    boolean logout(String token);

    Boolean isValidToken(String token);

    String createNewToken(String token);
    
	User saveUser(User user);
}
