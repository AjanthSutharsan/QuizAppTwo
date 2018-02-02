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
        fun show(user: User)
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

            val listView = ListView<Quiz>()
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
            val allSortedQuizzes = FXCollections.observableArrayList(DBService.allSortedQuizzes())
            listView.items = FXCollections.observableArrayList(DBService.allSortedQuizzes())
            var playQuizNext = false
            listView.selectionModel.selectedItemProperty().addListener { _, _, _ ->
                if (playQuizNext) showQuestionsInQuiz(user)
                else playQuizNext = true
            }
            vbox.children.add(listView)

            // search bar
            val searchBar = TextField("Search me boiii")
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
            vbox.children.add(searchBar)

            // play quiz button
            val playQuizButton = Button("Play quiz!")
            playQuizButton.setOnAction {
                if (playQuizNext) showQuestionsInQuiz(user)
                else playQuizNext = true
            }
            vbox.children.add(playQuizButton)

            stage.show()
        }
    }
}