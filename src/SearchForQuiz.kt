import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.Stage

class SearchForQuiz
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

            // search bar
            val searchBar = TextField("Search me boiii")
            vbox.children.add(searchBar)

            // list view
            val listView = ListView(FXCollections.observableArrayList(arrayListOf("")))
            vbox.children.add(listView)

            // play quiz button
            val playQuizButton = Button("Play quiz!")
            var playQuizNext = false
            playQuizButton.setOnAction {
                if (playQuizNext) goToQuiz()
                else playQuizNext = true
            }
            vbox.children.add(playQuizButton)

        }

        fun goToQuiz() {}
    }
}