package project.real.view

import project.real.MainApp
import project.real.model.State
import project.real.util.{CutsceneGeneration, NoteGeneration, PlaySound}

import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml

// to handle the ending card
@sfxml
class EndingController (private var background: ImageView) extends PlaySound{
  // give the right ending card depending on the choice the player has made
  // the choice the player made is determined by the note they have obtained
  if (State.state.notes.contains(NoteGeneration.justiceNote)) {
    background.image = CutsceneGeneration.endingOneBG
  } else {
    background.image = CutsceneGeneration.endingTwoBG
  }

  // return the player to the main menu on mouse click
  def endGame(): Unit = {
    playSFX(PlaySound.interactSFX)
    MainApp.showMainMenu()
  }
}
