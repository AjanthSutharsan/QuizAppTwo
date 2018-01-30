data class Question(val questionID: Int, val title: String, val correctAnswer: String, val incorrectAnswers: ArrayList<String>, val quizID: Int)
data class Quiz(val quizID: Int, val quizTitle: String)
data class Score(val scoreID: Int, val quizID: Int)
data class User(val userID: Int, val firstName: String, val lastName: String, val password: String)