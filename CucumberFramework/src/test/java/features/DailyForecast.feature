@sanity
Feature: Daily forecast should summarise the 3 hour data

Scenario Outline: Most dominant (or current) condition
Given Enter city name <cityName>
When Hit Enter key
And Select Day <day>
When Verify Most dominant condition <day>

Examples:
|cityName  |day |
|Edinburgh |1 |
|Edinburgh |5 |

Scenario Outline: Most dominant (or current) wind speed and direction
Given Enter city name <cityName>
When Hit Enter key
And Select Day <day>
When Verify Most dominant Wind Speed <day>

Examples:
|cityName  |day |
|Edinburgh |1 |
|Edinburgh |5 |

@Sethu
Scenario Outline: Aggregate rainfall
Given Enter city name <cityName>
When Hit Enter key
And Select Day <day>
When Verify Aggregate rainfall <day>

Examples:
|cityName  |day |
|Edinburgh |1 |
|Edinburgh |5 |

@SethuMathavan
Scenario Outline: Minimum and maximum temperatures
Given Enter city name <cityName>
When Hit Enter key
And Select Day <day>
When Verify Max and Min Temp <day>

Examples:
|cityName  |day |
|Edinburgh |1 |
|Edinburgh |5 |


