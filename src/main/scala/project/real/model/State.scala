/*
This class indicates the current state of the game such as the player's status and the notes the player has.

Has trait Database to remember the player's location.
 */

package project.real.model

import project.real.util.Database

import scalafx.collections.ObservableBuffer
import scalikejdbc._

class State (var notes: ObservableBuffer[Note], var location: String) extends Database {
  def this() = {
    this(ObservableBuffer[Note](), "Floor1Lobby")
  }

  def addNote(note: Note): Unit = {
    notes += note
  }

  // saves the player's current location in the database to be extracted during loading
	def saveState(): Unit  = {
    DB autoCommit { implicit session =>
      sql"""
        delete from state
      """.execute.apply()
    }

    DB autoCommit { implicit session =>
      sql"""
        insert into state (location) values
          ($location)
      """.update.apply()
    }
	}

  // loads the player's location from the database
  def loadState(): Any = {
    DB readOnly { implicit session =>
      val result =
        sql"""
        select location from state
        FETCH FIRST 1 ROW ONLY
      """.map(rs => rs.string("location")).single.apply()

      result match {
        // change the player's location if exist in the database
        case Some(x) => location = x
        // return null if not exist
        case None => null
      }
    }
  }
}

object State extends Database{
  // initialize game's state
  val state = new State()

  // create state table
  // stores the location of the player
  def initializeStateTable(): Unit = {
    DB autoCommit {
      implicit session =>
        sql"""
             create table state (
              location varchar(100) PRIMARY KEY
             )
           """.execute.apply()
    }
  }

  // create notes table
  // stores all the notes the player has
  def initializeNotesTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
           create table notes (
            name varchar(100) PRIMARY KEY,
            description varchar(500)
           )
           """.execute.apply()
    }
  }

  // create cutscene table
  // stores which cutscenes have been triggered and which have not
  def initializeCutscenesTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
           create table cutscenes (
            cutsceneID varchar(100) PRIMARY KEY,
            seen char(1)
           )
         """.execute.apply()
    }
  }

}
