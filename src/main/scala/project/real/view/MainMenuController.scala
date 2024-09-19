package project.real.view

import project.real.MainApp
import project.real.model.State
import project.real.util.{CutsceneGeneration, NoteGeneration, PlaySound}

import scalafx.scene.control.Alert
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

// controller for the main menu
@sfxml
class MainMenuController extends PlaySound {
  // pay the main menu bgm
  setBGM(PlaySound.mainMenuBGM)
  playBGM()

  // initialize the alert's window
  var dialogStage: Stage = null

  // starts a new game
  def newGame(): Unit = {
    // reset player location, notes and cutscenes
    State.state.notes.clear()
    // player starts with the bundle note
    State.state.addNote(NoteGeneration.bundleNote)
    // player starts at the lobby
    State.state.location = "Floor1Lobby"
    // reset cutscenes
    CutsceneGeneration.resetCutscenes()

    // start game with the newGame cutscene
    CutsceneGeneration.retrieveID = "newGame"
    MainApp.showCutscene()

    // play click sfx
    playSFX(PlaySound.interactSFX)
    // stop the main menu bgm
    stopBGM()
  }

  // loads the save file
  def loadGame(): Unit = {
    playSFX(PlaySound.interactSFX)
    // show alert if no save file is found
    if (State.state.loadState() == null) {
      val alert = new Alert(Alert.AlertType.Error) {
          initOwner(dialogStage)
          title = "Load Error"
          headerText = "No save file found."
          contentText = "Please save first."
      }.showAndWait()
    }
    // otherwise load the save fie
    else {
      State.state.loadState()
      NoteGeneration.loadNotes()
      CutsceneGeneration.loadSeenCutscenes()
      MainApp.showRoom()
      playSFX(PlaySound.interactSFX)
      stopBGM()
    }
  }

  // closes the game
  def exitGame(): Unit = {
    MainApp.exitGame()

    playSFX(PlaySound.interactSFX)
  }
}
