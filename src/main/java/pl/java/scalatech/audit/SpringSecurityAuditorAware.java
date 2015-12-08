package pl.java.scalatech.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pl.java.scalatech.entity.User;
import pl.java.scalatech.security.UserSec;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof User) {
				return (User) principal;
			}else if(principal instanceof UserSec) {
			    UserSec userSec = (UserSec) principal;
			    User user = new User();
			    user.setLogin(userSec.getUsername());
			    user.setId(userSec.getId());
			    return user;
			}
		}
		return null;
	}
}