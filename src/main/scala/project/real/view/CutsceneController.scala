package project.real.view

import project.real.MainApp
import project.real.model.{ConditionalCutscene, ConditionalTimedCutscene, Cutscene, NoteCutscene, TimedCutscene}
import project.real.util.{CutsceneGeneration,PlaySound}

import scalafx.scene.control.Label
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml

// controller for cutscenes
// most cutscenes share this controller
@sfxml
class CutsceneController (private var name: Label, private var dialog: Label, private var cutsceneBG: ImageView,
                          private var character1: ImageView, private var character2: ImageView) {
  // get the cutscene that will be played based on the button's id
  // the id is obtained from MainController
  private val cutscene: Cutscene = CutsceneGeneration.retrieveCutscene()

  // to get the dialog from dialogArray based on the dialog's index
  private var count: Int = 0

  // set the background
  cutsceneBG.image = cutscene.background

  // play the cutscene
  playCutscene()

  // plays the cutscene
  def playCutscene(): Unit = {
    // set the characters' image in the dialog
    // images varies with the dialog as each has different expressions
    // character1 sets the character on the left in the cutscene
    character1.image = cutscene.dialogArray(count).character1
    // character2 sets the character on the right
    character2.image = cutscene.dialogArray(count).character2
    // set the name of the person speaking
    name.text = cutscene.dialogArray(count).name
    // set the words spoken
    dialog.text = cutscene.dialogArray(count).text
  }

  // moves on to the next dialog on the player's mouse click
  def nextLine(): Unit = {
    // with each click, increment count by 1 and change the dialog shown
    try {
      count += 1
      playCutscene()
    } catch {
      // when count exceeds the arrayBuffer's bounds, it means that the cutscene is over
      case e: IndexOutOfBoundsException =>
        // set the count back to 0
        count = 0

        // implement different behavior based on the type of cutscene played
        cutscene match {
          case n: NoteCutscene =>
            n.addNote()
          case c: ConditionalCutscene =>
            c.replaceNote()
          case t: TimedCutscene =>
            t.addNote()
          case ct: ConditionalTimedCutscene =>
            ct.replaceNote()
          case _ =>
        }

        // toggle the cutscene being triggered before
        cutscene.seen = true

        // to handle the choice cutscene
        if (cutscene == CutsceneGeneration.aria3) {
          MainApp.showChoiceCutscene()
        }
        // return the player to the map
        else {
          MainApp.showRoom()
        }

    }
  }

}

