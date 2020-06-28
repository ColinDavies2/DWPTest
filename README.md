# DWPTest

A Spring Boot application that provides an API that wraps the API at https://bpdts-test-app.herokuapp.com/

**GET** /users will return people who are listed as either living in London, or whose current coordinates are within 50 miles of London.

The request can be modified using the following (optional) query parameters:

| Name               | Type                     | min    |  max   |
| ------------------ | ------------------------ | ------ | ------ |
| city               | String                   | n/a    | n/a    |
| latitude           | double                   | -90.0  | 90.0   |
| longitude          | double                   | -180.0 | 180.0  |
| distance           | double                   | n/a    | n/a    |
| distanceUnit       | enum [miles, kilometers] | n/a    | n/a    |

e.g. a request to return people living in Edinburgh, or whose current coordinates are within 100 kilometers of Edinburgh would look like this:

**GET** /users?city=Edinburgh&latitude=55.9533&longitude=3.1883&distance=100&distanceUnit=kilometers

