import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class FinishedPlaying
{
    companion object
    {
        fun show(user: User, totalScore: Int, quizID: Int)
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val vbox = VBox()
            vbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(vbox)
            stage.scene = scene

            // image
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 100.1
            image.fitWidth = 100.1
            vbox.children.add(image)

            // explanatory label
            val label = TextArea("You have finished the quiz!\nYour score was $totalScore / 10 \nWould you like to return to the main menu?")
            vbox.children.add(label)


            // yes/no hbox
            val hbox = HBox()
            vbox.children.add(hbox)

            // yes button
            val yesButton = Button("Yes!")
            yesButton.setOnAction {
                MainMenu.show(user)
                stage.close()
            }
            vbox.children.add(yesButton)

            // no button
            val noButton = Button("No!")
            noButton.setOnAction { stage.close() }
            vbox.children.add(noButton)

            DBService.saveScoreToDatabase(user, quizID, totalScore)

            stage.show()
        }
    }
}