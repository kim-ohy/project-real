package project.real.view

import project.real.MainApp
import project.real.model.{Note, State}
import project.real.util.PlaySound
import project.real.util.{CutsceneGeneration, NoteGeneration}

import scalafx.scene.control.{Alert, Label, TableColumn, TableView}
import scalafx.Includes._
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

// controller for the menu
@sfxml
class MenuController (private var notesTable: TableView[Note], private var noteName: Label, private var noteDescription: Label,
                     private var notesColumn: TableColumn[Note, String]) extends PlaySound {
  // binds the player's notes to the notes table in the menu
  notesTable.items = State.state.notes
  notesColumn.cellValueFactory = x => {x.value.name}

  // show nothing when the player does not select anything
  showNoteDetails(None)

  // show a note's details when the player selects it from the table
  notesTable.selectionModel.value.selectedItem.onChange((_,_,newValue) => {
    showNoteDetails(Option(newValue))
  })

  // initialize alert window
  var dialogStage: Stage = null

  // show the note's details
  private def showNoteDetails (note: Option[Note]) ={
    note match{
      // show details if a note is selected
      case Some(note) =>
        // set the noteName label's text to the note's name
        noteName.text <== note.name
        // set the noteDescription label's text to the note's description
        noteDescription.text <== note.description
      // show nothing is no note is selected
      case None =>
        noteName.text.unbind()
        noteDescription.text.unbind()

        noteName.text = ""
        noteDescription.text = ""
    }
  }

  // closes the game
  def exitGame(): Unit = {
    playSFX(PlaySound.interactSFX)
    MainApp.exitGame()
  }

  // closes the menu and return the player to the map
  def closeMenu(): Unit = {
    playSFX(PlaySound.interactSFX)
    MainApp.showRoom()
  }

  // saves the game
  def saveGame(): Unit = {
    playSFX(PlaySound.interactSFX)
    NoteGeneration.saveNotes()
    CutsceneGeneration.saveSeenCutscenes()
    State.state.saveState()
  }

  // loads the player's save
  def loadGame(): Unit = {
    playSFX(PlaySound.interactSFX)
    // show alert if there is no save file
    if (State.state.loadState() == null) {
      val alert = new Alert(Alert.AlertType.Error) {
          initOwner(dialogStage)
          title = "Load Error"
          headerText = "No save file found."
          contentText = "Please save first."
      }.showAndWait()
    }
    // load save if there is a save file
    else {
      State.state.loadState()
      NoteGeneration.loadNotes()
      CutsceneGeneration.loadSeenCutscenes()
      MainApp.showRoom()
    }
  }
}
