package com.example.janche.security.hadler;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

public class SecuritySecurityExpressionHandler implements SecurityExpressionHandler<FilterInvocation> {
    @Override
    public ExpressionParser getExpressionParser() {
        return null;
    }

    @Override
    public EvaluationContext createEvaluationContext(Authentication authentication, FilterInvocation filterInvocation) {
        return null;
    }
}
