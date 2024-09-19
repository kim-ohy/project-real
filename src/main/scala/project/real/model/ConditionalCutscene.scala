/*
This class is used to create cutscenes trigger only when the condition is fulfilled, which is when the player has the
note which serves as the key to the cutscene.
ConditionalCutscenes are used as something similar to obstacles in the game, where the user must get the note to
unlock the cutscene in order to advance the game.

Subclass to Cutscene
 */

package project.real.model

import scalafx.scene.image.Image
import scala.collection.mutable.ArrayBuffer

// key is used to indicate the note needed to unlock the cutscene
class ConditionalCutscene(_dialogArray: ArrayBuffer[Dialog], _background: Image, _seen: Boolean, _id: String,
                          private val key: Note, private val note: Note) extends Cutscene (_dialogArray, _background, _seen, _id){
  def this(background: Image, id: String, key: Note, note: Note) = {
    this(ArrayBuffer[Dialog](), background, false, id, key, note)
  }

  // checks if the player has the note to unlock the cutscene
  def isUnlocked(): Boolean = {
    State.state.notes.contains(key)
  }

  // consumes the note that was used to unlock the cutscene and gives the player a new note to advance the game
  def replaceNote(): Unit = {
    State.state.notes -= key
    State.state.notes += note
  }

}
