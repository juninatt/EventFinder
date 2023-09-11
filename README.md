# EventFinder

## Description

`EventFinder` is a Micronaut-based microservice designed to manage and retrieve different kinds of cultural events. It leverages MongoDB as its primary data store and is organized and built using Maven.


### Prerequisites

Before you begin, ensure you have met the following requirements:
1. You have installed [Java 17](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot).
2. You have installed [Maven](https://maven.apache.org/).
3. You have a working MongoDB setup. Learn how to set it up [here](https://www.mongodb.com/try/download/community).

### Installation

Clone the `EventFinder` repository to your local machine to get started with the project.

## Configuration

The application utilizes Micronaut's `application.yml` for configuration. Ensure to set up the MongoDB URI and database name correctly before running the application.

```yaml
micronaut:
  application:
    name: eventFinder

mongodb:
  uri: mongodb://localhost:27017/eventsdb
    ...
```

## Usage

To run `EventFinder`, follow these steps:

1. Navigate to the root directory of the project.
2. Use the command `mvn clean install` to build the project.
3. Run the application with `java -jar target/eventfinder-1.0.jar`.

## Documentation

`EventFinder` is thoroughly documented using JavaDoc. Key classes include:
- `MongoConfig`: Handles MongoDB configuration properties.
- `MongoEventRepository`: Implements CRUD operations for events.

For detailed documentation, refer to the JavaDoc comments in the respective source files.

## Author

Petter Bergström / [juninatt](https://github.com/juninatt) on GitHub



