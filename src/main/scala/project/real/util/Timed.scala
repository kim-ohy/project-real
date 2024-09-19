package project.real.util

import project.real.model.State

// Timed trait for timed cutscenes
// sets the location the player will travel to in lieu of the usual travel mechanism
trait Timed {
  val travelTo: String

  def changeLocation(): Unit = {
    State.state.location = travelTo
  }
}
