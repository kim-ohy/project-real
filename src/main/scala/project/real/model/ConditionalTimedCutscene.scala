/*
This class is used to create cutscenes which trigger only when the condition is fulfilled, which is when the player has the
note which serves as the key to the cutscene and only once.

Like ConditionalCutscenes, ConditionalTimedCutscenes serve as a hurdle the players must overcome.

Subclass to ConditionalCutscene
Has trait Timed
 */

package project.real.model

import project.real.util.Timed

import scalafx.scene.image.Image
import scala.collection.mutable.ArrayBuffer

class ConditionalTimedCutscene (_dialogArray: ArrayBuffer[Dialog], _background: Image, _seen: Boolean, _id: String,
                                _key: Note, _note: Note, val travelTo: String) extends ConditionalCutscene (_dialogArray,
                                _background, _seen, _id, _key, _note) with Timed {
  def this(background: Image, id: String, key: Note, note: Note, travelTo: String) = {
    this(ArrayBuffer[Dialog](), background, false, id, key, note, travelTo)
  }
}
