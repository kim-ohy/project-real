package project.real.util

import project.real.model.State

import scalikejdbc._

// Database trait for classes which use the database
trait Database {
  private val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  private val dbURL = "jdbc:derby:save;create=true;";
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me", "mine")

  // ad-hoc session provider on the REPL
  implicit val session: AutoSession.type = AutoSession

}

object Database extends Database{
  // check if the notes table exists
  def hasNotesDBInitialize: Boolean = {
    DB getTable "notes" match {
      case Some(x) => true
      case None => false
    }
  }

  // check if the cutscenes table exists
  def hasCutscenesDBInitialize: Boolean = {
    DB getTable "cutscenes" match {
      case Some(x) => true
      case None => false
    }
  }

  // check if the state table exists
  def hasStateDBInitialize: Boolean = {
    DB getTable "state" match {
      case Some(x) => true
      case None => false
    }
  }

  // setup the database and create all tables if they have not been created yet
  def setupDB() = {
    if (!hasNotesDBInitialize && !hasCutscenesDBInitialize && !hasStateDBInitialize) {
      State.initializeCutscenesTable()
      State.initializeNotesTable()
      State.initializeStateTable()
    }
  }

}
