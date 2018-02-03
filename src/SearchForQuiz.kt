import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class SearchForQuiz
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
            val scene = Scene(vbox)
            scene.stylesheets.add("Styles.css")
            stage.scene = scene

            // image
            val imageHBox = HBox()
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.0
            image.fitWidth = 250.0
            (0 until 17).forEach { imageHBox.children.add(Label("      ")) }
            imageHBox.children.add(image)
            vbox.children.add(imageHBox)

            val listView = ListView<Quiz>()
            val allSortedQuizzes = FXCollections.observableArrayList(DBService.allSortedQuizzes())

            // search bar
            val searchBar = TextField()
            searchBar.style = "-fx-alignment: center;"
            searchBar.promptText = "Search for quiz"
            searchBar.textProperty().addListener { _, _, searchText
            ->
                val searchTrimmed = searchText.trim().toLowerCase()
                if (searchTrimmed.isNotBlank())
                {
                    listView.items.clear()
                    allSortedQuizzes.forEach { if (it.toString().toLowerCase().contains(searchTrimmed)) listView.items.add(it) }
                }
                else { listView.items = FXCollections.observableArrayList(DBService.allSortedQuizzes()) }
            }
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(searchBar)

            fun showQuestionsInQuiz(user: User)
            {
                if (listView.selectionModel.selectedIndex < listView.items.size)
                {
                    val quiz = listView.selectionModel.selectedItem
                    QuestionView.show(0, DBService.questionsFromQuiz(quiz), user, quiz.quizID)
                    stage.close()
                }
            }

            // list view
            listView.items = FXCollections.observableArrayList(DBService.allSortedQuizzes())
            var playQuizNext = false
            listView.selectionModel.selectedItemProperty().addListener { _, _, _ ->
                if (playQuizNext) showQuestionsInQuiz(user)
                else playQuizNext = true
            }
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(listView)

            // play quiz button
            val playQuizHBox = HBox()
            val playQuizButton = Button("Play quiz!")
            playQuizButton.setOnAction {
                if (playQuizNext) showQuestionsInQuiz(user)
                else playQuizNext = true
            }
            (0 until 2).forEach { vbox.children.add(Label("")) }
            (0 until 18).forEach { playQuizHBox.children.add(Label("       ")) }
            playQuizHBox.children.add(playQuizButton)
            vbox.children.add(playQuizHBox)

            stage.show()
        }
    }
}