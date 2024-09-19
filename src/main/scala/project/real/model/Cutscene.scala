/*
This class is used to create cutscenes for the player to view.
Cutscenes are the very basis of this game as it provides the players notes to proceed.
Cutscenes are triggered with the player interact with buttons in the map.

Superclass to ConditionalCutscene, NoteCutscene, TimedCutscene and ConditionalTimedCutscene
 */

package project.real.model

import project.real.util.Database
import scalafx.scene.image.Image
import scalikejdbc._

import scala.collection.mutable.ArrayBuffer

// seen is used to indicate whether the player has triggered the cutscene before
// background is displayed as the background of the cutscene
// dialogArray contains the dialogs of the cutscene
// id is used to identify the cutscene
abstract class Cutscene (var dialogArray: ArrayBuffer[Dialog], val background: Image, var seen: Boolean, val id: String) extends Database{
  def this(background: Image, id: String) = {
    this(ArrayBuffer[Dialog](), background, false, id)
  }

  // to save the cutscene's seen state to the database
  def saveCutsceneState: Boolean = {
    // seen is stored as "1" in database if triggered before and "0" if not triggered before
    if (seen) {
      val seenBit = 1
      DB autoCommit { implicit session =>
        sql"""
         insert into cutscenes(cutsceneID, seen) values
         ($id, $seenBit)
         """.execute.apply()
      }
    } else {
      val seenBit = 0
      DB autoCommit { implicit session =>
        sql"""
         insert into cutscenes(cutsceneID, seen) values
         ($id, $seenBit)
         """.execute.apply()
      }
    }
  }

  // to load the cutscene' load state from the database
  def loadCutsceneState(seenBit: String): Unit = {
    // if the seen bit in the database is 0, set the cutscene to never triggered before
    if (seenBit == "0"){
      seen = false
    }
    // if the seen bit in the database is 1, set the cutscene to have been triggered before
    else {
      seen = true
    }
  }
}

