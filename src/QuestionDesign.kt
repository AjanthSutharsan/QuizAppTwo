import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class QuestionDesign
{
    companion object
    {
        fun show(questionNumber: Int, quizID: Int)
        {
            val stage = Stage()
            val hbox = HBox()
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox()
            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, rightVBox)

            // user label
            val userLabel = TextArea("ID: 62761\nName: Lelouch Lamperouge")
            leftVBox.children.add(userLabel)

            // question name
            val questionName = TextField("Question name: ")
            leftVBox.children.add(questionName)

            // guidance
            val guidanceText = TextArea("Ensure you don't make any spelling mistakes!\n\nREMEMBER: This is NOT the order in which the answers will be show to the users.")
            leftVBox.children.add(guidanceText)

            // question number
            val questionNumberLabel = TextField("Question #$questionNumber")
            rightVBox.children.add(questionNumberLabel)

            val answerTexts = ArrayList<TextArea>()
            (0 until 4).forEach {
                // answer header label
                val answerLabel = TextField("Answer ${it + 1} (${if (it != 3) "in" else ""}correct):")
                rightVBox.children.add(answerLabel)

                // answer text
                val answerText = TextArea("\n\n")
                rightVBox.children.add(answerText)
                answerTexts += answerText
            }

            val nextButton = Button("Next!")
            nextButton.setOnAction { _
            ->
                // saving question
                val question = Question(-1, questionName.text, answerTexts[3].text, arrayListOf(answerTexts[0].text, answerTexts[1].text, answerTexts[2].text), quizID)
                DBService.writeOneQuestion(question)

                if (questionNumber != 10) show(questionNumber + 1, quizID)
                else FinishedMaking.show()

                stage.close()
            }
            nextButton.snapPositionY(50.1)
            rightVBox.children.add(nextButton)

            stage.show()
        }
    }
}