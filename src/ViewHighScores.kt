import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.scene.control.cell.PropertyValueFactory


class ViewHighScores
{
    companion object
    {
        fun show(user: User)
        {
            val stage = Stage()
            val mainHBox = HBox()
            val scene = Scene(mainHBox)
            stage.scene = scene

            // hboxes
            val topHBox = HBox()
            val bottomHBox = HBox()
            mainHBox.children.addAll(topHBox, bottomHBox)

            // vboxes
            val leftVBox = VBox()
            val centerVBox = VBox()
            topHBox.children.addAll(leftVBox, centerVBox)

            // left vbox -- labels
            val quizLabel = Label("Quiz: \t")
            val highscoresLabel = Label("Highscore to display: \t")
            leftVBox.children.addAll(quizLabel, highscoresLabel)

            // center vbox
            val highscoresHeader = Label("HIGHSCORES")
            centerVBox.children.add(highscoresHeader)

            val highscoreTypeCombo = ComboBox<String>(FXCollections.observableArrayList(arrayListOf("All Users", "Mine Only")))
            highscoreTypeCombo.selectionModel.select(0)
            centerVBox.children.add(highscoreTypeCombo)

            val table = TableView<ScoreTableData>()
            val highscoreCombo = ComboBox<Quiz>(FXCollections.observableArrayList(DBService.allSortedQuizzes()))
            highscoreCombo.selectionModel.select(0)

            centerVBox.children.add(highscoreCombo)

            // bottom -- listview
            bottomHBox.children.add(table)
            val idCol = TableColumn<ScoreTableData, String>("User ID")
            val firstNameCol = TableColumn<ScoreTableData, String>("First Name")
            val lastNameCol = TableColumn<ScoreTableData, String>("Last Name")
            val scoreCol = TableColumn<ScoreTableData, String>("Score")
            table.columns.addAll(idCol, firstNameCol, lastNameCol, scoreCol)

            idCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("userID")
            firstNameCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("firstName")
            lastNameCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("lastName")
            scoreCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("score")

            val displayButton = Button("Display!")
            displayButton.setOnAction {
                if (highscoreTypeCombo.selectionModel.selectedItem == "All Users")
                    table.items = FXCollections.observableArrayList(DBService.getScoreData(highscoreCombo.selectionModel.selectedItem.quizID))
                else
                    table.items = FXCollections.observableArrayList(DBService.getScoreData(highscoreCombo.selectionModel.selectedItem.quizID).filter { it.getUserID().toInt() == user.userID })
            }
            centerVBox.children.add(displayButton)

            stage.show()
        }
    }
}