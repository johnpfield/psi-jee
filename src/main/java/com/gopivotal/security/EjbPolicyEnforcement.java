/*
 * Copyright (c) [2013] GoPivotal, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product includes a number of subcomponents with
 * separate copyright notices and license terms. Your use of these
 * subcomponents is subject to the terms and conditions of the
 * subcomponent's license, as noted in the LICENSE file.
 */

package com.gopivotal.security;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.logging.*;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.annotation.*;
import javax.ejb.*;

/**
 * @author John P. Field
 *
 */

public class EjbPolicyEnforcement {
	
	@SuppressWarnings("restriction")
	@Resource
	private SessionContext theContext;
	
	private Logger logger;

	public EjbPolicyEnforcement() {
		logger = Logger.getLogger("com.gopivotal.security.EjbPolicyEnforcement");
		return;
	}
	
		
	@AroundInvoke
	public Object checkAccess(InvocationContext invocationContext) throws Exception
	{
		Method operation = invocationContext.getMethod();
		String name = operation.getName();
		Object params[] = invocationContext.getParameters();  // For ETP we'll want the 4th parameter
		
		logger.log(Level.INFO, "Intercepted EJB invocation; operation name: " + name + "\n");
		System.out.println("Intercepted EJB invocation; operation name: " + name + "\n");
		
		logger.log(Level.INFO, "Calling Args: \n");		
		System.out.println("Calling Args: \n");

		for (int i = 0; i < params.length; i++ ) {
			logger.log(Level.INFO, "\t" + params[i].toString() + "\n");
			System.out.println("\t" + params[i].toString() + "\n");
		}

//   TODO:  Need to understand why the principals are not available via the ProtectionDomain. 
//		Principal[] thePrincipals = invocationContext.getTarget().getClass().getProtectionDomain().getPrincipals();
//		
//		if (thePrincipals.length > 0) {
//			logger.log(Level.INFO, "\t Trying to list Principals via the ProtectionDomain: \n");
//			for (int i = 0; i < thePrincipals.length; i++ ) {
//				logger.log(Level.INFO, "Principal[" + i + "]:" + "\t" + thePrincipals[i].getName() + "\n");
//			}
//		}
//		else {
//			logger.log(Level.INFO, "There were no Principals available via the ProtectionDomain: \n");		
//		}

		if (theContext != null) {
			Principal thePrincipal = theContext.getCallerPrincipal();
			logger.log(Level.INFO, "The callerPrincipal is: " +  thePrincipal.getName() + "\n");
			System.out.println("The callerPrincipal is: " +  thePrincipal.getName() + "\n");
		}
		
		return invocationContext.proceed();
	}

}