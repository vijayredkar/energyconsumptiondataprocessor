# energyconsumptiondataprocessor

Spring Boot REST application to read and process meter readings
Build : Maven
Persistence: HSQL DB using JPA

Launch the service:
  Download the code base
  on a command prompt, navigate to \powerhouse\energy-web-services
  run the command : spring-boot:run

Testing: can use Postman REST client to submit requets

Use cases -

1-create meter readings
  URI - localhost:8080/meterreadings/create
  method - POST
  request body -
  data- [{"meterId":"0001","profileName":"A","month":"JAN","meterReading":1},{"meterId":"0001","profileName":"A","month":"FEB","meterReading":2},{"meterId":"0001","profileName":"A","month":"MAR","meterReading":3.2},{"meterId":"0001","profileName":"A","month":"APR","meterReading":4},{"meterId":"0001","profileName":"A","month":"MAY","meterReading":5},{"meterId":"0001","profileName":"A","month":"JUN","meterReading":6},{"meterId":"0001","profileName":"B","month":"JUL","meterReading":7},{"meterId":"0001","profileName":"B","month":"AUG","meterReading":8},{"meterId":"0001","profileName":"B","month":"SEP","meterReading":9},{"meterId":"0001","profileName":"B","month":"OCT","meterReading":10},{"meterId":"0001","profileName":"B","month":"NOV","meterReading":11},{"meterId":"0001","profileName":"B","month":"DEC","meterReading":12},{"meterId":"0002","profileName":"A","month":"JAN","meterReading":1},{"meterId":"0002","profileName":"A","month":"FEB","meterReading":2},{"meterId":"0002","profileName":"A","month":"MAR","meterReading":3},{"meterId":"0002","profileName":"A","month":"APR","meterReading":4},{"meterId":"0002","profileName":"A","month":"MAY","meterReading":5},{"meterId":"0002","profileName":"A","month":"JUN","meterReading":6},{"meterId":"0002","profileName":"B","month":"JUL","meterReading":7},{"meterId":"0002","profileName":"B","month":"AUG","meterReading":8},{"meterId":"0002","profileName":"B","month":"SEP","meterReading":9},{"meterId":"0002","profileName":"B","month":"OCT","meterReading":10},{"meterId":"0002","profileName":"B","month":"NOV","meterReading":11},{"meterId":"0002","profileName":"B","month":"DEC","meterReading":15}]


2-read meter readings
  URI - localhost:8080/meters
  method - GET

3-update meter readings
  uri - localhost:8080/meterReadings/update
  method - POST
  data- {"meterId":"0001","profileName":"A","month":"JAN","meterReading":2}

4-delete meter readings
  URI - localhost:8080/meterReadings/delete
  method - POST
  data- {"meterId":"0001","profileName":"A","month":"JAN","meterReading":2}


5-create profiles
  URI - localhost:8080/profiles/create
  method - POST
  data- [{"month":"JAN","profile":"A","fraction":0.2},{"month":"FEB","profile":"B","fraction":0.1}]

6-read profiles
  URI - localhost:8080/profiles
  method - GET

7-update profiles
  URI - localhost:8080/profiles/update
  method - POST
  data- {"month":"JAN","profile":"A","fraction":0.7}
  
8-delete profiles
  URI - localhost:8080/profiles/delete
  method - POST
  data- {"month":"JAN","profile":"A","fraction":0.7}
