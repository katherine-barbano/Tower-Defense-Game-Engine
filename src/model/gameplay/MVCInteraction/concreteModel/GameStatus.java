package model.gameplay.MVCInteraction.concreteModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import model.gameplay.MVCInteraction.API.GameStatusAPI;

public class GameStatus implements GameStatusAPI {

  private final int timeInterval;
  private final int initialSun;
  private int sunAmount;
  private int timeElapsed;
  private int points;
  private int timeRate;
  private int animationSpeed;
  private double progress;

  public GameStatus(int animationRate, int initialSun) {
    sunAmount = initialSun;
    this.initialSun = initialSun;
    timeElapsed = 0;
    points = 0;
    timeInterval = animationRate / 2;
    animationSpeed = animationRate;
    timeRate = 0;
    progress = 0;
  }

  @Override
  public boolean progressComplete() {
    return progress >= 1;
  }

  @Override
  public Map getStatus() {
    Map<String, String> statusMap = new HashMap<>();
    statusMap.put("points", String.valueOf(this.points));
    statusMap.put("timeElapsed", String.valueOf(this.timeElapsed));
    return statusMap;
  }

  @Override
  public void updateProgress(double progress) {
    this.progress = progress;
  }

  @Override
  public int getAnimationSpeed() {
    return animationSpeed;
  }

  @Override
  public boolean sunLessThan(int target) {
    return sunAmount < target;
  }

  @Override
  public boolean pointsGreaterThan(int points) {
    return this.points > points;
  }

  @Override
  public void changeGameSpeed(double modifier) {
    animationSpeed *= modifier;
  }

  /**
   * @see GameStatusAPI#applySun(Consumer) 
   * @param statusUpdater
   */
  @Override
  public void applySun(Consumer<Integer> statusUpdater) {
    statusUpdater.accept(sunAmount);
  }

  /**
   * @see GameStatusAPI#applyTime(Consumer) 
   * @param statusUpdater
   */
  @Override
  public void applyTime(Consumer<Integer> statusUpdater) {
    statusUpdater.accept(timeElapsed);
  }

  /**
   * @see GameStatusAPI#applyPoints(Consumer) 
   * @param statusUpdater
   */
  @Override
  public void applyPoints(Consumer<Integer> statusUpdater) {
    statusUpdater.accept(points);
  }

  /**
   * @see GameStatusAPI#applyProgress(Consumer) 
   * @param statusUpdater
   */
  @Override
  public void applyProgress(Consumer<Double> statusUpdater) {
    statusUpdater.accept(progress);
  }

  private boolean checkTimeRate() {
    if (timeRate == timeInterval) {
      timeRate = 0;
      return true;
    } else {
      timeRate++;
      return false;
    }
  }

  @Override
  public void update() {
    if (checkTimeRate()) {
      timeElapsed++;
    }
  }


  @Override
  public boolean validateCost(int cost) {
    return sunAmount - cost >= 0;
  }

  @Override
  public void changeSunAmount(int sun) {
    sunAmount += sun;
  }

  @Override
  public void reset() {
    sunAmount = initialSun;
    timeElapsed = 0;
    points = 0;
    progress = 0;
  }

  @Override
  public void updatePoints(int increaseScore) {
    points += increaseScore;
  }

  @Override
  public int getTimeElapsed() {
    return timeElapsed;
  }
}
