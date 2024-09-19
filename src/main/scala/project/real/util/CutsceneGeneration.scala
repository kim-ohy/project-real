package project.real.util

import project.real.MainApp
import project.real.model.{BranchingCutscenes, ConditionalCutscene, ConditionalTimedCutscene, ConnectedCutscenes, Cutscene, Dialog, NoteCutscene, TimedCutscene}

import scalafx.scene.image.Image
import scalikejdbc._

// All the game's cutscenes are generated here
object CutsceneGeneration extends Database {
  var retrieveID: String = "newGame"
  var cutscene: Cutscene = null

  // Source: https://www.flaticon.com/
  // the entire map is originally made with the style inspired by 1BitHeart's map and certain icons downloaded from flaticon using InkScape
  // all cutscene BGs are created by applying a special pixelated filter on the map, the style is also inspired by 1BitHeart
  val floorOneLobbyBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_1_lobby.png"))
  val floorOneLiftBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_1_lift.png"))
  val floorOneToiletsBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_1_toilets.png"))
  val floorOneStairsBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_1_stairs.png"))
  val floorOneStoreroomBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_1_storeroom.png"))

  val floorTwoCafeBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_2_cafe.png"))
  val floorTwoLiftBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_2_lift.png"))
  val floorTwoToiletsBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_2_toilets.png"))
  val floorTwoStairsBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_2_stairs.png"))
  val floorTwoStoreroomBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_2_storeroom.png"))

  val floorSixLiftBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_6_lift.png"))
  val floorSixGardenLeftBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_6_garden_left.png"))
  val floorSixGardenRightBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_6_garden_right.png"))

  val floorThirteenLiftBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_13_lift.png"))
  val floorThirteenHallwayOneBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_13_hallway_1.png"))
  val floorThirteenHallwayTwoBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_13_hallway_2.png"))
  val floorThirteenHallwayThreeBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_13_hallway_3.png"))
  val floorThirteenOfficeBG = new Image(MainApp.getClass.getResourceAsStream("sprites/cutscene_bg/floor_13_office.png"))

  val endingOneBG = new Image(MainApp.getClass.getResourceAsStream("sprites/miscellaneous/ending_1.png"))
  val endingTwoBG = new Image(MainApp.getClass.getResourceAsStream("sprites/miscellaneous/ending_2.png"))

  // Source: https://www.spriters-resource.com/pc_computer/1bitheart/
  // all character and NPC sprites are traced and recoloured from 1BitHeart's character sprites with some minor tweaks
  val riel_neutral = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/riel_1.png"))
  val riel_talking = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/riel_2.png"))

  val aria_happy = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_1.png"))
  val aria_confused = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_2.png"))
  val aria_sad = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_3.png"))
  val aria_v_happy = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_4.png"))
  val aria_neutral = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_5.png"))
  val aria_scared = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_6.png"))
  val aria_v_sad = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_7.png"))
  val aria_dead = new Image(MainApp.getClass.getResourceAsStream("sprites/characters/aria_8.png"))

  val gun = new Image(MainApp.getClass.getResourceAsStream("sprites/miscellaneous/gun.png"))

  val lobby = new Cutscene(floorOneLobbyBG, "lobby") {
    dialogArray += Dialog("Riel", "We can't leave before we get to him.", riel_neutral, null)
  }

  val door1 = new Cutscene(floorOneToiletsBG, "door1") {
    dialogArray += Dialog("Aria", "The doors to the stairs are locked.", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "We'll need to find a different path.", riel_neutral, aria_confused)
  }

  val door2 = new Cutscene(floorOneStairsBG, "door2") {
    dialogArray += Dialog("Aria", "The doors are locked.", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "We'll need to find a different path.", riel_neutral, aria_confused)
  }

  val stairs = new Cutscene(floorTwoStairsBG, "stairs") {
    dialogArray += Dialog("Aria", "The stairs are blocked...", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "I guess we can't climb all the way up to the top floor, huh?", riel_neutral, aria_sad)
  }

  val cafe1 = new Cutscene(floorTwoCafeBG, "cafe1") {
    dialogArray += Dialog("Cashier", "Hello, can I get you anything?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "No, we're just browsing!", riel_neutral, aria_v_happy)
  }

  val cafe2 = new ConditionalCutscene(floorTwoCafeBG, "cafe2", NoteGeneration.gardenerNote, NoteGeneration.coffeeNote) {
    dialogArray += Dialog("Cashier", "Hello, can I get you anything?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "Yes, please! I'd like a cup of coffee!", riel_neutral, aria_happy)
    dialogArray += Dialog("Cashier", "Here you go, a cup of coffee, freshly brewed. That'll be 5 bucks.", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "5 bucks? The gardener didn't give us any money, did he?", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "Well... I guess I'll have to use mine.", riel_neutral, aria_sad)
  }

  val cafe3= new Cutscene(floorTwoCafeBG, "cafe3") {
    dialogArray += Dialog("Cashier", "Hello again, can I get you anything else?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "No, we're just browsing!", riel_neutral, aria_v_happy)
  }

  val coffeeQuest1 = new NoteCutscene(floorSixGardenLeftBG, "coffeeQuest1", NoteGeneration.gardenerNote) {
    dialogArray += Dialog("Gardener", "Hey, you two over there!", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "Huh? Are you talking to us?", riel_neutral, aria_confused)
    dialogArray += Dialog("Gardener", "That's right! I'm been working here all day and my throat's feeling awfully parched, so can you fetch me a cup of coffee from the cafe?", riel_neutral, aria_confused)
    dialogArray += Dialog("Gardener", "I promise I'll make it worth your time!", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "O-Okay then. I guess we'll need to fetch him a cup of coffee now.", riel_neutral, aria_sad)
  }

  val coffeeQuest2 = new ConditionalCutscene(floorSixGardenLeftBG, "coffeeQuest2", NoteGeneration.coffeeNote, NoteGeneration.flowerNote) {
    dialogArray += Dialog("Aria", "Here's your coffee, sir.", riel_neutral, aria_happy)
    dialogArray += Dialog("Gardener", "About time! Thanks for the help, I really appreciate it.", riel_neutral, aria_happy)
    dialogArray += Dialog("Gardener", "As promised, here's your reward.", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "A bouquet of flowers?", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "It's so pretty!", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Gardener", "I'm glad to hear that. They were cultivated in the labs here. Takes a longer time to wilt and a shorter time to grow.", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Aria", "I see...", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "In any case, thank you for the flowers!", riel_neutral, aria_happy)
    dialogArray += Dialog("Gardener", "No need to thank me, I'm the one who should thank you for the coffee instead.", riel_neutral, aria_happy)
  }

  val coffeeQuest3 = new Cutscene(floorSixGardenLeftBG, "coffeeQuest3") {
    dialogArray += Dialog("Aria", "Thanks for the flowers again!", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Gardener", "No problem. Good luck with whatever you're here for!", riel_neutral, aria_v_happy)
  }

  val employeeGossip1 = new NoteCutscene(floorOneLiftBG, "employeeGossip1", NoteGeneration.presidentNote) {
    dialogArray += Dialog("Employee 1", "Have you heard about that one high priority project in the R&D department?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "The one about resurrecting the dead?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "More like replicating the dead, but yes. Just what is the president thinking?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "It's not like we're in R&D, so why are you kicking up a fuss?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "We're talking about resurrecting the dead here! The president must be a madman for pouring that much money into that project.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "A madman, huh? You should've known that when he proposed that project about building an elevator to space.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "...You know what? You've got a point.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "A project about resurrecting the dead? Is this about...?", riel_neutral, aria_confused)
  }

  val employeeGossip2 = new NoteCutscene(floorTwoCafeBG, "employeeGossip2", NoteGeneration.cafeNote) {
    dialogArray += Dialog("Employee 1", "Ah... I needed this.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "The coffee here's heavenly, isn't it?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "Yup, feels like I can do just about anything now.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "Now that's just an exaggeration.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "Coffee, huh? I never really understood the hype...", riel_neutral, aria_sad)
  }

  val employeeGossip3 = new NoteCutscene(floorTwoToiletsBG, "employeeGossip3", NoteGeneration.presidentsSonNote) {
    dialogArray += Dialog("Employee 1", "Sigh... I've been working overtime every day just for that art exhibition next Saturday.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "Are you talking about the president's son's exhibition?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "Yep, that's the one. I just don't understand. I mean, I know he was extremely gifted in art, but...", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "It's already the fifth one this year, and it's always the same artworks.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "Well, it's probably the way the president wants to honour him.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "Art... I used to paint too...", riel_neutral, aria_sad)
  }

  val employeeGossip4 = new NoteCutscene(floorOneStairsBG, "employeeGossip4", NoteGeneration.accidentNote) {
    dialogArray += Dialog("Employee 1", "Have you heard about what happened to the president's son?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "Yeah. I heard it was a freak accident. His friend got caught up in too.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "Yup, that's what they all say. But did you know that his friend survived?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 1", "Apparently, his friend was the one who got in the way of the car. The president's son died saving her.", riel_neutral, aria_neutral)
    dialogArray += Dialog("Employee 2", "For real?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "...", riel_neutral, aria_v_sad)
  }

  val ladder = new NoteCutscene(floorOneStoreroomBG, "ladder", NoteGeneration.ladderNote) {
    dialogArray += Dialog("Aria", "Oh, there's a ladder here.", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "We can use it to reach something in a high place.", riel_neutral, aria_v_happy)
  }

  val boxQuest1 = new NoteCutscene(floorTwoStoreroomBG, "boxQuest1", NoteGeneration.boxNote) {
    dialogArray += Dialog("Aria", "The box up there's labelled 'lost and found'... Do you think there's something we can use in there?", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "It's too high up for us to reach though...", riel_neutral, aria_sad)
  }

  val boxQuest2 = new ConditionalCutscene(floorTwoStoreroomBG, "boxQuest2", NoteGeneration.ladderNote, NoteGeneration.pinkKeyCardNote) {
    dialogArray += Dialog("Aria", "We can use that ladder to reach the box!", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "There's a key card in here!", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Aria", "Maybe it'll let us go to another floor!", riel_neutral, aria_v_happy)
  }

  val boxQuest3 = new Cutscene(floorTwoStoreroomBG, "boxQuest3") {
    dialogArray += Dialog("Aria", "We already got the key card from the box.", riel_neutral, aria_neutral)
  }

  val flowerQuest1 = new NoteCutscene(floorSixGardenRightBG, "flowerQuest1", NoteGeneration.grannyNote) {
    dialogArray += Dialog("Granny", "Hello there, dearies. Are you two here to admire the garden as well?", riel_neutral, aria_neutral)
    dialogArray += Dialog("Aria", "Hi there, Granny. We're not really here to admire the garden, but the flowers are beautiful!", riel_neutral, aria_happy)
    dialogArray += Dialog("Granny", "I'm glad. My husband used to tend to the flowers, you see.", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "Used to...?", riel_neutral, aria_confused)
    dialogArray += Dialog("Granny", "He passed away a while ago. I come here to admire the flowers whenever I think of him.", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "Oh... My condolences.", riel_neutral, aria_sad)
    dialogArray += Dialog("Granny", "Thank you, deary. Sometimes I wish he could've taken me with him...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "She seems to be grieving her husband... Is there anything we can do to cheer her up?", riel_neutral, aria_sad)
  }

  val flowerQuest2 = new ConditionalCutscene(floorSixGardenRightBG, "flowerQuest2", NoteGeneration.flowerNote, NoteGeneration.blackKeyCardNote) {
    dialogArray += Dialog("Aria", "Granny, here.", riel_neutral, aria_happy)
    dialogArray += Dialog("Granny", "Oh? A bouquet? It's beautiful! Thank you, dearies!", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "You're welcome! Cheer up, Granny!", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Aria", "I'm sure Gramps wouldn't want to see you like this either.", riel_neutral, aria_happy)
    dialogArray += Dialog("Granny", "Oh, you're too kind, deary. Marigolds, calendulas and white lilies... These are mourning flowers.", riel_neutral, aria_happy)
    dialogArray += Dialog("Granny", "I'll be sure to drop it off on his grave later.", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "Oh... I didn't know they were mourning flowers. I'm sorry if they made you feel worse instead...", riel_neutral, aria_sad)
    dialogArray += Dialog("Granny", "Not at all. I'm very happy you gave this to me. To be honest...", riel_neutral, aria_sad)
    dialogArray += Dialog("Granny", "I know what the both of you are here for.", riel_neutral, aria_sad)
    dialogArray += Dialog("Riel", "...", riel_neutral, aria_sad)
    dialogArray += Dialog("Granny", "Don't worry I won't stop you. In fact...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "A black key card?", riel_neutral, aria_confused)
    dialogArray += Dialog("Granny", "It should take you to the top floor.", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "Thank you!", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Granny", "No need to thank me. I simply wish that no matter what path you choose in the end...", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Granny", "Don't regret it.", riel_neutral, aria_v_happy)
  }

  val flowerQuest3 = new Cutscene(floorSixGardenRightBG, "flowerQuest3") {
    dialogArray += Dialog("Granny", "No matter what path you choose in the end...", riel_neutral, aria_neutral)
    dialogArray += Dialog("Granny", "Don't regret it.", riel_neutral, aria_neutral)
  }

  val meetingAria = new TimedCutscene(floorOneLiftBG, "meetingAria", NoteGeneration.ariaNote, "Floor1Lift") {
    dialogArray += Dialog("Cheerful Girl", "Hi there, may I ask how I can get to the...", riel_neutral, aria_happy)
    dialogArray += Dialog("Cheerful Girl", "...Alex?", riel_neutral, aria_confused)
    dialogArray += Dialog("???", "...", riel_neutral, aria_confused)
    dialogArray += Dialog("Cheerful Girl", "Ah! My mistake. You just look like a friend of mine.", riel_neutral, aria_happy)
    dialogArray += Dialog("Cheerful Girl", "May I ask how I can get to the top floor?", riel_neutral, aria_happy)
    dialogArray += Dialog("???", "...Are you trying to get to the top floor too?", riel_talking, aria_happy)
    dialogArray += Dialog("Cheerful Girl", "Too? Could it be... You too?", riel_neutral, aria_happy)
    dialogArray += Dialog("Cheerful Girl", "Then, how about we work together to get there?", riel_neutral, aria_v_happy)
    dialogArray += Dialog("Cheerful Girl", "Though I don't know how...", riel_neutral, aria_confused)
    dialogArray += Dialog("???", "...Okay.", riel_talking, aria_confused)
    dialogArray += Dialog("Cheerful Girl", "That's great! My name's Aria, what about yours?", riel_neutral, aria_v_happy)
    dialogArray += Dialog("???", "Re:Al.", riel_talking, aria_v_happy)
    dialogArray += Dialog("Aria", "Ree-Al?", riel_neutral, aria_confused)
    dialogArray += Dialog("Aria", "Nice to meet you, Riel! I'll try my best to be helpful!", riel_neutral, aria_v_happy)
  }

  val aria1 = new TimedCutscene(floorThirteenHallwayOneBG, "aria1", NoteGeneration.ariasGoalNote, "Floor13Hallway1") {
    dialogArray += Dialog("Aria", "Um... Now that we're almost there, if you don't mind me asking, why were you trying to get to the top floor?", riel_neutral, aria_happy)
    dialogArray += Dialog("Riel", "...", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "You see, I'm actually here to talk to the president... There's something I need to tell him.", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "Something I should've told him a long time ago...", riel_neutral, aria_sad)
  }

  val aria2 = new TimedCutscene(floorThirteenHallwayTwoBG, "aria2", NoteGeneration.ariasIdentityNote, "Floor13Hallway2") {
    dialogArray += Dialog("Aria", "I used to be close friends with the president's son. His name was Alex.", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "We shared the same interest -- drawing. We used to go to a lot of different places just to draw the view or anything, really.", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "We joined so many competitions together...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "He was so talented. He won every single one of them, while I...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "...", riel_neutral, aria_v_sad)
    dialogArray += Dialog("Aria", "We joined another competition that day. He was in first place again.", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "I was so upset... I just kept walking, not even waiting for him to catch up.", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "I must've walked into the middle of traffic at some point, and before I knew it...", riel_neutral, aria_sad)
  }

  val aria3 = new TimedCutscene(floorThirteenHallwayThreeBG, "aria3", NoteGeneration.projectNote, "Floor13Hallway3") {
    dialogArray += Dialog("Aria", "...", riel_neutral, aria_v_sad)
    dialogArray += Dialog("Aria", "You're him, aren't you?", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "Alex...", riel_neutral, aria_sad)
    dialogArray += Dialog("Alex?", "...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "No, Alex is dead. You must be the product of that project everyone's been talking about, the project that's the culmination of a father's regrets.", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "...Project Revive Alex, Project Re:Al.", riel_talking, aria_sad)
    dialogArray += Dialog("Aria", "Re:Al... What are you actually here for?", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "It's not to talk to the president like I am, is it? It's something else...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "Hey, why don't we leave instead?", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "After all, we have access to the top floor now. We can come to talk to the president any time.", riel_neutral, aria_sad)
    dialogArray += Dialog("Re:Al", "I can't do that.", riel_talking, aria_sad)
  }

  val aria4 = new Cutscene(floorThirteenHallwayThreeBG, "aria4"){
    dialogArray += Dialog("Aria", "I-Is that a gun?", gun, aria_scared)
    dialogArray += Dialog("Re:Al", "I may not be the real Alex, but I carry his memories and his will. I am him, and he is me.", gun, aria_scared)
    dialogArray += Dialog("Re:Al", "That man has insulted life itself, and in turn insulted my death.", gun, aria_scared)
    dialogArray += Dialog("Re:Al", "I will punish him for his misdeeds. If you insist on stopping me, I will have no choice but to punish you as well.", gun, aria_scared)
    dialogArray += Dialog("Re:Al", "After all, you are his murderer. Your jealousy killed him.", gun, aria_scared)
    dialogArray += Dialog("Aria", "...You're right. My jealousy caused his death. It's a fact that won't change no matter how I try to atone for it.",gun, aria_scared)
    dialogArray += Dialog("Aria", "And by attempting to bring you back to life, your father has gone against everything you stood for.",gun, aria_scared)
    dialogArray += Dialog("Aria", "But he is just a father grieving for his son.",gun, aria_scared)
    dialogArray += Dialog("Re:Al", "That will not erase the things that he has done to my body.",gun, aria_scared)
    dialogArray += Dialog("Re:Al", "He is a wretched man who won't even allow the dead to rest in peace...",gun, aria_scared)
    dialogArray += Dialog("Re:Al", "And a cruel man who tosses all his failed creations away without regard.",gun, aria_scared)
    dialogArray += Dialog("Aria", "...",gun, aria_scared)
    dialogArray += Dialog("Re:Al", "His experiments have led to our creation, sentient androids that can imitate humans perfectly.",gun, aria_scared)
    dialogArray += Dialog("Re:Al", "We are at our core, humans. We may not bleed or require sustenance in the same way, but we are still alive.",gun, aria_scared)
    dialogArray += Dialog("Aria", "...Re:Al ...Alex, you've always been so kind.",gun, aria_sad)
    dialogArray += Dialog("Aria", "The only reason you're so upset is because you can't bear to see your kind suffer in silence anymore, isn't it?",gun, aria_sad)
    dialogArray += Dialog("Aria", "But violence is not the answer. Killing your father won't solve anything.",gun, aria_sad)
    dialogArray += Dialog("Aria", "I'll help you. Let's talk to him, make him see the err of his ways.",gun, aria_sad)
    dialogArray += Dialog("Aria", "We'll figure something out. I promise.",gun, aria_sad)
  }

  val aria5 = new ConditionalCutscene(floorThirteenHallwayThreeBG, "aria5", NoteGeneration.ariaNote, NoteGeneration.revengeNote){
    dialogArray += Dialog("", "...", gun, aria_dead)
    dialogArray += Dialog("", "The murderer is now dead.", gun, aria_dead)
  }

  val aria6 = new ConditionalCutscene(floorThirteenHallwayThreeBG, "aria6", NoteGeneration.ariaNote, NoteGeneration.mercyNote){
    dialogArray += Dialog("Re:Al", "...Okay. I suppose I can listen to your plans for now.", gun, aria_v_sad)
    dialogArray += Dialog("Aria", "! Thank you, Alex.", gun, aria_v_happy)
    dialogArray += Dialog("Re:Al", "But if it doesn't work, I'm doing things my way.", gun, aria_v_happy)
    dialogArray += Dialog("Aria", "Don't worry! Just leave it to me.", gun, aria_happy)
    dialogArray += Dialog("Aria", "For now, put your gun away, and let's talk to him first.", gun, aria_sad)
  }

  val liftTwoQuest1 = new Cutscene(floorTwoLiftBG, "liftTwoQuest1") {
    dialogArray += Dialog("Aria", "It seems like we need a special key card to go to a higher floor.", riel_neutral, aria_confused)
  }

  val liftTwoQuest2 = new ConditionalTimedCutscene(floorTwoLiftBG, "liftTwoQuest2", NoteGeneration.pinkKeyCardNote, NoteGeneration.sixthFloorNote, "Floor6Lift") {
    dialogArray += Dialog("Aria", "Let's try the key card we just found!", riel_neutral, aria_happy)
    dialogArray += Dialog("Aria", "Oh? Is this place... a garden?", riel_neutral, aria_confused)
  }

  val liftSixQuest1 = new Cutscene(floorSixLiftBG, "liftSixQuest1") {
    dialogArray += Dialog("Aria", "We need another special key card to go to a higher floor.", riel_neutral, aria_confused)
  }

  val liftSixQuest2 = new ConditionalTimedCutscene(floorSixLiftBG, "liftSixQuest2", NoteGeneration.blackKeyCardNote, NoteGeneration.thirteenthFloorNote, "Floor13Lift") {
    dialogArray += Dialog("Aria", "The top floor, huh...", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "We're almost there now...", riel_neutral, aria_sad)
  }

  val ending1 = new ConditionalTimedCutscene(floorThirteenOfficeBG, "ending1", NoteGeneration.revengeNote, NoteGeneration.justiceNote, "Ending"){
    dialogArray += Dialog("Re:Al", "Issac Freud, you have commited vile acts in the name of grief, playing god and toying with life itself...", riel_talking, null)
    dialogArray += Dialog("Re:Al", "You have cut apart your son's body time and time again, putting him back together only to watch him fall apart.", riel_talking, null)
    dialogArray += Dialog("Re:Al", "You have not allowed him a single moment of rest even in death.", riel_talking, null)
    dialogArray += Dialog("Re:Al", "I, as the materialisation of his will, will punish you in his stead.", riel_talking, null)
    dialogArray += Dialog("Re:Al", "May he and all the lives you've ruined rest in peace while you burn in the infernos of hell.", riel_talking, null)
    dialogArray += Dialog("Re:Al", "Farewell.", riel_talking, null)
  }

  val ending2 = new ConditionalTimedCutscene(floorThirteenOfficeBG, "ending2", NoteGeneration.mercyNote, NoteGeneration.forgivenessNote, "Ending"){
    dialogArray += Dialog("Aria", "Um, Mr. Freud... I'm Aria, Alex's friend.", riel_neutral, aria_sad)
    dialogArray += Dialog("Aria", "There's something I'd like to tell you...", riel_neutral, aria_sad)
  }

  val newGame = new Cutscene(floorOneLobbyBG, "newGame") {
    dialogArray += Dialog("System", "Use mouse to navigate and interact with objects. Advance cutscenes by clicking on the screen.", null, null)
    dialogArray += Dialog("System", "Sometimes, interacting with objects will grant you a note. You may look at all your notes in the menu.", null, null)
    dialogArray += Dialog("System", "Interacting with certain objects after obtaining a note may open up a path that is previously inaccessible.", null, null)
    dialogArray += Dialog("???", "He is here. On the top floor.", riel_talking, null)
    dialogArray += Dialog("???", "Get to him.", riel_talking, null)
  }

  // cutscenes that are connected to each other
  val coffeeQuest = new ConnectedCutscenes(coffeeQuest1, coffeeQuest2, coffeeQuest3, "coffeeQuest")
  val cafe = new ConnectedCutscenes(cafe1, cafe2, cafe3, "cafe")
  val boxQuest = new ConnectedCutscenes(boxQuest1, boxQuest2, boxQuest3, "boxQuest")
  val flowerQuest = new ConnectedCutscenes(flowerQuest1, flowerQuest2, flowerQuest3, "flowerQuest")
  val liftTwoQuest = new ConnectedCutscenes(liftTwoQuest1, liftTwoQuest2, "liftTwoQuest")
  val liftSixQuest = new ConnectedCutscenes(liftSixQuest1, liftSixQuest2, "liftSixQuest")

  val ending = new BranchingCutscenes(ending1, ending2, "ending")

  val cutsceneArray = Array(coffeeQuest, cafe, boxQuest, ladder, employeeGossip1, employeeGossip2,
    employeeGossip3, employeeGossip4, door1, door2, lobby, stairs, flowerQuest, meetingAria, aria1, aria2, aria3, aria4, aria5,
    liftTwoQuest, liftSixQuest, newGame, ending)

  // to retrieve the cutscene based on the id
  def retrieveCutscene(): Cutscene = {
    var cutscene: Cutscene = null

    // loop through the cutsceneArray and fetch the corresponding cutscene based on the retrieveID
    for (c <- cutsceneArray) {
      c match {
        case a: ConnectedCutscenes =>
          if (a.id == retrieveID) {
            cutscene = a.getCutscene()
          }

        case b: BranchingCutscenes =>
          if (b.id == retrieveID) {
            cutscene = b.getBranch
          }

        case c: Cutscene =>
          if (c.id == retrieveID) {
            cutscene = c
          }
      }
    }
    cutscene
  }

  // to reset all cutscenes to never being triggered before when the player starts a new game
  def resetCutscenes(): Unit = {
    for (c <- cutsceneArray) {
      c match {
        case a: ConnectedCutscenes =>
          for (n <- Array[Cutscene](a.cutscene1, a.cutscene2, a.cutscene3)) {
            if (n != null) {
              n.seen = false
            }
          }

        case b: BranchingCutscenes =>
          for (b <- Array[Cutscene](b.cutscene1, b.cutscene2)) {
            b.seen = false
          }

        case c: Cutscene =>
          c.seen = false

        case _ =>
      }
    }
  }

  // to save the player's progress, remembering which cutscenes have been triggered before and which have not
  def saveSeenCutscenes(): Unit = {
    // Source: https://openai.com/chatgpt/
    // to delete all contents of a table
    DB autoCommit { implicit session =>
      sql"""
        delete from cutscenes
         """.execute.apply()
    }

    for (c <- cutsceneArray) {
      c match {
        case a: ConnectedCutscenes =>
          for (s <- Array[Cutscene](a.cutscene1, a.cutscene2, a.cutscene3)) {
            if (s != null) {
              s.saveCutsceneState
            }
          }

        case b: BranchingCutscenes =>
          for (c <- Array[Cutscene](b.cutscene1, b.cutscene2)) {
            c.saveCutsceneState
          }

        case n: Cutscene =>
          n.saveCutsceneState

        case _ =>
      }
    }
  }

  // to load the player's save file
  // extracts all the cutscenes from the database and toggles the cutscene to being triggered before if the player
  // has triggered in before in their save file
  def loadSeenCutscenes(): Unit = {
    DB readOnly  { implicit session =>
      val cutscenes =
        sql"""
        select * from cutscenes
      """.map(rs => (rs.string("cutsceneID"), rs.string("seen"))).list.apply()

      for ((cutsceneID, seen) <- cutscenes) {
        for (c <- cutsceneArray) {
          c match {
            case a: ConnectedCutscenes =>
              for (s <- Array[Cutscene](a.cutscene1, a.cutscene2, a.cutscene3)) {
                if (s != null) {
                  if (cutsceneID == s.id) {
                    s.loadCutsceneState(seen)
                  }
                }
              }

            case b: BranchingCutscenes =>
              for (c <- Array[Cutscene](b.cutscene1, b.cutscene2)) {
                if (cutsceneID == c.id) {
                  c.loadCutsceneState(seen)
                }
              }

            case n: Cutscene =>
              if (cutsceneID == n.id) {
                n.loadCutsceneState(seen)
              }

            case _ =>
          }
        }
      }
    }
  }
}
