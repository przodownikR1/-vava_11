package pl.java.scalatech.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.java.scalatech.entity.Product;
import pl.java.scalatech.entity.User;

@Component
public class ProductPermissionEvaluator implements PermissionEvaluator {
	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		if(authentication == null) {
			return false;
		}
		Product product= (Product) targetDomainObject;
		if(product == null) {
			return true;
		}
		User currentUser = (User) authentication.getPrincipal();
		return currentUser.getId().equals(product.getOwner().getId());
	}

	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}


}