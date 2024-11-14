# BibliothequeJEE

A Java Spring Boot application designed to manage an image library, featuring a REST API and various backend functionalities. The application uses MongoDB for data storage and TensorFlow for advanced image processing.

## Features

- **Image Library Management**: Upload, view, edit, and delete images.
- **REST API**: Accessible endpoints for interacting with the library.
- **Machine Learning Integration**: TensorFlow-based processing for enhanced image features.
- **Session Management**: MongoDB-backed sessions with Spring Session.

## Technologies

- **Backend**: Spring Boot, Spring Web, Spring Session, MongoDB
- **Image Processing**: TensorFlow (via DJL - Deep Java Library)
- **Utility Libraries**: Commons IO, TwelveMonkeys ImageIO
- **Build Tool**: Maven

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/jee-groupe5/BibliothequeJEE.git
   cd BibliothequeJEE

2. Build the project with Maven:
   ```bash
   mvn clean install

## Usage

Start the application with:
   ```bash
    mvn spring-boot:run
