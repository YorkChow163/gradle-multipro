# 在这里我们将实现动态的权限管理

## 重要类的功能流程概述
- AbstractSecurityInterceptor 
  - beforeInvocation(Object object)方法
    ```java
    protected InterceptorStatusToken beforeInvocation(Object object) {
            Assert.notNull(object, "Object was null");
            final boolean debug = logger.isDebugEnabled();

            if (!getSecureObjectClass().isAssignableFrom(object.getClass())) {
                throw new IllegalArgumentException(
                        "Security invocation attempted for object "
                                + object.getClass().getName()
                                + " but AbstractSecurityInterceptor only configured to support secure objects of type: "
                                + getSecureObjectClass());
            }
            //在这里我们将通过SecurityMetadataSource获取ConfigAttribute集合,一般这个集合我们存放的是角色集合
            Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource()
                    .getAttributes(object);

            if (attributes == null || attributes.isEmpty()) {
                if (rejectPublicInvocations) {
                    throw new IllegalArgumentException(
                            "Secure object invocation "
                                    + object
                                    + " was denied as public invocations are not allowed via this interceptor. "
                                    + "This indicates a configuration error because the "
                                    + "rejectPublicInvocations property is set to 'true'");
                }

                if (debug) {
                    logger.debug("Public object - authentication not attempted");
                }

                publishEvent(new PublicInvocationEvent(object));

                return null; // no further work post-invocation
            }

            if (debug) {
                logger.debug("Secure object: " + object + "; Attributes: " + attributes);
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                credentialsNotFound(messages.getMessage(
                        "AbstractSecurityInterceptor.authenticationNotFound",
                        "An Authentication object was not found in the SecurityContext"),
                        object, attributes);
            }

            Authentication authenticated = authenticateIfRequired();

            // Attempt authorization
            try {
                //通过AccessDecisionManager来决定用户访问的url是否拥有相应的权限
                this.accessDecisionManager.decide(authenticated, object, attributes);
            }
            catch (AccessDeniedException accessDeniedException) {
                publishEvent(new AuthorizationFailureEvent(object, attributes, authenticated,
                        accessDeniedException));

                throw accessDeniedException;
            }

            if (debug) {
                logger.debug("Authorization successful");
            }

            if (publishAuthorizationSuccess) {
                publishEvent(new AuthorizedEvent(object, attributes, authenticated));
            }

            // Attempt to run as a different user
            Authentication runAs = this.runAsManager.buildRunAs(authenticated, object,
                    attributes);

            if (runAs == null) {
                if (debug) {
                    logger.debug("RunAsManager did not change Authentication object");
                }

                // no further work post-invocation
                return new InterceptorStatusToken(SecurityContextHolder.getContext(), false,
                        attributes, object);
            }
            else {
                if (debug) {
                    logger.debug("Switching to RunAs Authentication: " + runAs);
                }

                SecurityContext origCtx = SecurityContextHolder.getContext();
                SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
                SecurityContextHolder.getContext().setAuthentication(runAs);

                // need to revert to token.Authenticated post-invocation
                return new InterceptorStatusToken(origCtx, true, attributes, object);
            }
        }
    ```