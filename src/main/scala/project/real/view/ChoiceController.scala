package project.real.view

import project.real.MainApp
import project.real.model.{ConditionalCutscene, Cutscene}
import project.real.util.{CutsceneGeneration, PlaySound}

import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.ImageView
import scalafx.scene.layout.HBox
import scalafxml.core.macros.sfxml

// to handle the choice cutscene and branching cutscenes in the game
@sfxml
class ChoiceController (private var name: Label, private var dialog: Label, private var cutsceneBG: ImageView,
                          private var character1: ImageView, private var character2: ImageView, private var choice1: Button,
                        private var choice2: Button, private var box: HBox) extends PlaySound {

  // set the initial cutscene that will be played
  private var cutscene: Cutscene = CutsceneGeneration.aria4
  // for getting the dialog in the dialogArray inside the cutscene
  private var count: Int = 0

  // set the bgm that will be played
  setBGM(PlaySound.ariaBGM)

  // set the background for the cutscene
  cutsceneBG.image = cutscene.background

  playCutscene()

  // play the cutscene
  def playCutscene(): Unit = {
    // play the bgm at the 13th dialog
    if (cutscene.id == "aria4" && count == 14) {
      playBGM()
    }

    // set the characters and dialog based on dialog case class in dialogArray
    character1.image = cutscene.dialogArray(count).character1
    character2.image = cutscene.dialogArray(count).character2
    name.text = cutscene.dialogArray(count).name
    dialog.text = cutscene.dialogArray(count).text
  }

  // move to next dialog on player mouse click
  def nextLine(): Unit = {
    // add to count when mouse click and change the dialog
    try {
      count += 1
      playCutscene()
    } catch {
      // when count exceeds the length of the dialog array (meaning no more dialog in the cutscene), move on depending
      // the case
      case e: IndexOutOfBoundsException =>
        // show the choices at the end of the cutscene if the cutscene is aria4
        if (cutscene == CutsceneGeneration.aria4) {
          // Source: https://www.scalafx.org/api/8.0/#scalafx.scene.layout.HBox
          // to make visible or invisible
          box.setOpacity(1.0)

          // Source: https://www.scalafx.org/api/8.0/#scalafx.scene.control.Button
          // to disable the button and makes sure the player cannot press them when they are invisible
          // enable the buttons only when the time is right
          choice1.disable = false
          choice2.disable = false
        }
        // end the cutscene if the cutscene is not aria4
        else {
          cutscene.asInstanceOf[ConditionalCutscene].replaceNote()
          stopBGM()
          MainApp.showRoom()
        }
    }
  }

  // implement for if the player shoots Aria
  def shoot(): Unit = {
    // change the cutscene to where Aria gets shot and play the cutscene
    cutscene = CutsceneGeneration.aria5
    count = 0
    playCutscene()

    // make the choices invisible
    box.setOpacity(0)
    choice1.disable = true
    choice2.disable = true

    // stop bgm and play the gunshot sfx
    stopBGM()
    playSFX(PlaySound.shootSFX)
  }

  // implement for if the player does not shoot Aria
  def dont(): Unit = {
    cutscene = CutsceneGeneration.aria6
    count = 0
    playCutscene()
    box.setOpacity(0)
    choice1.disable = true
    choice2.disable = true

    playSFX(PlaySound.interactSFX)
  }

}
