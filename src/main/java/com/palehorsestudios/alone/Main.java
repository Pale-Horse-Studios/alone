package com.palehorsestudios.alone;

import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.activity.BoostMoraleActivity;
import com.palehorsestudios.alone.activity.BuildFireActivity;
import com.palehorsestudios.alone.activity.DrinkWaterActivity;
import com.palehorsestudios.alone.activity.EatActivity;
import com.palehorsestudios.alone.activity.FishActivity;
import com.palehorsestudios.alone.activity.ForageActivity;
import com.palehorsestudios.alone.activity.GatherFirewoodActivity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.activity.GetWaterActivity;
import com.palehorsestudios.alone.activity.HuntActivity;
import com.palehorsestudios.alone.activity.ImproveShelterActivity;
import com.palehorsestudios.alone.activity.PutItemActivity;
import com.palehorsestudios.alone.activity.RestActivity;
import com.palehorsestudios.alone.activity.TrapActivity;
import com.palehorsestudios.alone.gui.GameApp;
import com.palehorsestudios.alone.player.Player;
import javafx.application.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
  private static GameAssets game;

  public static void main(String[] args) {
    game = new GameAssets();
    game.loadAssets();
    Application.launch(GameApp.class, args);
  }

  public static Choice parseChoice(String input, Player player) {
    Choice choice;

    // uses input to build a choice by looking up keywords in a choice map
    // split up input into array of words
    String keyword;
    Food food;
    Item item;
    if (Optional.ofNullable(GameAssets.choiceKeywordMap.get(input.toLowerCase())).isPresent()) {
      keyword = Optional.ofNullable(GameAssets.choiceKeywordMap.get(input.toLowerCase())).get();
      if (keyword.equals("eat")) {
        if (Optional.ofNullable(GameAssets.choiceFoodMap.get(input.toLowerCase())).isPresent()) {
          food = Optional.ofNullable(GameAssets.choiceFoodMap.get(input.toLowerCase())).get();
          choice = new Choice(keyword, player, food);
        } else {
          choice = new Choice(keyword, player);
        }
      } else if (keyword.equals("get") || keyword.equals("put")) {
        if (Optional.ofNullable(GameAssets.choiceItemMap.get(input.toLowerCase())).isPresent()) {
          item = Optional.ofNullable(GameAssets.choiceItemMap.get(input.toLowerCase())).get();
          choice = new Choice(keyword, player, item);
        } else {
          choice = null;
        }
      } else {
        choice = new Choice(keyword, player);
      }
    } else {
      choice = null;
    }
    return choice;
  }

  public static Activity parseActivityChoice(Choice choice) {
    Activity activity;
    if (choice == null) {
      // display help menu
      activity = null;
    } else {
      if (choice.getKeyword().equals("get")) {
        activity = GetItemActivity.getInstance();
      } else if (choice.getKeyword().equals("put")) {
        activity = PutItemActivity.getInstance();
      } else if (choice.getKeyword().equals("eat")) {
        activity = EatActivity.getInstance();
      } else if (choice.getKeyword().equals("drink")) {
        activity = DrinkWaterActivity.getInstance();
      } else if (choice.getKeyword().equals("fish")) {
        activity = FishActivity.getInstance();
      } else if (choice.getKeyword().equals("hunt")) {
        activity = HuntActivity.getInstance();
      } else if (choice.getKeyword().equals("trap")) {
        activity = TrapActivity.getInstance();
      } else if (choice.getKeyword().equals("forage")) {
        activity = ForageActivity.getInstance();
      } else if (choice.getKeyword().equals("improve")) {
        activity = ImproveShelterActivity.getInstance();
      } else if (choice.getKeyword().equals("gather")) {
        activity = GatherFirewoodActivity.getInstance();
      } else if (choice.getKeyword().equals("fire")) {
        activity = BuildFireActivity.getInstance();
      } else if (choice.getKeyword().equals("water")) {
        activity = GetWaterActivity.getInstance();
      } else if (choice.getKeyword().equals("morale")) {
        activity = BoostMoraleActivity.getInstance();
      } else {
        activity = RestActivity.getInstance();
      }
    }
    return activity;
  }

}
