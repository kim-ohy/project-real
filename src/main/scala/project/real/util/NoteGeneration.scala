package project.real.util

import project.real.model.{Note, State}

import scalikejdbc._

// All the game's notes are generated here
object NoteGeneration extends Database {
  val bundleNote = new Note("Bundle", "A bundle. It's wrapped in a white cloth.")
  val ladderNote = new Note("Ladder", "There's a ladder in the first floor's storeroom. Is there something we can reach with it?")
  val liftNote = new Note("The Lift", "Special key cards are needed to go to higher floors. Where can we find them?")
  val presidentNote = new Note("The President", "The employees call the president a madman for trying to resurrect the dead.")
  val cafeNote = new Note("The Cafe", "They serve excellent coffee.")
  val coffeeNote = new Note("Coffee", "You have a cup of coffee. The gardener asked for one.")
  val grannyNote = new Note("Granny", "Her husband passed away a few days ago. Can we do anything to cheer her up?")
  val flowerNote = new Note("Flower Bouquet", "Contains marigolds, calendulas and white lilies. A good gift to cheer someone up.")
  val boxNote = new Note("Box", "It's too high to reach.")
  val gardenerNote = new Note("Gardener", "The gardener told us to fetch him a cup of coffee. He said he'd make it worth our time.")
  val presidentsSonNote = new Note("The President's Son", "The president had a son who was extremely talented in art. He died in a car crash.")
  val accidentNote = new Note("The Accident", "The president's son passed away in a freak accident. He died to save his friend.")
  val pinkKeyCardNote = new Note("Pink Key Card", "Grants access to the sixth floor.")
  val blackKeyCardNote = new Note("Black Key Card", "Grants access to the top floor.")
  val sixthFloorNote = new Note("The Sixth Floor", "You now have access to the sixth floor, the garden.")
  val thirteenthFloorNote = new Note("The Thirteenth Floor", "You now have access to the thirteenth floor, the president's office.")

  val ariaNote = new Note("Aria", "A girl you met on the first floor. She seems to want to get to top floor too.")
  val ariasGoalNote = new Note("Aria's Goal", "Aria says she's here to tell the president something.")
  val ariasIdentityNote = new Note("Aria's Identity", "Aria is the friend the president's son saved.")
  val projectNote = new Note("Project Revive Alex", "The president's project to resurrect the dead by creating android replicas of humans and installing the dead's memory into them.")

  val revengeNote = new Note("Revenge", "You have avenged Alex.")
  val mercyNote = new Note("Mercy", "You have chosen to grant them mercy.")

  val justiceNote = new Note("Justice", "Was what you did truly justified?")
  val forgivenessNote = new Note("Forgiveness", "Perhaps that was all they wanted.")

  val notesArray = Array[Note](bundleNote, ariaNote, ladderNote, liftNote, presidentNote, cafeNote, coffeeNote, grannyNote, flowerNote, boxNote, gardenerNote,
    ariasGoalNote, presidentsSonNote, pinkKeyCardNote, blackKeyCardNote, ariasIdentityNote, revengeNote, forgivenessNote, accidentNote, sixthFloorNote, thirteenthFloorNote,
    justiceNote, mercyNote)

  // saves all the note the player has into the database
  def saveNotes() = {
    // empty the table
    DB autoCommit { implicit session =>
      sql"""
           delete from notes
         """.execute.apply()
    }

    // save all notes into the table
    for (note <- State.state.notes) {
      note.saveNote()
    }
  }

  // load all the note the player saved in the database
  def loadNotes() = {
    // remove all the notes the player currently has
    State.state.notes.clear()

    // replace them with the notes saved in the database
    DB readOnly  { implicit session =>
      val notes =
        sql"""
        select * from notes
      """.map(rs => rs.string("name")).list.apply()

      for (note <- notes) {
        for (n <- notesArray) {
          n.loadNote(note)
        }
      }
    }
  }


}
