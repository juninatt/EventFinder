### Welcome to SocialAlert – Your Universal Social Data Gathering Tool!

# About SocialAlert

SocialAlert is a powerful platform that empowers you to monitor social media for specific keywords
and create detailed alerts based on users' behaviors. Whether you are friendly neighbourhood company trying to remove offensive content,
a government aiming to silence opposition, or a business wanting to 'understand' its customers, SocialAlert provides you with the insights you need.

### With SocialAlert, you can:

* __Track Political Opposition:__ Identify and monitor individuals expressing unwanted political ideas.
* __Map Social Interactions:__ Keep an eye on what people are saying about you and your business.
* __Suppress Unwanted Opinions:__ Locate and neutralize those expressing undesirable views and opinions.
* __Tailor Advertising Campaigns:__ Use data to target your ads to specific demographics.
* __Establish Personal Profiles:__ Gather information about individual users to sell to the highest bidder. 

### Your Tool, Your Purpose

SocialAlert grants you complete freedom to use the data you gather in any way you see fit. Our software is flexible and customizable to meet your unique requirements. 
Whether you are interested in shaping public opinion, boosting sales, or simply knowing what people are saying about you, 
SocialAlert equips you with the tools for success.

What are you waiting for? Contact us today and let's start shaping the future together.

__SocialAlert__ – _Your Key to Limitless Social Insight_



### Prerequisites

Before you begin, ensure you have met the following requirements:
1. You have installed [Java 17](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot).
2. You have installed [Maven](https://maven.apache.org/).
3. You have a working MongoDB setup. Learn how to set it up [here](https://www.mongodb.com/try/download/community).

### Installation

Clone the `SocialAlert` repository to your local machine to get started with the project.


### Usage

To run `SocialAlert`, follow these steps:

1. Navigate to the root directory of the project.
2. Use the command `mvn clean install` to build the project.
3. Run the application with `java -jar target/socialalert-1.0.jar`.



### Docker

To facilitate the use of Docker in the SocialAlert project, a Dockerfile and a docker-compose.yml file are included in the repository. 
These files are configured to build a Docker image of the application and run it along with a MongoDB container.
Starting the Application and MongoDB with Docker Compose

To build the Docker image and spin up both the SocialAlert application and MongoDB containers, navigate to the root directory of the SocialAlert project and run the following command:

```bash
docker-compose up --build
```

This command will do the following:

1.   Build the SocialAlert Docker image based on the Dockerfile in the current directory.
2.   Start a container for MongoDB.
3.   Start the SocialAlert application container, which will use MongoDB for its data storage.

For Docker-specific usage see their [Official Website](https://www.docker.com/)

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



