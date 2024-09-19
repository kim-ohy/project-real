/*
This class is used to link related cutscenes together.
The cutscenes occur sequentially, where the second cutscene is triggered only if the condition is fulfilled, and the
third cutscene is triggered only if the first and second cutscene have been triggered once.
ConnectedCutscenes are used to create quests, where the first cutscene would be giving the player the quest, and
the second cutscene is when the quest is completed. The third cutscene is the default cutscene after the
quest has been completed.
 */

package project.real.model

class ConnectedCutscenes (val cutscene1: Cutscene, val cutscene2: ConditionalCutscene, val cutscene3: Cutscene, val id: String) {
  // second constructor to allow this class to be used for 2 cutscenes instead of 3
  def this (cutscene1: Cutscene, cutscene2: ConditionalCutscene, id: String) = {
    this(cutscene1, cutscene2, null, id)
  }

  def getCutscene(): Cutscene = {
    // return second cutscene if the condition for the second cutscene is fulfilled and that the first cutscene has been
    // triggered at least once
    if (cutscene2.isUnlocked() && cutscene1.seen) {
      cutscene2
    }

    // return third cutscene if second and first cutscene have been triggered before
    else if (cutscene1.seen && cutscene2.seen) {
      // if there is no third cutscene, return the second cutscene
      if (cutscene3 != null) {
        cutscene3
      } else {
        cutscene2
      }
    }

    // return the first cutscene if it has never been triggered before
    else {
      cutscene1
    }
  }

}
