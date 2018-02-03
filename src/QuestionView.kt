import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
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
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false
            val hbox = HBox()
            hbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            hbox.padding = Insets(25.0, 15.0, 15.0, 15.0)
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox()

            val divider = VBox()
            val dividerHBox = HBox()
            divider.children.add(dividerHBox)
            (0 until 7).forEach { dividerHBox.children.add(Label("       ")) }

            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, divider, rightVBox)

            // user label
            val userLabel = TextArea("ID: ${user.userID} \nName: ${user.firstName} ${user.lastName}")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            leftVBox.children.add(userLabel)

            // question number
            val questionNumberLabel = TextField("Question #${questionIndex + 1}")
            (0 until 8).forEach { leftVBox.children.add(Label("")) }
            leftVBox.children.add(questionNumberLabel)

            // question label
            val questionTitle = TextField(question.title)
            leftVBox.children.add(questionTitle)

            // guidance
            val guidanceText = TextArea("REMEMBER: You cannot come back to a question, so be careful! \n \nTo move to the next question, either double click an answer, \n or click the answer once and press `next`.")
            guidanceText.setMaxSize(600.0, 100.0)
            guidanceText.isEditable = false
            (0 until 9).forEach { leftVBox.children.add(Label("")) }
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
                answerButton.setMinSize(250.0, 70.0)
                answerButton.setOnAction {
                    answerCorrect = answer == question.correctAnswer

                    if (viewQuestionNext) showNextQuestion(answerCorrect)
                    viewQuestionNext = true
                }
                rightVBox.children.add(answerButton)
                (0 until 2).forEach { rightVBox.children.add(Label("")) }
            }

            // next button
            val nextButton = Button("Next")
            nextButton.setMinSize(110.0, 60.0)
            nextButton.setOnAction {
                println(answerCorrect)
                if (viewQuestionNext) showNextQuestion(answerCorrect)
                else Alert(Alert.AlertType.ERROR, "You haven't submitted an answer yet").showAndWait()
            }
            val nextButtonHBox = HBox()
            rightVBox.children.add(nextButtonHBox)
            (0 until 4).forEach{nextButtonHBox.children.add(Label("     "))}
            nextButtonHBox.children.add(nextButton)

            stage.show()
        }


    }
}