Narrative:
RestFul Websevice: https://www.zipcodeapi.com/API

Write and execute test for the following two requests

Send a GET request to

https://www.zipcodeapi.com/rest/<api_key>/multi-distance.<format>/<zip_code>/<other_zip_codes>/<units>

Pick the first zip code from response and use it as zip_code2 in the below request
https://www.zipcodeapi.com/rest/<api_key>/distance.<format>/<zip_code1>/<zip_code2>/<units>

Scenario: initialise the endpoints
Given multi distance and single distance urls

Examples:
|multiDistanceURL|singleDistanceURL
|https://www.zipcodeapi.com/rest/%s/multi-distance.json/%s/%s/km|https://www.zipcodeapi.com/rest/%s/distance.json/%s/%s/km

Scenario: positive case with valid zip codes for multi level distance url
Given current zip code value
And target zip code values
When I check for first picked target zip code
Then The expected picked zip code matches actual first picked zip code

Examples:
|currentZip|targetZipCodes|expectedPickedZipCode
|75024|33102,78727,32801,33101,95023|33102

Scenario: positive case with valid zip codes for single level distance url
Given current zip code value
And target zip code values
When retrieving distance between target zip to current zip
Then Expected distance should match Actual distance

Examples:
|currentZip|targetZipCodes|expectedDistance
|75074|95206|2298.155


Scenario: positive case to pick first zip code from multi level and use it in single level to find distance
Given current zip code value
And target zip code values
When I check for first picked target zip code
When retrieving distance between target zip to picked zip
Then Expected distance should match Actual distance

Examples:
|currentZip|targetZipCodes|expectedPickedZipCode|expectedDistance
|75024|33102,78727,32801,33101,95023|33102|1798.471


Scenario: negative case with one invalid target zip codes
!-- here we are passing 93487 an invalid zip code
Given current zip code value
And target zip code values
When I check for first picked target zip code
When retrieving distance between target zip to picked zip
Then Expected distance should match Actual distance

Examples:
|currentZip|targetZipCodes|expectedDistance
|75024|93487,75014,75029|29.029