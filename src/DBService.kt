import java.util.*
import kotlin.collections.ArrayList

class DBService
{
    companion object
    {
        private val connection = DBConn()

        fun close() = connection.close()

        fun writeQuizTitle(quiz: Quiz) = connection.runQuery("INSERT INTO Quiz (quizTitle) VALUES ('${quiz.quizTitle}');")

        fun writeOneQuestion(question: Question)
        {
            var query = "INSERT INTO Question (question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, quizID) VALUES('${question.title.trim()}', '${question.correctAnswer.trim()}', "
            question.incorrectAnswers.forEach { answer -> query += "'${answer.trim()}', " }
            query += question.quizID.toString() + ");"

            connection.runQuery(query)
        }

        fun getFirstLastName(userID: Int) =  connection.linesFromQuery("SELECT firstName, lastName FROM Users WHERE userID = $userID;").toList()[0]
        fun saveScoreToDatabase(user: User, quizID: Int, finalScore: Int)
        {
            val query = "INSERT INTO Scores (userID, quizID, score) VALUES(${user.userID}, $quizID, $finalScore);"
            connection.runQuery(query)
        }

        fun nextQuizID(): Int
        {
            val quizIDs = connection.linesFromQuery("SELECT quizID FROM Quiz;")
            if (quizIDs.isNotEmpty()) return quizIDs.last().toInt()
            return 1
        }

        fun getUserFromID(userID: Int): User
        {
            val userData = connection.linesFromQuery("SELECT * FROM Users WHERE userID = $userID")[0].split("\t")
            return User(userData[0].toInt(), userData[1], userData[2], userData[3])
        }

        fun allQuizNames(): ArrayList<String>
        {
            val quizNames = ArrayList<String>()
            connection.linesFromQuery("SELECT quizTitle FROM Quiz;").forEach { quizNames += it }
            return quizNames
        }

        fun userExists(userID: Int, password: String) = connection.linesFromQuery("SELECT * FROM Users WHERE userID = $userID AND password = '$password';").isNotEmpty()

        fun getScoreData(quizID: Int): ArrayList<ScoreTableData>
        {
            val firstPass = ArrayList<ArrayList<String>>()
            connection.linesFromQuery("SELECT * FROM Scores WHERE quizID = $quizID ORDER BY score DESC;").forEach { firstPass += it.split("\t") as ArrayList }
            val scoreTableData = ArrayList<ScoreTableData>()
            firstPass.forEach {
                val userID = it[1].toInt()
                scoreTableData += ScoreTableData(
                        userID,
                        getFirstLastName(userID).split("\t")[0], // first name
                        getFirstLastName(userID).split("\t")[1], // last name
                        it[3].toInt()
                )
            }
            return scoreTableData
        }

        fun allSortedQuizzes() : ArrayList<Quiz>
        {
            val quizzes = ArrayList<Quiz>()
            connection.linesFromQuery("SELECT * FROM Quiz;").forEach { quizzes += Quiz(it.split("\t")[0].toInt(), it.split("\t")[1]) }
            Collections.sort(quizzes) { a: Quiz, b: Quiz -> a.quizTitle.compareTo(b.quizTitle) }
            return quizzes
        }

        fun questionsFromQuiz(quiz: Quiz): ArrayList<Question>
        {
            val questions = ArrayList<Question>()
            val allQuestionLines = connection.linesFromQuery("SELECT * FROM Question;")
            var i = 0; while (questions.size != 10)
            {
                val questionLineSplit = allQuestionLines[i].split("\t")
                if (questionLineSplit[6].toInt() == quiz.quizID)
                {
                    questions += Question(
                            questionLineSplit[0].toInt(),
                            questionLineSplit[1],
                            questionLineSplit[2],
                            questionLineSplit.subList(3, 6),
                            questionLineSplit[6].toInt()
                    )
                }
                i++
            }
            return questions
        }

        fun saveScore(user: User, quiz: Quiz, score: Score) // ViewQuestion needs to be passed the quiz object for it to be saved here.
        {

        }
    }
}