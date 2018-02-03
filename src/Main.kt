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
        val vbox = VBox()
        vbox.style = "-fx-background-color: #D1C4D6"
        vbox.padding = Insets(15.0, 15.0, 100.0, 15.0)

        val scene = Scene(vbox)
        stage.scene = scene

        stage.width = 1000.0
        stage.height = 650.0
        stage.isResizable = false

        // logo
        val logoHBox = HBox()
        val logo = ImageView(COLLEGE_LOGO)
        logo.fitHeight = 250.0
        logo.fitWidth = 250.0
        (0 until 17).forEach { logoHBox.children.add(Label("      ")) }
        logoHBox.children.add(logo)
        vbox.children.add(logoHBox)

        // title
        val titleHBox = HBox()
        val collegeLabel = Label("Farnborough 6th Form College Quiz Application")
        collegeLabel.style = "-fx-font-family: Georgia; -fx-font-size: 20; -fx-underline: true;"
        (0 until 13).forEach { titleHBox.children.add(Label("      ")) }
        titleHBox.children.add(collegeLabel)
        (0 until 2).forEach { vbox.children.add(Label("")) }
        vbox.children.add(titleHBox)

        // username
        val usernameHBox = HBox()
        (0 until 2).forEach { vbox.children.add(Label("")) }
        vbox.children.add(usernameHBox)
        val usernameLabel = Label("Username: \t")
        (0 until 17).forEach { usernameHBox.children.add(Label("      ")) }
        usernameHBox.children.add(usernameLabel)

        val userIDField = TextField()
        userIDField.promptText = "Please enter your college ID [E.g. 53475]"
        usernameHBox.children.add(userIDField)

        // password
        val passwordHBox = HBox()
        (0 until 2).forEach { vbox.children.add(Label("")) }
        vbox.children.add(passwordHBox)

        val passwordField = PasswordField()
        passwordField.promptText = "Please enter your college password."

        val passwordLabel = Label("Password: \t")
        (0 until 17).forEach { passwordHBox.children.add(Label("      ")) }
        passwordHBox.children.addAll(passwordLabel, passwordField)

        val loginHBox = HBox()
        val loginButton = Button("Log In!")
        loginButton.setOnAction {
            if (DBService.userExists(userIDField.text.toInt(), passwordField.text.trim()))
            {
                MainMenu.show(DBService.getUserFromID(userIDField.text.toInt()))
                stage.close()
            }
            else Alert(Alert.AlertType.ERROR, "Invalid college ID and/or password.\nRemember, college ID should be 5 digits long.").showAndWait()
        }
        (0 until 22).forEach { loginHBox.children.add(Label("      ")) }
        loginHBox.children.add(loginButton)
        (0 until 2).forEach { vbox.children.add(Label("")) }
        vbox.children.add(loginHBox)

        stage.show()

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