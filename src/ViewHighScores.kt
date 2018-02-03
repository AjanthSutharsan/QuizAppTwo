import javafx.collections.FXCollections
import javafx.geometry.Insets
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
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val mainVBox = VBox()
            mainVBox.padding = Insets(15.0, 15.0, 100.0, 85.0)
            mainVBox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(mainVBox)
            stage.scene = scene

            // hboxes
            val topHBox = HBox()
            val bottomHBox = HBox()
            mainVBox.children.addAll(topHBox, bottomHBox)

            // vboxes
            val leftVBox = VBox()
            val lefterVBox = VBox()
            val lefterHBox = HBox()
            (0 until 8).forEach { lefterHBox.children.add(Label("       ")) }
            lefterVBox.children.add(lefterHBox)
            val divider = VBox()
            val dividerHBox = HBox()
            divider.children.add(dividerHBox)
            (0 until 1).forEach { dividerHBox.children.add(Label("      ")) }
            val rightVBox = VBox()
            topHBox.children.addAll(lefterVBox, leftVBox, dividerHBox, rightVBox)

            // left vbox -- labels
            val quizLabel = Label("Quiz: \t")
            val highscoresLabel = Label("Highscore to display: \t")
            (0 until 4).forEach { leftVBox.children.add(Label("")) }
            leftVBox.children.addAll(quizLabel, Label(""), highscoresLabel)

            // center vbox
            val highscoresHeader = Label("HIGHSCORES")
            highscoresHeader.style = "-fx-font-family: Georgia; -fx-font-size: 20; -fx-underline: true;"
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoresHeader)

            val highscoreTypeCombo = ComboBox<String>(FXCollections.observableArrayList(arrayListOf("All Users", "Mine Only")))
            highscoreTypeCombo.selectionModel.select(0)
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoreTypeCombo)

            val table = TableView<ScoreTableData>()
            table.minWidth = 800.0
            val highscoreCombo = ComboBox<Quiz>(FXCollections.observableArrayList(DBService.allSortedQuizzes()))
            highscoreCombo.selectionModel.select(0)
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoreCombo)

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

            idCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))
            firstNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))
            lastNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))
            scoreCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))

            idCol.isSortable = false
            firstNameCol.isSortable = false
            lastNameCol.isSortable = false
            scoreCol.isSortable = false

            val displayButton = Button("Display!")
            displayButton.setOnAction {
                if (highscoreTypeCombo.selectionModel.selectedItem == "All Users")
                    table.items = FXCollections.observableArrayList(DBService.getScoreData(highscoreCombo.selectionModel.selectedItem.quizID))
                else
                    table.items = FXCollections.observableArrayList(DBService.getScoreData(highscoreCombo.selectionModel.selectedItem.quizID).filter { it.getUserID().toInt() == user.userID })
            }
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(displayButton)
            (0 until 2).forEach { rightVBox.children.add(Label("")) }

            stage.show()
        }
    }
}