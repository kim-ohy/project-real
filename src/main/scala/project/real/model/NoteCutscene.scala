/*
This class is used to create cutscenes that gives the player a note.
Players do not need to satisfy any condition to obtain the note. They simply need to trigger the cutscene once.

Subclass of Cutscene.
 */

package project.real.model

import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image

import scala.collection.mutable.ArrayBuffer

// note is given to the player
class NoteCutscene (_dialogArray: ArrayBuffer[Dialog], _background: Image, _seen: Boolean, _id: String,
                    private val note: Note) extends Cutscene (_dialogArray, _background, _seen, _id){

  def this(background: Image, id: String, note: Note) = {
    this(ArrayBuffer[Dialog](), background, false, id, note)
  }

  //to give the note to the player
  def addNote(): Unit = {
    if (!seen) {
      State.state.addNote(note)
    }
    else {

    }
  }
}
