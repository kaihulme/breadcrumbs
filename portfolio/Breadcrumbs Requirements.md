Breadcrumbs Requirements
========================

System Stakeholders
-------------------

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

---
  
## Use-Case Diagram

> Use-case diagram for the interaction of actors in the Breadcrumbs system.

![](https://i.imgur.com/EeSs3VU.png)

### Use-case goal

> Our main goal is the discussion between the Android application user and the expert which can be can be seen as a set of further goals. 

Question and hint functionalities of the application allow the expert to be well introduced to the knowledge and expertise of the user in a given subject through progression of users through the breadcrumb trail within the Android application. These functionalities are achieved by the user scanning the QR codes around the venue which are pin-pointed on the in-app map. 

The Breadcrumb trail allows for a smoother and enjoyable experience while undergoing this quiz and sets a relaxed atmosphere for the meeting with the expert to follow. The discussion is achieved through a meeting call once the quiz has been completed, thus informing both parties about their availability and location for the meeting; both inside the Android and web app.

#### Goals
1. **Navigation:** The user logs into the Android application, observes the given map and searches for a QR code in the designated areas. 
1. **Scanning:** Upon finding a code, they scan it and if they've found a code related to the current question, 
1. **Questions and hints:** they get either that question or a hint related to it (depending on which QR code they've found). 
1. **Point system:** They can proceed to answer the question and get points, or read a hint and get more information about the current unanswered question. After that the map updates and they should proceed to the next area to search for QR codes. 
1. **Meeting:** This continues until all questions have been answered, after which a meeting is scheduled between the user and an expert and they are both guided toward a location for their discussion to take place.

#### Alternative flow

- When a user finds a QR code they wish to scan, they may optionally input a code manually (located under the QR code) which will correspond to the same question (or hint) as the QR code. This is the same step as scanning but it ensures that there is a reliable way to transition between the search and the question / hint, should the QR code scanning functionality fail.

#### Exceptional flow

- When a user is unable to find the QR code for the current question the flow of the quiz is interrupted and cannot continue to its goal. There is no other way for the Quiz to end but to have all Questions answered, and so the goal is not reachable.

- If a user completes the last question and an activity manager has not assigned them an expert to have a meeting with at this time, they will not be notified of where to go and so will not be able to acheive the main goal of having a discussion with an expert.

---

Main Goal
---------

Through use of an application, gamify an event space by providing a trail of interactive breadcrumb targets relating to hints and questions to be found throughout the venue. The trail should engage early-career members in finding breadcrumbs, using nearby hints to help them find and answer questions; with the trail leading to an expert where answers shall be discussed, acting as an icebreaker for further discussion.

### Functional requirements

#### Breadcrumb Trail

> Functional requirements of the breadcrumb trail and its related targets.

| Requirement | Priority | Description |
| ----------- | -------- | ----------- |
| Visible breadcrumbs | Must | It must be possible to add highly visible breadcrumb targets to printed material. |
| Targests relate to breadcrumbs | Must | Each target must uniquely identify a breadcrumb. |
| Ordered breadcrumbs | Must | Users must only be able to scan the breadcrumb for their curent question or its associated hints. |

#### Breadcrumbs Android Application

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

#### Breadcrumbs Web Application

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

### Non-Functional Requirements

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
 
---

Completeness
------------

Early career members will be interacting with the quiz by physically finding hints around the venue in order to hunt down and answer the questions whilst the experts will be observing and assessing their progress throughout the 'Breadcrumb trail'. Having the early career members search for the 'Breadcrumbs' around the venue and scan them through the Android application will gamify the experience prior to their discussion with the experts. This will act as an icebreaker, allowing for a more relaxing meeting, making the experience more impactful and creating a stronger connection between both parties.