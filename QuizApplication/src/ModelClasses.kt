data class Question(val questionID: Int, val title: String, val correctAnswer: String, val incorrectAnswers: List<String>, val quizID: Int)
data class Quiz(val quizID: Int, val quizTitle: String) { override fun toString(): String = quizTitle }
data class Score(val scoreID: Int, val quizID: Int)
data class User(val userID: Int, val firstName: String, val lastName: String, val password: String)