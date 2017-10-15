@smoke
Feature: Select day, get 3 hourly forecast

Scenario Outline: Select day, get 3 hourly forecast
Given Enter city name <cityName>
When Hit Enter key
And Select Day <day>
When Verify 3 hourly forecast <day>

Examples:
|cityName  |day |
|Edinburgh |1 |
|Edinburgh |5 |

Scenario Outline: Select day again, hide 3 hourly forecast
Given Enter city name <cityName>
When Hit Enter key
And Select Day <day>
And Select Day <day>
Then Verify hourly forecast is hidden <day>

Examples:
|cityName  |day |
|Edinburgh |1 |
|Edinburgh |5 |

