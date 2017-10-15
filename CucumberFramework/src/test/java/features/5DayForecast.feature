Feature: 5 Day Weather Forecast
Enter city name, get 5 day weather forecast


Scenario Outline: Enter city name, get 5 day weather forecast
Given Enter city name <cityName>
When Hit Enter key
Then Verify 5 day weather forecast displayed

Examples:
|cityName  |
|Edinburgh |

Scenario Outline: Enter invalid city name and confirm error message
Given Enter city name <cityName>
When Hit Enter key
Then Verify Error Message <Error Message>

Examples:
|cityName  | Error Message |
|London | Error retrieving the forecast|

