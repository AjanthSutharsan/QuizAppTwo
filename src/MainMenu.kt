import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MainMenu
{
    companion object
    {
        fun show()
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            // set up
            val hbox = HBox()
            hbox.padding = Insets(15.0, 15.0, 15.0, 15.0)
            hbox.spacing = 100.0

            val scene = Scene(hbox)
            stage.scene = scene

            // left vbox
            val leftVBox = VBox()
            hbox.children.add(leftVBox)

            // middle vbox
            val middleVBox = VBox()
            hbox.children.add(middleVBox)

            // right vbox
            val rightVBox = VBox()
            hbox.children.add(rightVBox)

            // user label
            val userLabel = TextArea("ID: 62761\nName: Lelouch Lamperouge")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            leftVBox.children.add(userLabel)

            // make a quiz button

            // PADDING
            (0 until 22).forEach {
                val padding = Label("")
                leftVBox.children.add(padding)
            }
            val STYLE = "-fx-alignment: CENTER;"
            val makeQuizButton = Button("Make a quiz!")
            makeQuizButton.style = STYLE
            makeQuizButton.setMinSize(250.0, 150.0)
            makeQuizButton.setOnAction { MakeQuiz.show(); stage.close() }
            makeQuizButton.alignment = Pos.BOTTOM_LEFT
            leftVBox.children.add(makeQuizButton)

            // image
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.1
            image.fitWidth = 250.1
            middleVBox.children.add(image)

            //

            // play a quiz button

            // PADDING
            (0 until 10).forEach {
                val padding = Label("")
                middleVBox.children.add(padding)
            }
            val playQuizButton = Button("Play a quiz!")
            playQuizButton.style = STYLE
            playQuizButton.setMinSize(250.0, 150.0)
            middleVBox.children.add(playQuizButton)

            // log out hbox
            val padddingHBox = HBox()
            rightVBox.children.add(padddingHBox)

            // padding!!
            (0 until 4).forEach {
                val padding = Label("           ")
                padddingHBox.children.add(padding)
            }

            val logOutButton = Button("Log out")
            logOutButton.alignment = Pos.TOP_RIGHT
            logOutButton.style = STYLE
            logOutButton.setMinSize(100.0, 80.0)
            padddingHBox.children.add(logOutButton)

            // view highscores button
            // PADDING
            (0 until 20).forEach {
                val padding = Label("")
                rightVBox.children.add(padding)
            }
            val viewHighscoresButton = Button("View highscores!")
            viewHighscoresButton.style = STYLE
            viewHighscoresButton.setMinSize(250.0, 150.0)
            rightVBox.children.add(viewHighscoresButton)

            // finally..
            stage.show()
        }
    }
}