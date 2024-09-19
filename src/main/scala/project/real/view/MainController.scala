package project.real.view

import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent

import project.real.model.{Cutscene, State}
import project.real.MainApp
import project.real.util.{CutsceneGeneration, PlaySound, Timed}

// controller for the map
// lets the player navigate through the map
@sfxml
class MainController extends PlaySound{
  // play the game's bgm
  setBGM(PlaySound.mainBGM)

  // stop the bgm when the player reaches the top floor for spookiness
  if (State.state.location.contains("Floor13")) {
    stopBGM()
  } else {
    playBGM()
  }

  // handles the player's interactions with items and npcs in the map
  def handlePlayerInteraction(action: ActionEvent): Unit = {

    // Source: https://www.scalafx.org/api/8.0/#scalafx.event.ActionEvent
    // to get the object the player clicked on (a button)
    val button = action.source.toString

    // Source: https://openai.com/chatgpt/
    // to get the button's id by splitting the string gotten from toString() of the object
    val id = button.split("[,=\\[]")(2)

    // set the retrieveID in the cutscene generation to the button's id
    // this is to retrieve the cutscene based on the item clicked
    CutsceneGeneration.retrieveID = id

    // show the cutscene
    MainApp.showCutscene()

    // play the mouseclick sfx
    playSFX(PlaySound.interactSFX)
  }

  // handles how the player moves from area to area (changing fxml file)
  def travelToArea(action: ActionEvent): Unit = {
    // get the button clicked on
    val button = action.source.toString

    // get the button's id
    val id = button.split("[,=\\[]")(2)

    // change the player's location the button's id
    State.state.location = id

    // change the fxml file shown to the id which corresponds with the fxml file (basically the fxml file's name)
    MainApp.showRoom()

    // play mouse clickey
    playSFX(PlaySound.interactSFX)
  }

  // handles TimedCutscenes
  // this is for handling travel buttons that also display a cutscene
  // the cutscene are meant to occur only once
  def handleTimedCutscene(action: ActionEvent): Unit = {
    val button = action.source.toString
    val id = button.split("[,=\\[]")(2)
    CutsceneGeneration.retrieveID = id

    // get the cutscene from CutsceneGeneration based on the button's id
    val cutscene: Cutscene = CutsceneGeneration.retrieveCutscene()

    // typecast based on Timed trait
    cutscene match {
      case t: Timed =>
        // if cutscene has the timed trait, change the player's location
        t.changeLocation()

        // show the cutscene if it has not be triggered before, otherwise, move the player
        if (!cutscene.seen) {
          MainApp.showCutscene()
        }
        else {
          MainApp.showRoom()
        }

      case _ =>
        // simply show the cutscene if it is not timed
        MainApp.showCutscene()
    }
    // click
    playSFX(PlaySound.interactSFX)
  }

  // opens the menu when the menu button (bottom right button) is clicked
  def openMenu(): Unit = {
    MainApp.showMenu()
    playSFX(PlaySound.interactSFX)
  }

}
