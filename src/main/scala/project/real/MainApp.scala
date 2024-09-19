package project.real

import project.real.model.State
import project.real.util.Database

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{ImageCursor, Scene}
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import scalafx.scene.image.Image
import javafx.{scene => jfxs}

object MainApp extends JFXApp {
  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load()
  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // Source: https://www.scalafx.org/api/8.0/#scalafx.scene.ImageCursor
  // to show custom cursor for the game
  val playerCursor = new ImageCursor(new Image(getClass.getResourceAsStream("sprites/interface/cursor.png")), 30, 30)

  // initialize stage
  // Source: https://www.scalafx.org/api/8.0/#scalafx.stage.Stage
  // to set cursor and set maximized or fullscreen
  stage = new PrimaryStage {
    title = "Project Re:Al"
    icons += new Image(getClass.getResourceAsStream("sprites/miscellaneous/game_icon.png"))
    scene = new Scene {
      root = roots
      cursor = playerCursor
    }

    //maximized = true
    fullScreen = true
  }

  Database.setupDB()

  // to display the main menu
  def showMainMenu(): Unit = {
    val resource = getClass.getResource("view/MainMenu.fxml")

    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // to close the game
  def exitGame(): Unit = {
    stage.close()
  }

  // to open the menu
  def showMenu(): Unit = {
    val resource = getClass.getResource("view/Menu.fxml")

    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // to show cutscenes
  def showCutscene(): Unit = {
    val resource = getClass.getResource("view/Cutscene.fxml")

    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // to show the choice cutscene
  def showChoiceCutscene(): Unit = {
    val resource = getClass.getResource("view/Choice.fxml")

    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // to show the map based on the player's current location
  def showRoom(): Unit = {
    val resource = getClass.getResource(s"view/${State.state.location}.fxml")

    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // show the main menu when the game starts up
  showMainMenu()
}