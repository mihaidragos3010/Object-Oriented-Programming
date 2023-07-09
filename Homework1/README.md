
Mihai Dragos-Andrei
Grupa 322 CB

#Tema1.java
	In this class I check for every command if all agruments send in a correct format. If so in the next step I creat a required object
for each command and make the necessaty action. 
	In this class exist a clean method that delete all file and reset all IDs created in a previous test.

#User.java
	This class is formed by name (a String meanning the name of an user) and password (a String that mean password of an user).
	The main methods from this class are: createUser that write a user name and a password into a file split by "," and getMySolutions
that read from a file the user's score.

#Question.java
	This class is formed by: user(a User object meanning user that created this question), type (a String that mean type of question 
single/multiple), text (a String that mean the text of question), answers (a list with Answers object), nrQuestions (a static variable that
mean numbers of questions created in this test), ID (a integar that mean an unique code).
	In this class i have two builders: first used to creat question object, write on a file (createQuestion), increase nrQuestions 
and second used to call get data functions (getQuestion, getAllQuestion).

#Quizz.java 
	This class is formed by: user(a User object meanning user who creat this quizz), name (a String that mean name of this quizz), 
nrQuizz (a static integar meanning the number of quizz), questions (an array String that mean a list with question ID), ID (a integar 
that mean a unique number of quizz)
	In this class I have two builders: first used to creat quizz object, write on a file (createQuizz), increase nrQuizz
and second used to call get, delete and submit data functions (getQuizz, getAllQuizz, getQuizzDetails, deleteQuizz, submitQuizz).

#Check.java
	In this class I have all verification functions that use to check for every command if arguments are sended in a corect format.

#Answer.java
	This is a class who saved an answer data for a question's answer. It has these arguments: text (String mean text of a answer), 
value (Boolean variable that provided if this answer is correct/wrong), nrAnswers (an integar that mean numbers of answers), ID (an 
integar that mean a unique code for every answers for every quiestions).