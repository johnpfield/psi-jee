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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author John P. Field
 *
 */

/**
 * Unit test for the Pivotal Security Intercepter.
 */
public class EjbPolicyEnforcementTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EjbPolicyEnforcementTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EjbPolicyEnforcementTest.class );
    }

    /**
     * For now, we'll defer on developing a rigorous test suite...but at least
     * I've included JUnit, and have the best of intentions :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
