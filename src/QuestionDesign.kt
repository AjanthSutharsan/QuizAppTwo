import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class QuestionDesign
{
    companion object
    {
        fun show(questionNumber: Int, quizID: Int, user: User)
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val hbox = HBox()
            hbox.padding = Insets(15.0, 15.0, 100.0, 15.0)
            hbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox()
            val dividerHBox = HBox()
            val divider = VBox()
            divider.children.add(dividerHBox)
            (0 until 7).forEach { dividerHBox.children.add(Label("       ")) }

            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, divider, rightVBox)

            // user label
            val userLabel = TextArea("ID: ${user.userID} \nName: ${user.firstName} ${user.lastName}")
            userLabel.isEditable = false
            userLabel.setMinSize(110.0, 20.0)
            leftVBox.children.add(userLabel)

            // question name
            (0 until 7).forEach { leftVBox.children.add(Label("")) }
            val questionName = TextField("")
            questionName.promptText = "Type question here"
            leftVBox.children.add(questionName)

            // guidance
            (0 until 9).forEach { leftVBox.children.add(Label("")) }
            val guidanceText = TextArea("Ensure you don't make any spelling mistakes! \n\nREMEMBER: This is NOT the order in which \nthe answers will be show to the users.")
            guidanceText.isEditable = false
            leftVBox.children.add(guidanceText)

            // question number
            val questionNumberLabel = TextField("Question #$questionNumber")
            rightVBox.children.add(questionNumberLabel)
            (0 until 2).forEach { rightVBox.children.add(Label("")) }

            val answerTexts = ArrayList<TextArea>()
            (0 until 4).forEach {
                // answer header label
                val answerLabel = TextField("Answer ${it + 1} (${if (it != 3) "in" else ""}correct):")
                rightVBox.children.add(answerLabel)

                // answer text
                val answerText = TextArea("\n\n")
                rightVBox.children.add(answerText)
                (0 until 2).forEach { rightVBox.children.add(Label("")) }
                answerTexts += answerText
            }

            val nextButton = Button("Next!")
            nextButton.setMinSize(110.0, 60.0)
            nextButton.setOnAction { _
            ->
                // saving question
                val question = Question(-1, questionName.text, answerTexts[3].text, arrayListOf(answerTexts[0].text, answerTexts[1].text, answerTexts[2].text), quizID)
                DBService.writeOneQuestion(question)

                if (questionNumber != 10) show(questionNumber + 1, quizID, user)
                else FinishedMaking.show(user)

                stage.close()
            }
            val nextButtonHBox = HBox()
            rightVBox.children.add(nextButtonHBox)
            (0 until 9).forEach{nextButtonHBox.children.add(Label("     "))}
            nextButtonHBox.children.add(nextButton)

            stage.show()
        }
    }
}