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
        fun show()
        {
            val stage = Stage()
            val hbox = HBox()
            hbox.padding = Insets(15.0, 15.0, 15.0, 15.0)
            val leftVBox = VBox()
            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, rightVBox)
            val scene = Scene(hbox)
            stage.scene = scene

            // user label
            val userLabel = TextArea("ID: 62761\nName: Lelouch Lamperouge")
            leftVBox.children.add(userLabel)

            // quiz title const label
            val quizTitleLabel = TextField("Quiz title: \t")
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
                QuestionDesign.show(1, DBService.nextQuizID()); stage.close()
            }
            rightVBox.children.add(nextButton)

            // finally...
            stage.show()
        }
    }
}