import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
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

            val vbox = VBox()
            vbox.padding = Insets(15.0, 15.0, 100.0, 15.0)
            vbox.style = MainMenu.BACKGROUND_COLOUR_STYLE

            val topHBox = HBox()
            val middleHBox = HBox()
            val bottomHBox = HBox()

            vbox.children.addAll(topHBox)//, middleHBox) ,bottomHBox)
            val scene = Scene(vbox)
            stage.scene = scene

            // user label
            val userLabel = TextArea("ID: ${user.userID}\nName: ${user.firstName + " " + user.lastName}")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            topHBox.children.add(userLabel)

            // image
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.1
            image.fitWidth = 250.1
            (0 until 10).forEach { topHBox.children.add(Label("      ")) }
            topHBox.children.add(image)

            // quiz title const label
            val quizTitleLabel = TextField("Quiz title: \t")
            quizTitleLabel.isEditable = false
            (0 until 17).forEach { middleHBox.children.add(Label("      ")) }

            (0 until 2).forEach { vbox.children.add(Label("      ")) }
            middleHBox.children.add(quizTitleLabel)
            vbox.children.add(middleHBox)

            // quiz title enter
            val quizName = TextField()
            quizName.promptText = "Enter the quiz title here"
            middleHBox.children.add(quizName)

            // next button
            val nextButton = Button("Next!")
            nextButton.setOnAction {
                DBService.writeQuizTitle(Quiz(-1, quizName.text))
                QuestionDesign.show(1, DBService.nextQuizID(), user); stage.close()
            }

            (0 until 23).forEach { bottomHBox.children.add(Label("      ")) }
            bottomHBox.children.add(nextButton)
            (0 until 5).forEach { vbox.children.add(Label("      ")) }
            vbox.children.add(bottomHBox)

            // finally...
            stage.show()
        }
    }
}