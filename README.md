# An Example JBehave JAVA11 REST API Client
ZipcodeAPI Problem statement

Send a GET request to

https://www.zipcodeapi.com/rest/<api_key>/multi-distance./<zip_code>/<other_zip_codes>/

Pick the first zip code from response and use it as zip_code2 in the below request https://www.zipcodeapi.com/rest/<api_key>/distance./<zip_code1>/<zip_code2>/

# How to run

simply run:  

mvn clean install

# where to see jbehave generated reports

as part of the run, jbehave automatically generates the report in html format. 
it can be found under
target/jbehave/view/reports.html

