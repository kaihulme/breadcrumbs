# breadcrumbs

## Project Overview

BioScientifica are the commercial subsidiary of the Society for Endocrinology, a non-profit organisation supporting career members working in the field of endocrinology. Working with experts in this field, along with pharmaceutical companies, they regularly host events popular with early-career members looking for jobs and new oppurtunities.

The problem that BioScientifica face is that it is often difficult to start conversation between the early-career members and experts at their events. The solution they wish us to implement aims to bridge this gap and act as an icebreaker between the two parties.

Breadcrumbs is an Android application we shall develop to solve this problem. It aims to gamify the event space for early-career members through a trail of 'breadcrumbs' consisting of multiple-choice questions and hints, with the trail ending with a meeting with one of the expert members.

A system shall be developed along side the game to allow the experts and event managers to both view how the game's users are progressing, but also allow the contents of the breadcrumbs to be customised. This should allow the application to be easily modified for the numerous events BioScientifica run throughout the year.

Through searching the venue for physical questions and clues to help find and  answer them, whilst receiving a scoring incentives, the Breadcrumbs application shall gamify the event experience for early-career members. This, along with experts being able to view their answers as they progress, should act as a starting point for conversation for their meeting allowing a better connection to be made.

---

## Requirements

### System Stakeholders

> System stakeholders and their role in the breadcrumbs project.

| Stakeholder | Role |
| ----------- | ---- | 
| Bioscientifica | Bioscientifica Ltd is the commercial subsidiary of the Society for Endocrinology and provides publishing, events, and association management services to biomedical societies, and to the pharmaceutical industry. |
| Society for Endocrinology | The Society for Endocrinology is an international membership organisation and registered charity representing scientists, clinicians and nurses who work with hormones. |
| Exhibitors | The companies who run stands at these events are where a lot of the funding comes from. They are usually pharmaceutical and medical equipment companies who will invest more money in the event the more participation their stand gets. |
| Experts | Assigned experts in the field that the quiz covers, who will monitor the users' answers and after a user has finished the quiz they will call them for a discussion. They will receive questions from the users that have acquired a specific hint and answer them accordingly. |
| Early-career members | Students and early careers clinicians at the events taking part in the breadcrumb trail. Our Application's core design will be oriented towards them as they will be the main users. |
| Activity managers | They will be organising the groups of users, giving out tablets with the application, explaining how the application works and possibly setting up the application before the events (setting up questions and hints, their locations and the map) |
| Other event attendees | There will be other people at the events who will not be taking part in the "Breadcrumb" trail quiz. We must ensure that despite the fact they won't be using it, our application should not get in their way. |
  
### Use-Case Diagram

> Use-case diagram for the interaction of actors in the Breadcrumbs system.

![](https://i.imgur.com/EeSs3VU.png)

#### Use-case goal

> Our main goal is the discussion between the Android application user and the expert which can be can be seen as a set of further goals. 

Question and hint functionalities of the application allow the expert to be well introduced to the knowledge and expertise of the user in a given subject through progression of users through the breadcrumb trail within the Android application. These functionalities are achieved by the user scanning the QR codes around the venue which are pin-pointed on the in-app map. 

The Breadcrumb trail allows for a smoother and enjoyable experience while undergoing this quiz and sets a relaxed atmosphere for the meeting with the expert to follow. The discussion is achieved through a meeting call once the quiz has been completed, thus informing both parties about their availability and location for the meeting; both inside the Android and web app.

##### Goals
1. **Navigation:** The user logs into the Android application, observes the given map and searches for a QR code in the designated areas. 
1. **Scanning:** Upon finding a code, they scan it and if they've found a code related to the current question, 
1. **Questions and hints:** they get either that question or a hint related to it (depending on which QR code they've found). 
1. **Point system:** They can proceed to answer the question and get points, or read a hint and get more information about the current unanswered question. After that the map updates and they should proceed to the next area to search for QR codes. 
1. **Meeting:** This continues until all questions have been answered, after which a meeting is scheduled between the user and an expert and they are both guided toward a location for their discussion to take place.

##### Alternative flow

- When a user finds a QR code they wish to scan, they may optionally input a code manually (located under the QR code) which will correspond to the same question (or hint) as the QR code. This is the same step as scanning but it ensures that there is a reliable way to transition between the search and the question / hint, should the QR code scanning functionality fail.

##### Exceptional flow

- When a user is unable to find the QR code for the current question the flow of the quiz is interrupted and cannot continue to its goal. There is no other way for the Quiz to end but to have all Questions answered, and so the goal is not reachable.

- If a user completes the last question and an activity manager has not assigned them an expert to have a meeting with at this time, they will not be notified of where to go and so will not be able to acheive the main goal of having a discussion with an expert.

### Main Goal

Through use of an application, gamify an event space by providing a trail of interactive breadcrumb targets relating to hints and questions to be found throughout the venue. The trail should engage early-career members in finding breadcrumbs, using nearby hints to help them find and answer questions; with the trail leading to an expert where answers shall be discussed, acting as an icebreaker for further discussion.

#### Functional requirements

##### Breadcrumb Trail

> Functional requirements of the breadcrumb trail and its related targets.

| Requirement | Priority | Description |
| ----------- | -------- | ----------- |
| Visible breadcrumbs | Must | It must be possible to add highly visible breadcrumb targets to printed material. |
| Targests relate to breadcrumbs | Must | Each target must uniquely identify a breadcrumb. |
| Ordered breadcrumbs | Must | Users must only be able to scan the breadcrumb for their curent question or its associated hints. |

##### Breadcrumbs Android Application

> Functional requirements of the Android application which will provide the gamification of the breadcrumb trail.

| Requirement | Priority | Description |
| ----------- | -------- | ----------- |
| Users have accounts | Must | Users must be able to log into an account so their breadcrumb data can be viewed by experts. |
| Interactive breadcrumbs | Must | The application Must be able to interact with the breadcrumb targets, e.g. via QR codes. |
| Multiple choice answers | Must | When a question's breadcrumb target is scanned it must display the associated question. |
| Display next breadcrumb | Must | When a question is answered correctly the location of the next breadcrumb must be shown to the user.
| Disable wrong answers | Must | If the wrong answer is chosen then it must grey it out and not allow it to be selected again. |
| Leads to a expert | Must | After the final question is answered correctly the user must be directed to an expert. |
| Scoring | Should | The user should recieve points for answering questions which are added to their score. |
| Dynamic scoring | Should | If a takes multiple attempts at a question they should receive less points. |
| Breadcrumbs have  codes | Should | Breadcrumb targets should have codes that can be inputted should QR codes not scan. |
| Media breadcurmbs | Should | Other media other than text should be able to be displayed in a breadcrumb. |
| Pre-loading | Should | The application should be able to be downloaded onto tablets before the event begins. |
| Map | Should | Current question and hint breadcrumb locations should be shown on a map. |
| Search areas | Could | The question beadcrumbs could be displayed as search areas rather than exact locations. |
| Re-usable targets | Could | Breadcrumb targets could be reusable - new targets wouldn't need to be generated each time. |
| Hint breadcrumbs | Could | Other breadcrumb targets could be found with information to help answer related question. |
| Hints help find questions | Could | Finding hint breadcrumbs could reduce the size of the question search areas. |
| Link to abstract | Could | Breadcrumbs could link to lectures or abstracts relating to the current question. |
| Augmented reality breadcrumbs | Could | Breadcrumbs targets could display 3D models related to the question. |

##### Breadcrumbs Web Application

> Functional requirements for the web application, used by the event's experts to view early-career member's breadcrumb trail data and activity managers to changes to the breadcrumb trail itself. 

| Requirement | Priority | Description |
| ----------- | -------- | ----------- |
| View user's data | Must | Experts must be able to view early-career member's breadcrumb trail data. |
| Experts have accounts | Must | Experts should have individual login details they can use to log in to the web app. |
| Add early career-members | Must | Activity managers must be able to add early-career member accounts to the system. |
| Add experts and event managers | Must | Activity managers must be able to add experts and early career members to the system. |
| Edit and remove accounts | Must | Activity managers must be able to edit and remove early career member, expert and activity manager accounts. |
| Admin access | Must | Only event managers must have admin access allowing them access to the management page. |
| Meetings with early-career members | Should | Experts should be able to see the meetings they have with early-career members. |
| Different quizes | Should | Activity managers should be able to change the contents of the breadcrumbs. |
| Group players | Should | Activity managers should be able to group players together to use one account. |
| Add media to breadcrumbs | Should | Should be able to add media such as images to breadcrumbs. |
| Questions have hints | Could | Should be able to add hints to questions which appear on the in-game map. |
| Different venues | Could | The venue map could be changed so that the system works at different venues. |
| Breadcrumb locations | Could | The locations of breadcrumbs could be moved and this would update the in-game map. |
| Meeting locations | Could | Event managers could change the location of meetings which will update the in-game map. |
| Notifications | Could | The experts could be alerted if an early-career member they have a meeting with is nearing the end of the breadcrumb trail. |

#### Non-Functional Requirements

> Non-functional requirements of the Breadcrumbs system.

| Requirement | Priority | Description |
| ----------- | -------- | ----------- |
| Security | Must | The system and its data must be secure and not allow an unauthorised user to access ant part without more effort than worth. |
| Web usability | Must | Nescesarry features should be understood in under 30 minutes with little to no guidence outside of the user guide. |
| Android usability | Must |Nescesarry features should be understood in under 10 minutes with little to no guidence outside of the help system.  |
| Reliability | Must | Data failures should occur less than once per hour as invalid data will effect outcomes of meetings and validation steps must be taken to insure invalid data is not stored in the database. |
| Web availabiity | Must | The web application must be deployed to a domain and each update should be integrated within 5 minutes.
| Android availabiity | Should | The Android application should be able to be installed on an Android device in under a minute. |
| Modifiability | Should | Aspects of the breadcrumb trail should be able to be edited by an events manager in under a minute. |
| Android performance | Should | When transfering data over the network such as map images, the Android application should not wait for more than 5 seconds for a response. |
| Web performance | Should | When transfering data over the network such as adding a new map, the Android application should not wait for more than 20 seconds for a response, smaller transactions such as getting upcomming meetings should not wait for more than 5 seconds. |
| Capacity | Should | The system should be able to handle over 50 connected Android devices without error.
| High capacity | Could | The system could be able to handle over 500 connected Android devices without error. |

### Completeness

Early career members will be interacting with the quiz by physically finding hints around the venue in order to hunt down and answer the questions whilst the experts will be observing and assessing their progress throughout the 'Breadcrumb trail'. Having the early career members search for the 'Breadcrumbs' around the venue and scan them through the Android application will gamify the experience prior to their discussion with the experts. This will act as an icebreaker, allowing for a more relaxing meeting, making the experience more impactful and creating a stronger connection between both parties.

---

## Object Oriented Design

### System Architecture

![](https://i.imgur.com/tdMVGcp.png)
For our product, we used a virtual machine hosted on Oracle servers which interacted with the database, which was hosted on another server. The web server interacts with the database server via the use of SQL Queries implemented using Java's Prepared Statements. As the admins would need to create questions as well as add/delete users, this was to be done via a web GUI so that this could easily be done on a laptop. the user would then have access to the relevant data e.g the questions they need to answer via a REST API implemented on the server. Retrofit was used to handle these calls on the phone app.
#### Static UML Design

This is our updated UML Diagram. As we began to gain a clearer understanding as to what to do for the project, several classes were added and some were removed. For example the AcivityManager class was retooled to simply be an expert with an admin role.
![](https://i.imgur.com/UMqQBfD.png)

#### Object Analysis

| Object | Description |
| ----------- | ---- |
| User | This object holds the data of the app user. The loginCode is needed so that a user can access the app. We decided not to use a password since it’s a one time use and the codes would be handed out on the day. | 
| Expert | These are the pople who the students meet up with after the event. Some experts have admin roles which gives them greater privileges on the website. Each expert also has the ability to create a quiz that will be taken on the day. |
| Role | Each expert has a user role. Admins are also given an extra admin role. This is primarily used to give admins exclusive access to select pages |
| Quiz | Each quiz is composed of 8 questions. This will be taken by many users on the day of the event. |
| Question | Each question is composed of 4 choices, with only one being the correct answer. each queston also has 2 hints. The variables x_coord and y_coord refer to the x and y coordinates on the map. This is so that the we know where the question icon should be drawn on the map. The code which the Hint class is used because not all phones support QR code scanning so an alternative is needed.
| Choice | This holds the data for a specific question’s option.
| Hint | Each question can have up to 2 hints which the user can scan help them answer the current question. x_coord and y_coord are repeated here as hints also need to be drawn on the map as well as the code in case a phone cannot scan QR codes. Hints may also hold images in and do so with the 'picture' Blob (Binary large object). |
| Attempt | Every time a user answers a question i.e selects a choice, an attempt is made. An attempt is composed of a user and a choice and is collected so we can see the order that a user answered a question. |
| Map | As the event is taking place indoors in a relatively small space, GPS would prove difficult so we had to circumvent this problem. We decided to use a collection of images with the hints and questions being drawn based on the coordinates that were supplied. |
| Meeting | At the end of an event a User meets up with an expert at a specific time and location to discuss the quiz that they took. This object stores that data. |

<!-- * User: This object holds the data of the app user. The loginCode is needed so that a user can access the app. We decided not to use a password since it's a one time use and the codes would be handed out on the day.
* Expert: These are the pople who the students meet up with after the event.Some experts have admin roles which gives them greater priveleges on the website.Each expert also has the ability to create a quiz that will be taken on the day
* Role: each expert has a user role. These are used to sort out admin privileges with admins being given an extra admin role.
* Quiz: Each quiz is composed of 8 questions. This will be taken by many users on the day of the event.
* Question: each question is composed of 4 choices, with only one being the correct answer. each queston also has 2 hints. The variables x_coord and y_coord refer to the x and y coordinates on the map. This is so that the we know where the question icon should be drawn on the map. The code which the Hint class is used because not all phones support QR code scanning so an alternative is needed.
* Choice: this holds the data for a specific question's option
* Hint: each question has 2 hints which the user can scan in order to gain extra points as well as to help them if they get stuck. x_coord and y_coord are repeated here as hints also need to be drawn on the map as well as the code in case a phone cannot scan QR codes. As a hint could also be an image, the Blob data type was used tro store it. Blob stands for 
* Attempt: Every time a user answers a question i.e selects a choice, an attempt is made. An attempt is composed of a user and a choice and is collected so we can see the order that a user answered a question.
* Map: As the event is taking place indoors in a relatively small space, gps would prove difficult so we had to circumvent this problem. We decided to use a collection of images with the hints and questions being drawn based on the coordinates that were supplied.
* Meeting: At the end of an event a User meets up with an expert at a specific time and location to discuss the quiz that they took. This object stores that data
 -->
 
#### Dynamic UML Design

![](https://i.imgur.com/c38CSdW.png)

Above is the flow chart of how the user is expected to use the app. They will first log in via the code given to them. Afterwards, they will be shown a main menu. From here, they could choose to look at the help page or dive straight into the game. They will then search the area for QR codes. If it's a hint, the search area is minimised. When they eventually find the question and answer it, they repeat this process until they've answered all the questions. From there, they will be prompted to attend their meeting with the assigned expert where they discuss how well they fared. The app would mainly be making use of the web api to fetch and the required data e.g getting the user via the code, updating the score, adding an attempt,etc.

### Reflection

Our modelling choices were very sound. It made for a much sleeker design and made it easier to extend functionality such as adding new API calls or additional SQL queries. This model gave us a better understanding of the model-view-controller design as all our data accessing was separate from the controllers. However, the biggest flaw we faced was securing our API. We could do this via generating API keys to hand them only to the admins . Also it would have been easier as well as faster if we had also hosted the database onto the vm. This choice was partly made because we set up the database way before we set up the deployment vm and it would've hard to port all that data onto the vm. However, this gives us the added benefit of the fact that if our web server were to go down, all the data is still secured.

---

## Testing and Development

### Testing Strategy

We had 4 core functionalities which required extensive testing. These included scanning the QR codes, map generation and rendering, the quiz application and administration via CRUD operations. Our System Architecture was also nicely divided into 3 key components:
1. The Database
2. The Web
3. The User Interface

Testing these components was necessary for us to assess the robustness of the functionalities and allowed us to incorporate back and front-end testing via unit and integration tests.

#### Data Layer

We used Mockito to test that the SQL Queries were written properly. This was because we did not want these tests to alter the actual database. The connection to the database was mocked and instead connected to a volatile in-memory database created specifically for testing. This made it easier to spot syntax errors mainly with the queries themselves as well as identify if anything wasn't being received properly. However, there was a slight caveat in this approach. We faced problems using JPA earlier on  so we couldn't have the database be automatically created by looking at the classes. This meant that we had to write a Drop-Create Script which mimicked the actual database. The probem arose when columns which were added to the real database weren't updated in the Drop Create Script so this had to be manually changed which decreased our productivity. Testing this layer allowed us to check that the CRUD operations needed for the admins was working smoothly.

#### Web layer

Our web layer was tested using Spring Boot's Junit runner which allowed us to mock the MVC and check that the right status responses were received. This made it easy to check that every page was loading via testing the GET requests, which was necessary so that the web pages wouldn't unexpectedly crash when the experts are using them. Tests were also made to check that accessing a page without logging in redirected you and also test that only admins could access the management page. The problem we faced was that as the database was hosted on another server, testing the web server actually connected it to the database deployment server. As a result we could only test GET requests which was very useful but limited the scope of what we could actually test. Luckily, as the data layer was tested rigorously, this wasn't too big of an issue because the API calls simply acted as a wrapper to the data layer calls. It also helped to serve as Integration testing between the web and data components.

#### User Interface

Testing some parts of the Android application and the web via unit tests was difficult because the api had already been tested, the biggest issues were to do with the UI. This had to be done manually as human input was required to make sure everything looked alright. This hampered our productivity. For the Android Application, we looked into Espresso and decided that this could help solve our problem. However functions such as QR code scanning had to be tested via manually with a textbox which showed the url the QR code scanned.

---

## Evaluation

For our evaluation, we did not have direct access to the target demographic of our product. Luckily, we managed to arrange this through our client. Every 2 weeeks, he conducted on-site user tests and give us the feedback. This feedback focused on look, feel and functionality which helped us to detect bugs such as the app not working at first on older versions of Android as well as add features that we overlooked such as not all phones being able to support QR code scanning. Messaging via LinkedIn was used to obtain real-time feedback.

We identified that a simple way of adding in targets (the location of a hint/question) was needed as we couldn't use conventional GPS technology. To solve this problem, we came up with the idea of admins being able to enter in coordinates on the venue map which will be uploaded onto the database.

This form of evaluation had its positives and negatives. Because we were not physically there, we were not able to chime in which could've corrupted the feedback. Leaving the end users to do it themselves allowed for more honest feedback. Doing it over messaging also allowed for a much quicker deployment turnover as we wouldn't have been able to get bi-weekly feedback as quickly if we had to travel there and back.

However, the downside of this is that we might not have been able to get more detailed feedback (e.g what a specific person thought) as it had all been collated. Also most of this data was collected on rough notes, a method which was suitable for our team of 4 but might not have been able to scale if we were a larger group. A more organised way of acting on this feedback might have been better.
