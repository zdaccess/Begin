# Team 01 — Java bootcamp
### Java FX & Sockets
*Takeaways: Today you will implement an actual client/server game with a full-scale interface.*

💡 [Tap here](https://new.oprosso.net/p/4cb31ec3f47a4596bc758ea1861fb624) **to leave your feedback on the project**. It's anonymous and will help our team make your educational experience better. We recommend completing the survey immediately after the project.

# Contents
1. [Chapter I](#chapter-i) \
	1.1. [Preamble](#preamble)
2. [Chapter II](#chapter-ii) \
	2.1. [General Rules](#general-rules)
3. [Chapter IV](#chapter-iv) \
	3.1. [Exercise 00 - Tanks!](#exercise-00-tanks)


# Chapter I
### Preamble

Minimum requirements for junior Java developers:
1. Java Core (Type System, OOP, Collections, IO/NIO, Reflection, Generics, Exceptions);
2. Maven;
3. JUnit;
4. Mockito;
5. SQL;
6. JDBC;
7. Spring Framework (Context, JDBC, MVC, AOP, Security, Data Jpa);
8. HTML/CSS/JS (JQuery);
9. Tomcat;
10. Servlets/JSP;
11. Freemarker;
12. REST API;
13. Postman, Curl, Swagger;
14. English;
      ...

![Types of Headache](misc/images/Types_of_headache.png)

# Chapter II
### General Rules
- Use this page as your only reference. Do not listen to rumors and speculations about how to prepare your solution.
- There is only one Java version for you, 1.8. Make sure you have the compiler and interpreter for this version installed on your machine.
- You can use the IDE to write and debug the source code.
- The code is more often read than written. Carefully read the [document](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf) where code formatting rules are given. When performing any task, make sure you follow the generally accepted [Oracle Standards](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html).
- Comments are not allowed in the source code of your solution. They make it difficult to read the code.
- Be aware of the permissions of your files and directories.
- Your solution must be in your GIT repository to be evaluated.
- Your solutions will be evaluated by your fellow bootcampers.
- You should not leave any files in your src directory other than those explicitly specified in the exercise instructions. It is recommended that you modify your .gitignore to avoid accidents.
- If you need accurate output in your programs, it is forbidden to display precalculated output instead of running the exercise correctly.
- Got a question? Ask your neighbor to the right. Otherwise, try your neighbor on the left.
- Your reference guide: peers / Internet / Google. And one more thing. For every question you have, there's an answer on Stackoverflow. Learn how to ask questions properly.
- Read the examples carefully. They may require things not specified in the subject.
- Use System.out for output.
- And may the Force be with you!
- Never leave for tomorrow what you can do today. ;)

# Chapter IV
### Exercise 00 — Tanks!
| Exercise 00: Tanks! | |
| ------ | ------ |
| Turn-in directory | ex00 |
| Files to turn-in | TanksClient-folder, TanksServer-folder |

JavaFX enables the creation of high-quality desktop applications. Despite the fact that JavaFX is not very popular in corporate development, this technology is used in a wide range of "private" solutions. JavaFX is also a good tool for exploring the mechanisms of the Java programming language.

Your goal is to implement a client/server game where the Socket server allows two JavaFX clients to play a tank game with each other. An example of the client-side appearance is shown below:

![tanks](misc/images/tanks.png)

So the game should allow two users to "drive" their tank and reduce the enemy's HP by shooting.

**Game Mechanics** :
1. A tank can only move horizontally by pressing the left and right arrow keys. Holding down the respective keys will result in a continuous movement in that direction.
2. A tank cannot move beyond the edge of the field.
3. Pressing the spacebar once will fire a shot. It is not possible to fire a series of shots by holding down the key.
4. Hitting the target will remove 5 HP from the enemy.
5. Both players start with 100 HP.
6. The player is always at the bottom of the screen, while the enemy is at the top.
7. Tanks can only move when both players are connected to the server.

**Additional requirements**:
- Interface must be able to connect to a specific server.
- When either player wins, a modal box should appear with the stats of shots, hits and misses.
- These stats are stored in a DBMS on the server.
- JavaFX client should have an executable jar archive that can be launched like a normal application (by clicking on the file).
- README.md file should be prepared with a set of instructions for application assembly and startup.
