/*
This class is used to link to one cutscene based on whether the condition for the cutscene is fulfilled.
The condition is fulfilled if the player has the corresponding note.
 */

package project.real.model

class BranchingCutscenes (val cutscene1: ConditionalTimedCutscene, val cutscene2: ConditionalTimedCutscene, val id: String) {
  // return cutscene1 if cutscene1's condition is fulfilled and cutscene2 if cutscene2's condition is fulfilled
  def getBranch: ConditionalTimedCutscene = {
    if (cutscene1.isUnlocked()) {
      cutscene1
    }
    else {
      cutscene2
    }
  }
}
