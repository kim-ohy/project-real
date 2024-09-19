package project.real.util

import project.real.MainApp

import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafx.util.Duration

// trait to play sounds like background music (bgm) and sound effects (sfx)
trait PlaySound {
  // Source: https://openai.com/chatgpt/
  // to initialize MediaPlayer, MediaView and Media
  val bgm: MediaView = new MediaView()
  val sfx: MediaView = new MediaView()

  // sets the bgm for a controller
  def setBGM(player: MediaPlayer): Unit = {
    // Source: https://www.scalafx.org/api/8.0/#scalafx.scene.media.MediaView
    // to set and get mediaPlayer from MediaView
    bgm.mediaPlayer = player

    // Source: https://openai.com/chatgpt/
    // to set bgm to repeat indefinitely
    player.setCycleCount(MediaPlayer.Indefinite)
  }

  // plays the bgm for a controller
  // Source: https://www.scalafx.org/api/8.0/#scalafx.scene.media.MediaPlayer
  // to play Media with MediaPlayer
  def playBGM(): Unit = {
    bgm.mediaPlayer().play()
  }

  // stops the bgm for a controller
  // to stop Media with MediaPlayer
  def stopBGM(): Unit = {
    bgm.mediaPlayer().stop()
  }

  // plays an sfx for an action
  def playSFX(player: MediaPlayer): Unit = {
    sfx.mediaPlayer = player
    player.play()

    // Source: https://openai.com/chatgpt/
    // to reset the track when it finished playing
    player.onEndOfMedia = () => {
      player.stop()
      player.seek(Duration.Zero)
    }
  }

}

object PlaySound extends PlaySound {
  def importMedia(media: String): MediaPlayer = {
    new MediaPlayer(new Media(MainApp.getClass.getResource(s"ost/$media.mp3").toExternalForm))
  }

  // Source: https://www.youtube.com/watch?v=Bnzhtwxb0Uk&list=PLCw2FmQUJFtw3s-yWv0dyrvy1clG1XCu4&index=6
  val mainMenuBGM = importMedia("main_menu_bgm")
  // Source: https://www.youtube.com/watch?v=Jl6VXZW8u00
  val mainBGM = importMedia("main_bgm")
  // Source: https://www.youtube.com/watch?v=-jmEK5QgUww&list=PLv9f_7ofOuLsGAF-IrVRgxd7jgM7eE-Ts&index=43
  val ariaBGM = importMedia("aria_bgm")

  // Source: https://pixabay.com/sound-effects/mouse-click-153941/
  // clipped shorter
  val interactSFX = importMedia("interact_sfx")
  // Source: https://pixabay.com/sound-effects/gun-shot-6178/
  val shootSFX = importMedia("shoot_SFX")
}
