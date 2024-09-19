/*
This class is used to create cutscenes that only occur once and only at a certain point of time.
As time does not really exist in the game, the time is indicated by when the player moves to a different area of the map
for the first time.
TimedCutscenes are triggered automatically when the player advances the game. It typically serves as a major event of
the game and grants an important note.

Subclass of NoteCutscene
Has trait Timed
 */

package project.real.model

import project.real.util.Timed
import scalafx.scene.image.Image

import scala.collection.mutable.ArrayBuffer

class TimedCutscene (_dialogArray: ArrayBuffer[Dialog], _background: Image, _seen: Boolean, _id: String,
                           _note:Note, val travelTo: String) extends NoteCutscene (_dialogArray, _background, _seen, _id, _note) with Timed {
  def this(background: Image, id: String, note: Note, travelTo: String) = {
    this(ArrayBuffer[Dialog](), background, false, id, note, travelTo)
  }
}
