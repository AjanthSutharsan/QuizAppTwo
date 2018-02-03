import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
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
            vbox.padding = Insets(15.0, 15.0, 100.0, 15.0)
            vbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(vbox)
            stage.scene = scene

            // image
            val imageHBox = HBox()
            (0 until 15).forEach { imageHBox.children.add(Label("       ")) }
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.1
            image.fitWidth = 250.1
            imageHBox.children.add(image)
            vbox.children.add(imageHBox)

            // explanatory label
            val explanatoryHBox = HBox()
            (0 until 14).forEach { explanatoryHBox.children.add(Label("       ")) }
            val label = TextArea("You have finished the quiz!\nYour score was $totalScore / 10 \nWould you like to return to the main menu?")
            label.setMaxSize(300.0, 80.0)
            explanatoryHBox.children.add(label)
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(explanatoryHBox)

            // yes/no hbox
            val yesNoHBox = HBox()
            (0 until 9).forEach { yesNoHBox.children.add(Label("       ")) }
            (0 until 1).forEach { vbox.children.add(Label("")) }
            vbox.children.add(yesNoHBox)

            // yes button
            val yesButton = Button("Yes!")
            yesButton.setMinSize(250.0, 120.0)
            yesButton.setOnAction {
                MainMenu.show(user)
                stage.close()
            }
            yesNoHBox.children.add(yesButton)
            (0 until 1).forEach { yesNoHBox.children.add(Label("        ")) }

            // no button
            val noButton = Button("No!")
            noButton.setMinSize(250.0, 120.0)
            noButton.setOnAction { stage.close() }
            yesNoHBox.children.add(noButton)

            //DBService.saveScoreToDatabase(user, quizID, totalScore)

            stage.show()
        }
    }
}