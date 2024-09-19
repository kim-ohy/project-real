/*
This case class is used to create dialogs in a cutscene.
 */

package project.real.model

import scalafx.scene.image.Image

// name refers to the name of the character speaking
// text refers to the word spoken
// character1 and character2 are the images of the characters involved in the cutscene
case class Dialog(name: String, text: String, character1: Image, character2: Image)
