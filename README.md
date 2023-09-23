# EventFinder

### Description

`EventFinder` is a Micronaut-based microservice optimized for managing a single object type stored in MongoDB. 
It features a collection of controller endpoints to streamline client-server communication and database access. 
The project is organized and built using Maven, and incorporates JMeter for targeted stress testing.


### Prerequisites

Before you begin, ensure you have met the following requirements:
1. You have installed [Java 17](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot).
2. You have installed [Maven](https://maven.apache.org/).
3. You have a working MongoDB setup. Learn how to set it up [here](https://www.mongodb.com/try/download/community).

### Installation

Clone the `EventFinder` repository to your local machine to get started with the project.


### Usage

To run `EventFinder`, follow these steps:

1. Navigate to the root directory of the project.
2. Use the command `mvn clean install` to build the project.
3. Run the application with `java -jar target/eventfinder-1.0.jar`.

### JMeter Performance Testing

[JMeter](https://jmeter.apache.org/) is an open-source tool designed for load testing and measuring the performance of various services, 
with a focus on web applications. It can simulate multiple users with concurrent threads, generate a heavy load against web servers,
and analyze the overall service performance under different types of load.

**Creating Custom Tests:**  
For guidance on creating your own JMeter tests, consult the [JMeter User Guide](jmeter-guide.md).

**Running Tests:**  
To execute the tests, simply run `mvn verify`.

**Viewing Test Results:**  
Results are generated in the `target` directory and can be viewed in real-time in the console output if running tests from the command line.

#### Author

Petter Bergström / [juninatt](https://github.com/juninatt) on GitHub



