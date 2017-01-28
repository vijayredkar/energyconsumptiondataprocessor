# energyconsumptiondataprocessor

This Spring Boot REST application and can be built with Maven.
REST API summary is given below and the code too has been commented appropriately.

I had some issues in moving the files to my the bitbucket repository, hence added to my Github repository.

I have completed most of the requirements given in the usecase.
I have added unit tests for the CRUD scenarios but I realize that there is scope for more code coverage.
This application uses HSQL DB for persistence.
I have added core validations for the input data but I could not complete all the validations that were asked in the use case.

PS: I had to balance my current work targets and bring those to a logical conclusion in addition to completing this exercise.
Given this, I tried my best to complete the usecase requirements as much as possible.


REST API summary - Test data used for verifications using Postman REST client-

create meter readings
uri - localhost:8080/meterreadings/create
method - POST
data- [{"meterId":"0001","profileName":"A","month":"JAN","meterReading":1},{"meterId":"0001","profileName":"A","month":"FEB","meterReading":2},{"meterId":"0001","profileName":"A","month":"MAR","meterReading":3.2},{"meterId":"0001","profileName":"A","month":"APR","meterReading":4},{"meterId":"0001","profileName":"A","month":"MAY","meterReading":5},{"meterId":"0001","profileName":"A","month":"JUN","meterReading":6},{"meterId":"0001","profileName":"B","month":"JUL","meterReading":7},{"meterId":"0001","profileName":"B","month":"AUG","meterReading":8},{"meterId":"0001","profileName":"B","month":"SEP","meterReading":9},{"meterId":"0001","profileName":"B","month":"OCT","meterReading":10},{"meterId":"0001","profileName":"B","month":"NOV","meterReading":11},{"meterId":"0001","profileName":"B","month":"DEC","meterReading":12},{"meterId":"0002","profileName":"A","month":"JAN","meterReading":1},{"meterId":"0002","profileName":"A","month":"FEB","meterReading":2},{"meterId":"0002","profileName":"A","month":"MAR","meterReading":3},{"meterId":"0002","profileName":"A","month":"APR","meterReading":4},{"meterId":"0002","profileName":"A","month":"MAY","meterReading":5},{"meterId":"0002","profileName":"A","month":"JUN","meterReading":6},{"meterId":"0002","profileName":"B","month":"JUL","meterReading":7},{"meterId":"0002","profileName":"B","month":"AUG","meterReading":8},{"meterId":"0002","profileName":"B","month":"SEP","meterReading":9},{"meterId":"0002","profileName":"B","month":"OCT","meterReading":10},{"meterId":"0002","profileName":"B","month":"NOV","meterReading":11},{"meterId":"0002","profileName":"B","month":"DEC","meterReading":15}]


read meter readings
uri - localhost:8080/meters
method - GET

update meter readings
uri - localhost:8080/meterReadings/update
method - POST
data- {"meterId":"0001","profileName":"A","month":"JAN","meterReading":2}


delete meter readings
uri - localhost:8080/meterReadings/delete
method - POST
data- {"meterId":"0001","profileName":"A","month":"JAN","meterReading":2}


create profiles
uri - localhost:8080/profiles/create
method - POST
data- [{"month":"JAN","profile":"A","fraction":0.2},{"month":"FEB","profile":"B","fraction":0.1}]

read profiles
uri - localhost:8080/profiles
method - GET

update profiles
uri - localhost:8080/profiles/update
method - POST
data- {"month":"JAN","profile":"A","fraction":0.7}


delete profiles
uri - localhost:8080/profiles/delete
method - POST
data- {"month":"JAN","profile":"A","fraction":0.7}
