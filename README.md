<link href="https://raw.github.com/clownfart/Markdown-CSS/master/markdown.css" rel="stylesheet"></link>

# Pivotal Security Interceptor for EJB 3  

EJB 3.0 includes the capability to use Aspect Oriented Programming techniques in your application. 

Specifically, EJB 3.0 supports the use of `Interceptors`, which enable you to intercept a call to an EJB method, 
in order to inject (at runtime) additional code that implements logic for cross-cutting concerns.

The deployment package in this example consists of a single class that implements a "checkAccess()" method. 
This checkAccess() method is annotated to be an `@AroundInvoke` interceptor, and performs a mock access control function. 

When appropriately configured into the deployment descriptor for any target application archive (e.g. an EAR file), 
this interceptor will be invoked on the target application's Enterprise Java Bean method interface(s).

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


## Quick Start

As prerequisites, you'll need to have Apache Geronimo 3.x or another standards-compliant J2EE container in which to deploy your EAR file.
You can download Geronimo from [here.](http://geronimo.apache.org/downloads.html)

If this works you are in business:

    $ git clone git://github.com/johnpfield/psi-jee.git
    $ cd psi-jee
    $ mvn clean install

This will create the JAR file in the psi-jee target directory, which you can then deploy using the Geronimo management console.

### Prerequisites

1. Install and run Geronimo as per [these instructions.](http://geronimo.apache.org/GMOxDOC30/quick-start-apache-geronimo-for-the-impatient.html)
2. In the Geronimo Management Console pages, add the psi-jee interceptor JAR file to the Geronimo repository.  From the navigation pane on the left-hand side of the Geronimo console, choose `Repository`.
3. From the Repository page, choose... 

 
### Build and Deploy

If this works you are in business:

    $ git clone git://github.com/johnpfield/psi-jee.git
    $ cd psi-jee
    $ mvn clean install 

The generated JAR file can be found in the `psi-jee/target` subdirectory.  This can be deployed to Geronimo via the Navigatoir's `Advanced` console view.  Choose "Repository," and then navigate to the JAR file on your local disk, and then click `install` to upload to the repository.  The groupId should be set to "com.gopivotal.security".

### Demo 

Point your browser at the home page of the application at the Geronimo server, i.e. `http://localhost:8080/myapp/`  

Access a function that will cause an invocation of a bean method that you are intercepting and you should see the interceptor log that request....
more ...


### Discussion

How does it work??

* Point 1

* Point 2

* Point 3

### More



