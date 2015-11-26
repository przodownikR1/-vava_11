package pl.java.scalatech.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pl.java.scalatech.entity.User;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, User> {

	@Override
	public void initialize(PasswordsEqualConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if (!user.getPassword().equals(user.getConfirmPassword()))
			return false;
		return true;
	}

}


