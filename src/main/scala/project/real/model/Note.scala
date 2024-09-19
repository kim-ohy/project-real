/*
This class is used to create notes that the player will obtain while playing the game.
Notes are basically items or snippets of information. They serve as keys to unlocking cutscenes or areas.
The description of the note contains details about the note and sometimes hints about where the note can be used.
Players can see all the notes they have obtained in the menu.
 */

package project.real.model

import project.real.util.Database

import scalafx.beans.property.StringProperty
import scalikejdbc._

import scala.collection.mutable.ArrayBuffer

class Note (val name: StringProperty, val description: StringProperty) extends Database {
  def this(n:String, d: String) = {
    this(new StringProperty(n), new StringProperty(d))
  }

  // to save a note into the database
  def saveNote(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        insert into notes(name, description) values
        (${name.value}, ${description.value})
         """.execute.apply()
    }
  }

  // load a note from the database
  def loadNote(noteName: String): Unit = {
    if (name.value == noteName) {
      State.state.addNote(this)
    }
  }
}