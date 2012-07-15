package com.antsapps.triples.backend;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.common.collect.Lists;

public class Application extends OnStateChangedReporter {
  private static final String TAG = "Application";

  private static Application INSTANCE;

  /** Should remain sorted */
  private final List<Game> mGames;

  public final DBAdapter database;

  private Application(Context context) {
    super();
    mGames = Lists.newArrayList();
    database = new DBAdapter(context);
    init();
  }

  public static Application getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new Application(context);
    }
    return INSTANCE;  }

  private void init() {
    database.initialize(mGames);
  }

  public void addGame(Game game) {
    game.setId(database.addGame(game));
    mGames.add(game);
    Log.i(TAG, "addGame. now mGames = " + mGames);
    notifyStateChanged();
  }

  public void saveGame(Game game) {
    database.updateGame(game);
    notifyStateChanged();
  }

  public void deleteGame(Game game) {
    mGames.remove(game);
    database.removeGame(game);
    notifyStateChanged();
  }

  public List<Game> getAllGames() {
    return mGames;
  }

  public Game getGame(long id) {
    for(Game game : mGames){
      if(game.getId() == id){
        return game;
      }
    }
    return null;
  }

  public List<Game> getCurrentGames() {
    return mGames;
  }

  public List<Game> getCompletedGames() {
    return mGames;
  }
}
