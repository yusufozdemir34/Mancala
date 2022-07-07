@author Yusuf OZDEMIR

- There is an object modelling diagram, an architecture diagram and a coverage diagram in the repository. It is strongly recommended that you look at these before checking the codes.

**Tech Stack**

- Java 				                    - backend

- JSP (Java Server Pages)				- frontend

- JUNIT				                    - TDD

- Spring Boot			

- Spring Data JPA		                - ORM - Template Pattern

- Eclipselink			                - ORM

- H2DB				                    - database

- Liquibase			                    - database schema changes

- Mapstruct			                    - mappings 

- Maven
- slf4j-api			                    - logging
- lombok				                - minimize code
- swagger	
- tomcat	

Mangala is a game played with 2 people.

Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits .
Rules

1. Game Play
The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. This can be repeated several times before it's the other player's turn.
Capturing Stones

2. During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.
The Game Ends
3. The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit. The winner of the game is the player who has the most stones in his big pit.
