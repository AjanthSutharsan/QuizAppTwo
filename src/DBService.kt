class DBService
{
    companion object
    {
        private val connection: DBConn = DBConn()

        fun close() = connection.close()

        fun writeQuizTitle(quiz: Quiz) = connection.runQuery("INSERT INTO Quiz (quizTitle) VALUES ('${quiz.quizTitle}');")

        fun writeOneQuestion(question: Question)
        {
            var query = "INSERT INTO Question (question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, quizID) VALUES('${question.title.trim()}', '${question.correctAnswer.trim()}', "
            question.incorrectAnswers.forEach { answer -> query += "'${answer.trim()}', " }
            query += question.quizID.toString() + ");"

            connection.runQuery(query)
        }

        fun nextQuizID(): Int
        {
            val quizIDs = connection.linesFromQuery("SELECT quizID FROM Quiz;")
            if (quizIDs.isNotEmpty()) return quizIDs.last().toInt()
            return 1
        }
    }
}