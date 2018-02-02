import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.FileInputStream

class Main : Application()
{
    override fun start(stage: Stage) = MainMenu.show(User(1, "AJ", "Trow", "hairygoat"))

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