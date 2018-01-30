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

        fun show(questionIndex: Int, questions: ArrayList<Question>)
        {
            val question = questions[questionIndex]

            val stage = Stage()
            val hbox = HBox()
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox()
            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, rightVBox)

            // user label
            val userLabel = TextArea("ID: 62761 \nName: Lelouch Lamperouge")
            leftVBox.children.add(userLabel)

            // question number
            val questionNumberLabel = TextField("Question #$questionIndex")
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

            var viewQuestionNext = false
            var answerCorrect = false


            fun showNextQuestion()
            {
                if (answerCorrect) runningTotal++
                if (questionIndex + 1 != 10) show(questionIndex + 1, questions)
                stage.close()
            }

            answerNames.forEach {
                val answerButton = Button(it)
                answerButton.setOnAction {
                    answerCorrect = answerButton.text == question.correctAnswer
                    if (viewQuestionNext) showNextQuestion()
                    viewQuestionNext = true
                }
                rightVBox.children.add(answerButton)
            }

            // next button
            val nextButton = Button("Next")
            nextButton.setOnAction { if (viewQuestionNext) showNextQuestion() else Alert(Alert.AlertType.ERROR, "You haven't submitted an answer yet").showAndWait() }
            rightVBox.children.add(nextButton)
        }


    }
}