import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MakeQuiz
{
    companion object
    {
        fun show(user: User)
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val hbox = HBox()
            hbox.padding = Insets(15.0, 15.0, 15.0, 15.0)
            hbox.style = MainMenu.BACKGROUND_COLOUR_STYLE

            val leftVBox = VBox()
            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, rightVBox)
            val scene = Scene(hbox)
            stage.scene = scene

            // user label
            val userLabel = TextArea("ID: ${user.userID}\nName: ${user.firstName + " " + user.lastName}")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            leftVBox.children.add(userLabel)

            // quiz title const label
            val quizTitleLabel = TextField("Quiz title: \t")
            quizTitleLabel.isEditable = false
            quizTitleLabel.maxWidth = 45.0
            leftVBox.children.add(quizTitleLabel)

            // image
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 100.1
            image.fitWidth = 100.1
            rightVBox.children.add(image)

            // quiz title enter
            val quizName = TextField("(Enter the title here)")
            rightVBox.children.add(quizName)

            // next button
            val nextButton = Button("Next!")
            nextButton.setOnAction {
                DBService.writeQuizTitle(Quiz(-1, quizName.text))
                QuestionDesign.show(1, DBService.nextQuizID(), user); stage.close()
            }
            rightVBox.children.add(nextButton)

            // finally...
            stage.show()
        }
    }
}