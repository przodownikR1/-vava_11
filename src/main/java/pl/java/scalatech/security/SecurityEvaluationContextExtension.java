package pl.java.scalatech.security;

import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.java.scalatech.annotation.SecurityComponent;
@SecurityComponent
public class SecurityEvaluationContextExtension extends EvaluationContextExtensionSupport {
        @Override
        public String getExtensionId() {
            return "security";
        }

        @Override
        public SecurityExpressionRoot getRootObject() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new SecurityExpressionRoot(authentication) {};
        }
    }