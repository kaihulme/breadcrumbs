Object Oriented Design
======================

System Architecture
-------------------

![](https://i.imgur.com/tdMVGcp.png)
For our product, we used a virtual machine hosted on Oracle servers which interacted with the database, which was hosted on another server. The web server interacts with the database server via the use of SQL Queries implemented using Java's Prepared Statements. As the admins would need to create questions as well as add/delete users, this was to be done via a web GUI so that this could easily be done on a laptop. the user would then have access to the relevant data e.g the questions they need to answer via a REST API implemented on the server. Retrofit was used to handle these calls on the phone app.


### Static UML Design

This is our updated UML Diagram. As we began to gain a clearer understanding as to what to do for the project, several classes were added and some were removed. For example the AcivityManager class was retooled to simply be an expert with an admin role.
![](https://i.imgur.com/UMqQBfD.png)

### Object Analysis

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
 
### Dynamic UML Design

![](https://i.imgur.com/c38CSdW.png)

Above is the flow chart of how the user is expected to use the app. They will first log in via the code given to them. Afterwards, they will be shown a main menu. From here, they could choose to look at the help page or dive straight into the game. They will then search the area for QR codes. If it's a hint, the search area is minimised. When they eventually find the question and answer it, they repeat this process until they've answered all the questions. From there, they will be prompted to attend their meeting with the assigned expert where they discuss how well they fared. The app would mainly be making use of the web api to fetch and the required data e.g getting the user via the code, updating the score, adding an attempt,etc.

---

Reflection
----------

Our modelling choices were very sound. It made for a much sleeker design and made it easier to extend functionality such as adding new API calls or additional SQL queries. This model gave us a better understanding of the model-view-controller design as all our data accessing was separate from the controllers. However, the biggest flaw we faced was securing our API. We could do this via generating API keys to hand them only to the admins . Also it would have been easier as well as faster if we had also hosted the database onto the vm. This choice was partly made because we set up the database way before we set up the deployment vm and it would've hard to port all that data onto the vm. However, this gives us the added benefit of the fact that if our web server were to go down, all the data is still secured.
