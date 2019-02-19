package exordian_avenger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import exordian_avenger.Exordian_avenger;
import exordian_avenger.patches.CombatUpdatePatch;
import exordian_avenger.patches.RecurrentScreenEnum;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecurrentPileViewScreen
  implements ScrollBarListener
{
  final static Logger logger = (Logger) LogManager.getLogger(Exordian_avenger.class.getName()); 
  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecurrentViewScreen");
  public static final String[] TEXT = uiStrings.TEXT;
  private CardGroup recurrentPileCopy = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
  public boolean isHovered = false;
  private boolean grabbedScreen = false;
  private float grabStartY = 0.0F;
  private float currentDiffY = 0.0F;
  private static float drawStartX;
  private static float drawStartY;
  private static float padX;
  private static float padY;
  private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
  private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
  private static final String DESC = TEXT[0];
  private AbstractCard hoveredCard = null;
  private int prevDeckSize = 0;
  private static final float SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;
  private ScrollBar scrollBar;
  private AbstractCard controllerCard = null;
  
  public RecurrentPileViewScreen()
  {
    drawStartX = Settings.WIDTH;
    drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
    drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
    drawStartX /= 2.0F;
    drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;
    
    padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
    padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
    
    this.scrollBar = new ScrollBar(this);
    this.scrollBar.move(0.0F, -30.0F * Settings.scale);
  }
  
  public void update()
  {
    boolean isDraggingScrollBar = false;
    if (shouldShowScrollBar()) {
      isDraggingScrollBar = this.scrollBar.update();
    }
    if (!isDraggingScrollBar) {
      updateScrolling();
    }
    if (this.recurrentPileCopy.group.size() > 0) {
      updateControllerInput();
    }
    if ((Settings.isControllerMode) && (this.controllerCard != null) && (!CardCrawlGame.isPopupOpen) && (!AbstractDungeon.topPanel.selectPotionMode)) {
      if (Gdx.input.getY() > Settings.HEIGHT * 0.7F) {
        this.currentDiffY += Settings.SCROLL_SPEED;
      } else if (Gdx.input.getY() < Settings.HEIGHT * 0.3F) {
        this.currentDiffY -= Settings.SCROLL_SPEED;
      }
    }
    updatePositions();
    if ((Settings.isControllerMode) && (this.controllerCard != null)) {
      Gdx.input.setCursorPosition((int)this.controllerCard.hb.cX, (int)(Settings.HEIGHT - this.controllerCard.hb.cY));
    }
  }
  
  private void updateControllerInput()
  {
    if ((!Settings.isControllerMode) || (AbstractDungeon.topPanel.selectPotionMode)) {
      return;
    }
    boolean anyHovered = false;
    int index = 0;
    for (AbstractCard c : this.recurrentPileCopy.group)
    {
      if (c.hb.hovered)
      {
        anyHovered = true;
        break;
      }
      index++;
    }
    if (!anyHovered)
    {
      Gdx.input.setCursorPosition(
        (int)((AbstractCard)this.recurrentPileCopy.group.get(0)).hb.cX, Settings.HEIGHT - 
        (int)((AbstractCard)this.recurrentPileCopy.group.get(0)).hb.cY);
      this.controllerCard = ((AbstractCard)this.recurrentPileCopy.group.get(0));
    }
    else if (((CInputActionSet.up.isJustPressed()) || (CInputActionSet.altUp.isJustPressed())) && 
      (this.recurrentPileCopy.size() > 5))
    {
      index -= 5;
      if (index < 0)
      {
        int wrap = this.recurrentPileCopy.size() / 5;
        index += wrap * 5;
        if (index + 5 < this.recurrentPileCopy.size()) {
          index += 5;
        }
      }
      Gdx.input.setCursorPosition(
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - 
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cY);
      this.controllerCard = ((AbstractCard)this.recurrentPileCopy.group.get(index));
    }
    else if (((CInputActionSet.down.isJustPressed()) || (CInputActionSet.altDown.isJustPressed())) && 
      (this.recurrentPileCopy.size() > 5))
    {
      if (index < this.recurrentPileCopy.size() - 5) {
        index += 5;
      } else {
        index %= 5;
      }
      Gdx.input.setCursorPosition(
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - 
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cY);
      this.controllerCard = ((AbstractCard)this.recurrentPileCopy.group.get(index));
    }
    else if ((CInputActionSet.left.isJustPressed()) || (CInputActionSet.altLeft.isJustPressed()))
    {
      if (index % 5 > 0)
      {
        index--;
      }
      else
      {
        index += 4;
        if (index > this.recurrentPileCopy.size() - 1) {
          index = this.recurrentPileCopy.size() - 1;
        }
      }
      Gdx.input.setCursorPosition(
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - 
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cY);
      this.controllerCard = ((AbstractCard)this.recurrentPileCopy.group.get(index));
    }
    else if ((CInputActionSet.right.isJustPressed()) || (CInputActionSet.altRight.isJustPressed()))
    {
      if (index % 5 < 4)
      {
        index++;
        if (index > this.recurrentPileCopy.size() - 1) {
          index -= this.recurrentPileCopy.size() % 5;
        }
      }
      else
      {
        index -= 4;
        if (index < 0) {
          index = 0;
        }
      }
      Gdx.input.setCursorPosition(
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - 
        (int)((AbstractCard)this.recurrentPileCopy.group.get(index)).hb.cY);
      this.controllerCard = ((AbstractCard)this.recurrentPileCopy.group.get(index));
    }
  }
  
  private void updateScrolling()
  {
    int y = InputHelper.mY;
    if (!this.grabbedScreen)
    {
      if (InputHelper.scrolledDown) {
        this.currentDiffY += Settings.SCROLL_SPEED;
      } else if (InputHelper.scrolledUp) {
        this.currentDiffY -= Settings.SCROLL_SPEED;
      }
      if (InputHelper.justClickedLeft)
      {
        this.grabbedScreen = true;
        this.grabStartY = (y - this.currentDiffY);
      }
    }
    else if (InputHelper.isMouseDown)
    {
      this.currentDiffY = (y - this.grabStartY);
    }
    else
    {
      this.grabbedScreen = false;
    }
    if (this.prevDeckSize != this.recurrentPileCopy.size()) {
      calculateScrollBounds();
    }
    resetScrolling();
    updateBarPosition();
  }
  
  private void calculateScrollBounds()
  {
    if (this.recurrentPileCopy.size() > 10)
    {
      int scrollTmp = this.recurrentPileCopy.size() / 5 - 2;
      if (this.recurrentPileCopy.size() % 5 != 0) {
        scrollTmp++;
      }
      this.scrollUpperBound = (Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * padY);
    }
    else
    {
      this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
    }
    this.prevDeckSize = this.recurrentPileCopy.size();
  }
  
  private void resetScrolling()
  {
    if (this.currentDiffY < this.scrollLowerBound) {
      this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
    } else if (this.currentDiffY > this.scrollUpperBound) {
      this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
    }
  }
  
  private void updatePositions()
  {
    this.hoveredCard = null;
    int lineNum = 0;
    ArrayList<AbstractCard> cards = this.recurrentPileCopy.group;
    for (int i = 0; i < cards.size(); i++)
    {
      int mod = i % 5;
      if ((mod == 0) && (i != 0)) {
        lineNum++;
      }
      ((AbstractCard)cards.get(i)).target_x = (drawStartX + mod * padX);
      ((AbstractCard)cards.get(i)).target_y = (drawStartY + this.currentDiffY - lineNum * padY);
      ((AbstractCard)cards.get(i)).update();
      ((AbstractCard)cards.get(i)).updateHoverLogic();
      if (((AbstractCard)cards.get(i)).hb.hovered) {
        this.hoveredCard = ((AbstractCard)cards.get(i));
      }
    }
  }
  
  public void reopen()
  {
    AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
  }
  
  public void open()
  {
    CardCrawlGame.sound.play("DECK_OPEN");
    AbstractDungeon.overlayMenu.showBlackScreen();
    AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
    this.currentDiffY = 0.0F;
    this.grabStartY = 0.0F;
    this.grabbedScreen = false;
    AbstractDungeon.isScreenUp = true;
    AbstractDungeon.screen = RecurrentScreenEnum.RECURRENT_VIEW;
    this.recurrentPileCopy.clear();
    for (AbstractCard c : CombatUpdatePatch.recurrentPile.group)
    {
      AbstractCard toAdd = c.makeStatEquivalentCopy();
      toAdd.setAngle(0.0F, true);
      toAdd.targetDrawScale = 0.75F;
      toAdd.targetDrawScale = 0.75F;
      toAdd.drawScale = 0.75F;
      toAdd.lighten(true);
      toAdd.unfadeOut();
      this.recurrentPileCopy.addToBottom(toAdd);
    }
    if (!AbstractDungeon.player.hasRelic("Frozen Eye"))
    {
      this.recurrentPileCopy.sortAlphabetically(true);
      this.recurrentPileCopy.sortByRarityPlusStatusCardType(true);
    }
    hideCards();
    if (this.recurrentPileCopy.group.size() <= 5) {
      drawStartY = Settings.HEIGHT * 0.5F;
    } else {
      drawStartY = Settings.HEIGHT * 0.66F;
    }
    calculateScrollBounds();
  }
  
  private void hideCards()
  {
    int lineNum = 0;
    ArrayList<AbstractCard> cards = this.recurrentPileCopy.group;
    for (int i = 0; i < cards.size(); i++)
    {
      int mod = i % 5;
      if ((mod == 0) && (i != 0)) {
        lineNum++;
      }
      ((AbstractCard)cards.get(i)).current_x = (drawStartX + mod * padX);
      ((AbstractCard)cards.get(i)).current_y = (drawStartY + this.currentDiffY - lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale));
      
      ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
      ((AbstractCard)cards.get(i)).drawScale = 0.75F;
    }
  }
  
  public void render(SpriteBatch sb)
  {
	logger.info(recurrentPileCopy.size());
    if (this.hoveredCard == null)
    {
      this.recurrentPileCopy.render(sb);
    }
    else
    {
      this.recurrentPileCopy.renderExceptOneCard(sb, this.hoveredCard);
      this.hoveredCard.renderHoverShadow(sb);
      this.hoveredCard.render(sb);
      this.hoveredCard.renderCardTip(sb);
    }
    FontHelper.renderDeckViewTip(sb, DESC, 96.0F * Settings.scale, Settings.CREAM_COLOR);
    if (shouldShowScrollBar()) {
      this.scrollBar.render(sb);
    }
  }
  
  public void scrolledUsingBar(float newPercent)
  {
    this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
    updateBarPosition();
  }
  
  private void updateBarPosition()
  {
    float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
    this.scrollBar.parentScrolledToPercent(percent);
  }
  
  private boolean shouldShowScrollBar()
  {
    return this.scrollUpperBound > SCROLL_BAR_THRESHOLD;
  }
}
