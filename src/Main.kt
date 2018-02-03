import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.FileInputStream

class Main : Application()
{
    override fun start(stage: Stage)
    {
        val hbox = HBox()
        val scene = Scene(hbox)
        stage.scene = scene

        // left vbox
        val leftVBox = VBox()
        hbox.children.add(leftVBox)
        val usernameLabel = Label("Username: \t")
        leftVBox.children.add(usernameLabel)

        val passwordLabel = Label("Password: \t")
        leftVBox.children.add(passwordLabel)

        // center vbox
        val centerVBox = VBox()
        hbox.children.add(centerVBox)

        val logo = ImageView(COLLEGE_LOGO)
        centerVBox.children.add(logo)

        val collegeLabel = Label("Farnborough 6th Form College Quiz Application")
        centerVBox.children.add(collegeLabel)

        val userIDField = TextField()
        userIDField.promptText = "Please enter your college ID [E.g. 53475]"
        centerVBox.children.add(userIDField)

        val passwordField = PasswordField()
        passwordField.promptText = "Please enter your college password."
        centerVBox.children.add(passwordField)

        val loginButton = Button("Log In!")
        loginButton.setOnAction {
            if (DBService.userExists(userIDField.text.toInt(), passwordField.text.trim()))
            {
                MainMenu.show(DBService.getUserFromID(userIDField.text.toInt()))
                stage.close()
            }
            else Alert(Alert.AlertType.ERROR, "Invalid college ID and/or password.\nRemember, college ID should be 5 digits long.").showAndWait()
        }
        centerVBox.children.add(loginButton)

        stage.show()
        //MainMenu.show(User(1, "AJ", "Trow", "hairygoat"))
    }

    companion object
    {
        @JvmStatic fun main(args: Array<String>)
        {
            launch(Main::class.java)
            DBService.close()
        }
        val COLLEGE_LOGO = Image(FileInputStream("C:/Users/Sutharsan/Pictures/logo.jpg"))
    }
}