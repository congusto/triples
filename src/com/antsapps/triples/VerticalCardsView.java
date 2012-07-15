package com.antsapps.triples;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.antsapps.triples.backend.Card;
import com.antsapps.triples.backend.Game;

public class VerticalCardsView extends CardsView {

  private static final float HEIGHT_OVER_WIDTH = (float) ((Math.sqrt(5) - 1) / 2);

  public static final int COLUMNS = 3;

  private static final int MIN_ROWS = Game.MIN_CARDS_IN_PLAY / COLUMNS;

  private int mWidthOfCard;

  private int mHeightOfCard;

  int mRows;

  public VerticalCardsView(Context context) {
    this(context, null);
  }

  public VerticalCardsView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void updateMeasuredDimensions(final int widthMeasureSpec,
      final int heightMeasureSpec) {
    int widthOfCards = getDefaultSize(getMeasuredWidth(), widthMeasureSpec);
    int rows = Math.max(mRows, MIN_ROWS);
    int heightOfCards = (int) (widthOfCards / COLUMNS * HEIGHT_OVER_WIDTH * rows);
    setMeasuredDimension(
        widthOfCards,
        getDefaultSize(heightOfCards, heightMeasureSpec));
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right,
      int bottom) {
    if (!changed) {
      return;
    }
    mWidthOfCard = (right - left) / COLUMNS;
    mHeightOfCard = (int) (mWidthOfCard * HEIGHT_OVER_WIDTH);
    mOffScreenLocation.set(right, bottom, right + mWidthOfCard, bottom + mHeightOfCard);
    updateBounds();
  }

  @Override
  protected Rect calcBounds(int i) {
    return new Rect(i % COLUMNS * mWidthOfCard, i / COLUMNS * mHeightOfCard, (i
        % COLUMNS + 1)
        * mWidthOfCard, (i / COLUMNS + 1) * mHeightOfCard);
  }

  @Override
  protected Card getCardForPosition(int x, int y) {
    int position = y / mHeightOfCard * COLUMNS + x / mWidthOfCard;
    if (position < mCards.size()) {
      return mCards.get(position);
    } else {
      return null;
    }
  }
}
