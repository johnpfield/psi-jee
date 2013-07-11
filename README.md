<link href="https://raw.github.com/clownfart/Markdown-CSS/master/markdown.css" rel="stylesheet"></link>

# Pivotal Security Interceptor for EJB 3  

EJB 3.0 includes the capability to use Aspect Oriented Programming techniques in your application. 

Specifically, EJB 3.0 supports the use of `Interceptors`, which enable you to intercept a call to an EJB method, 
in order to inject (at runtime) additional code that implements logic for cross-cutting concerns.  In this project, we've 
used this capability to implement a facility we call the Pivotal Security Interceptor (PSI-JEE).

The deployment package in this example consists of a single class that implements a "checkAccess()" method. 
This checkAccess() method is annotated to be an `@AroundInvoke` interceptor, and performs a mock access control function. 

When appropriately configured into the deployment descriptor for any target enterprise application archive (e.g. an EAR file), 
this interceptor can be invoked on one or more of the target application's Enterprise Java Bean method interface(s).

In this version of the code, the interceptor does not do any actual enforcement; it simply logs information about the current calling context.

First, the "checkAccess()" method will examine the `InvocationContext` in order to obtain the calling arguments that were passed 
to the intercepted target EJB method.  Next, the "checkAccess()" method will retrieve the `EJBContext`, 
and then from there will get the authenticated principal (userid of the caller). These details are logged, and then the call to the 
original EJB method is allowed to continue via a call to the `InvocationContext.proceed()` method.

A future version of this project may implement an actual security enforcement by permitting or denying access, as appropriate.

## Co-ordinates

* Team:
  * John Field (`jfield@gopivotal.com`)
  * Add Your Name Here (`my-mail@my-company.com`)


## Prerequisites

* A GIT client
* Apache Maven 3.x
* Apache Geronimo 3.x

# Quick Start

You'll need to have Apache Geronimo 3.x or another standards-compliant J2EE container in which to deploy your EAR file.
You can download Geronimo from [here.](http://geronimo.apache.org/downloads.html)

In general, the following steps are to be followed:

1. Install and run Geronimo as per [these instructions.](http://geronimo.apache.org/GMOxDOC30/quick-start-apache-geronimo-for-the-impatient.html)
2. Build and install the Pivotal Security Interceptor. 
3. Using the Geronimo management console, install the PSI jar file into the Geronimo repository.
4. Finally, amend your application EAR file to enable the Interceptor on the desired EJB method(s), and redeploy.

## Detailed Instructions for Build and Deploy


The build procedure for the PSI is quite simple.  If this works you are in business:

    $ git clone git://github.com/johnpfield/psi-jee.git
    $ cd psi-jee
    $ mvn clean install 

The generated JAR file can then be found in the `psi-jee/target` subdirectory (and in your local Maven repository).  This JAR can be deployed to Geronimo via the operators console. 

Log into the Geronimo console, e.g. point your browser at http://localhost:8080/console and authenticate as the system manager.  Once in the console pages, look to the left hand side pane and choose the Navigator's `Advanced` console view.  Next, in the tree view, choose `Resources`.  Below `Resources`, choose `Repository`. 

From the `Repository Viewer` page, locate the `Add Archive to Repository` heading, and then choose the `browse...` button.  Navigate to the JAR file on your local disk.  Enter the groupId as "com.gopivotal.security", and then click `install` to upload the JAR to the repository. 

If all goes well, you should see a confirmation message, and the PSI JAR should appear in the list of components on the bottom half of the page.  The interceptor is now ready for use in your application.

### Update Application Deployment Descriptor and Redeploy

You will need to add the interceptor to the target application EAR file.  The application's `META-INF/ejb-jar.xml` file must be updated to include the interceptor definition as shown in the code snippet below:

```xml
    <enterprise-beans>
     ...
    </enterprise-beans>

    <interceptors>
        <interceptor>
            <interceptor-class>
                com.gopivotal.security.EjbPolicyEnforcement
            </interceptor-class>
        </interceptor>
    </interceptors>
```

Next, associate the interceptor with the appropriate EJB methods by including an interceptor binding within the `<assembly-descriptor>` element:

```xml
	<assembly-descriptor>
	...	
	  <interceptor-binding>
            <ejb-name>ejb/MyTimeBean</ejb-name>
    		<interceptor-class>com.gopivotal.security.EjbPolicyEnforcement</interceptor-class>
            <method>
               <method-name>getTimePrivate</method-name>
            </method>    
          </interceptor-binding>

	</assembly-descriptor>
```

Finally, make sure you update the Maven POM file for your application so that it includes the dependency for the psi-jee-1.0.0.jar, as shown below.

```xml
	<dependencies>
	   <dependency>
		<groupId>com.gopivotal.security</groupId>
		<artifactId>psi-jee</artifactId>
		<version>1.0.0</version>
		<type>jar</type>
	   </dependency>
	</dependencies>
```

# Demo 

Point your browser at the home page of your application on the Geronimo server, i.e. `http://localhost:8080/myapp/` 

Authenticate if and as needed.  Then, access any function that will cause an invocation of a EJB method that you are intercepting.  You should see the interceptor be invoked, and as it runs it will log the details of the users request, including the authenticated userid (if any) and the arguments passed to the EJB method call from either the EJB Client, or the Web tier servlet.  A sample excerpt from the log is shown below for the [mytime] (https://github.com/johnpfield/mytime) application:


```
2013-07-11 11:11:28,690 INFO  [EjbPolicyEnforcement] Intercepted EJB invocation; operation name: getTimePrivate
2013-07-11 11:11:28,690 INFO  [EjbPolicyEnforcement] Calling Args: 
2013-07-11 11:11:28,690 INFO  [EjbPolicyEnforcement] 	 Go Blackhawks!
2013-07-11 11:11:28,691 INFO  [EjbPolicyEnforcement] The callerPrincipal is: johnfield
 
```

# Related Work

If you need a complete working example of how to configure your EAR file for the interceptor (or just need a simple standalone EJB application as a testbed), you can use the [mytime] (https://github.com/johnpfield/mytime) application.  This application includes both a Web tier and an EJB tier, and provides a simple stateless session bean with multiple business methods that you can intercept.
 

