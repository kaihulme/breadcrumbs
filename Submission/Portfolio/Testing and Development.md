Testing and Development
=======================

Testing Strategy
------------------
We had 4 core functionalities which required extensive testing. These included scanning the QR codes, map generation and rendering, the quiz application and administration via CRUD operations. Our System Architecture was also nicely divided into 3 key components:
1. The Database
2. The Web
3. The User Interface

Testing these components was necessary for us to assess the robustness of the functionalities and allowed us to incorporate back and front-end testing via unit and integration tests.

### Data Layer

We used Mockito to test that the SQL Queries were written properly. This was because we did not want these tests to alter the actual database. The connection to the database was mocked and instead connected to a volatile in-memory database created specifically for testing. This made it easier to spot syntax errors mainly with the queries themselves as well as identify if anything wasn't being received properly. However, there was a slight caveat in this approach. We faced problems using JPA earlier on  so we couldn't have the database be automatically created by looking at the classes. This meant that we had to write a Drop-Create Script which mimicked the actual database. The probem arose when columns which were added to the real database weren't updated in the Drop Create Script so this had to be manually changed which decreased our productivity. Testing this layer allowed us to check that the CRUD operations needed for the admins was working smoothly.

### Web layer

Our web layer was tested using Spring Boot's Junit runner which allowed us to mock the MVC and check that the right status responses were received. This made it easy to check that every page was loading via testing the GET requests, which was necessary so that the web pages wouldn't unexpectedly crash when the experts are using them. Tests were also made to check that accessing a page without logging in redirected you and also test that only admins could access the management page. The problem we faced was that as the database was hosted on another server, testing the web server actually connected it to the database deployment server. As a result we could only test GET requests which was very useful but limited the scope of what we could actually test. Luckily, as the data layer was tested rigorously, this wasn't too big of an issue because the API calls simply acted as a wrapper to the data layer calls. It also helped to serve as Integration testing between the web and data components.

### User Interface

Testing some parts of the Android application and the web via unit tests was difficult because the api had already been tested, the biggest issues were to do with the UI. This had to be done manually as human input was required to make sure everything looked alright. This hampered our productivity. For the Android Application, we looked into Espresso and decided that this could help solve our problem. However functions such as QR code scanning had to be tested via manually with a textbox which showed the url the QR code scanned.
