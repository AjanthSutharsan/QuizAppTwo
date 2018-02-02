import javafx.beans.property.SimpleStringProperty

data class Question(val questionID: Int, val title: String, val correctAnswer: String, val incorrectAnswers: List<String>, val quizID: Int)
data class Quiz(val quizID: Int, val quizTitle: String) { override fun toString(): String = quizTitle }
data class Score(val scoreID: Int, val quizID: Int)
data class User(val userID: Int, val firstName: String, val lastName: String, val password: String)

class ScoreTableData (private val userID: Int, private val firstName: String, private val lastName: String, private val score: Int)
{
    private val _userID = SimpleStringProperty(userID.toString())
    private val _firstName = SimpleStringProperty(firstName)
    private val _lastName = SimpleStringProperty(lastName)
    private val _score = SimpleStringProperty(score.toString())

    fun getUserID() = _userID.get()
    fun getFirstName() = _firstName.get()
    fun getLastName() = _lastName.get()
    fun getScore() = _score.get()
}