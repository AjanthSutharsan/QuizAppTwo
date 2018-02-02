import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

class QuestionView
{
    companion object
    {
        private var runningTotal = 0

        fun show(questionIndex: Int, questions: ArrayList<Question>, user: User, quizID: Int)
        {
            val question = questions[questionIndex]

            val stage = Stage()
            val hbox = HBox()
            hbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox()
            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, rightVBox)

            // user label
            val userLabel = TextArea("ID: 62761 \nName: Lelouch Lamperouge")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            leftVBox.children.add(userLabel)

            // question number
            val questionNumberLabel = TextField("Question #${questionIndex + 1}")
            leftVBox.children.add(questionNumberLabel)


            // question label
            val questionTitle = TextField(question.title)
            leftVBox.children.add(questionTitle)

            // guidance
            val guidanceText = TextArea("REMEMBER: You cannot come back to a question, so be careful! \n\nTo move to the next question, either double click an answer, or click the answer once and press `next`.")
            leftVBox.children.add(guidanceText)

            // answers + randomising
            var answerNames = ArrayList<String>() + question.correctAnswer
            question.incorrectAnswers.forEach { answerNames += it }
            Collections.shuffle(answerNames)

            fun showNextQuestion(isCorrect: Boolean)
            {
                if (isCorrect) runningTotal++
                if (questionIndex + 1 < questions.size) // if there is a next question to show
                {
                    show(questionIndex + 1, questions, user, quizID)
                    stage.close()
                }
                else { FinishedPlaying.show(user, runningTotal, quizID); stage.close() }
            }

            var answerCorrect = false
            var viewQuestionNext = false

            answerNames.forEach { answer
            ->
                val answerButton = Button(answer)
                answerButton.setOnAction {
                    answerCorrect = answer == question.correctAnswer

                    if (viewQuestionNext) showNextQuestion(answerCorrect)
                    viewQuestionNext = true
                }
                rightVBox.children.add(answerButton)
            }

            // next button
            val nextButton = Button("Next")
            nextButton.setOnAction {
                println(answerCorrect)
                if (viewQuestionNext) showNextQuestion(answerCorrect)
                else Alert(Alert.AlertType.ERROR, "You haven't submitted an answer yet").showAndWait()
            }
            rightVBox.children.add(nextButton)

            stage.show()
        }


    }
}