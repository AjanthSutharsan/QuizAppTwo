import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class FinishedMaking
{
    companion object
    {
        fun show()
        {
            val stage = Stage()
            val vbox = VBox()
            val scene = Scene(vbox)
            stage.scene = scene

            // image
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 100.1
            image.fitWidth = 100.1
            vbox.children.add(image)

            // explanatory label
            val label = TextArea("You have finished making your quiz! \nWould you like to return to the main menu?")
            vbox.children.add(label)

            // yes/no hbox
            val hbox = HBox()
            vbox.children.add(hbox)

            // yes button
            val yesButton = Button("Yes!")
            yesButton.setOnAction {
                MainMenu.show()
                stage.close()
            }
            vbox.children.add(yesButton)

            // no button
            val noButton = Button("No!")
            noButton.setOnAction { stage.close() }
            vbox.children.add(noButton)

            stage.show()
        }
    }
}