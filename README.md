# Track Movie Views
Tracks the total views of a movie hosted in Vimeo

This application is to	track	Vimeo	views	on	the	trailers	of	a	number	of	movies	 at	
an hourly	frequency	 in	order	to	understand	the	change	in	views	 throughout	a	24-hour	period.

## Getting Started

This application uses Spring Batch to achieve the result as the requirement overlaps with various stages 
of an ETL operation like reading movie views from the database, procressing invloving fetching views from Vimeo server, 
comparing values against previous run, computing the total views and finally writes data to a datasrtore like database, AWS S3.


These instructions will get you a copy of the application up and running on your local machine for development purpose 
only (pending unit tests). 
See deployment for notes on how to build and deploy the application.

### Dependency Libraries
```
Spring Core 3.2.2.RELEASE -- For IOC, Multithreading, Rest Template
Spring Batch 2.2.0.RELEASE -- For Spring batch (ETL operation)
MySQL Connector 5.1.31 -- For accessing MySQL database
SL4J 1.7.2 -- Logging facade with Log4J implementation
AWS Java SDK 1.9.6 -- For accessing AWS services
GSON 2.6.2 -- Converting JSON to Java Pojo
```
### Prerequisities
```
Oracle JDK 1.7 or above
Apache Maven 3.1.1 or above
AWS Access Key ID
AWS Secret Access Key
Vimeo OAuth Access Token
```
### Deployment

The below command will compile, build and execute the application.

```
mvn clean install exec:java -Daws.accessKeyId=<aws.accessKeyId> -Daws.secretKey=<aws.secretKey> -Dvimeo.oauthToken=<vimeo.oauthToken>
```








