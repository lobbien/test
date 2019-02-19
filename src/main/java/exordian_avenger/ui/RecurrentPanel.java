package exordian_avenger.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import exordian_avenger.Exordian_avenger;
import exordian_avenger.patches.CombatUpdatePatch;
import exordian_avenger.patches.RecurrentScreenEnum;
import exordian_avenger.vfx.RecurrentPileParticle;

public class RecurrentPanel extends AbstractPanel {
	  private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Recursive Tip");
	  public static final String[] MSG = tutorialStrings.TEXT;
	  public static final String[] LABEL = tutorialStrings.LABEL;
	  public static float fontScale = 1.0F;
	  public static final float FONT_POP_SCALE = 2.0F;
	  private static final float COUNT_CIRCLE_W = 100.0F * Settings.scale;
	  public static int totalCount = 0;
	  private GlyphLayout gl = new GlyphLayout();
	  private Hitbox hb = new Hitbox(0.0F, 0.0F, 100.0F * Settings.scale, 100.0F * Settings.scale);
	  public static float energyVfxTimer = 0.0F;
	  public static final float ENERGY_VFX_TIME = 2.0F;	
	  public RecurrentPanel()
		  {
		    super(Settings.WIDTH - 180.0F * Settings.scale, 80.0F * Settings.scale, Settings.WIDTH + 100.0F * Settings.scale, 184.0F * Settings.scale, 0.0F, 0.0F, null, false);
		  }
		  
		  public void updatePositions()
		  {
		    super.updatePositions();
		    if ((!this.isHidden) && 
		      (CombatUpdatePatch.recurrentPile.size() > 0))
		    {
		      this.hb.update();
		      updateVfx();
		    }
		    if ((this.hb.hovered) && ((!AbstractDungeon.isScreenUp) || (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) || (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT) || ((AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD) && (AbstractDungeon.overlayMenu.combatPanelsShown))))
		    {
		      AbstractDungeon.overlayMenu.hoveredTip = true;
		      if (InputHelper.justClickedLeft) {
		        this.hb.clickStarted = true;
		      }
		    }
		    if (((this.hb.clicked) || (InputActionSet.exhaustPile.isJustPressed()) || 
		      (CInputActionSet.pageRightViewExhaust.isJustPressed())) && (AbstractDungeon.screen == RecurrentScreenEnum.RECURRENT_VIEW))
		    {
		      this.hb.clicked = false;
		      this.hb.hovered = false;
		      CardCrawlGame.sound.play("DECK_CLOSE");
		      AbstractDungeon.closeCurrentScreen();
		      return;
		    }
		    if (((this.hb.clicked) || (InputActionSet.exhaustPile.isJustPressed()) || 
		      (CInputActionSet.pageRightViewExhaust.isJustPressed())) && (AbstractDungeon.overlayMenu.combatPanelsShown) && (AbstractDungeon.getMonsters() != null) && 
		      (!AbstractDungeon.getMonsters().areMonstersDead()) && (!AbstractDungeon.player.isDead))
		    {
		      this.hb.clicked = false;
		      this.hb.hovered = false;
		      if (AbstractDungeon.isScreenUp)
		      {
		        if (AbstractDungeon.previousScreen == null) {
		          AbstractDungeon.previousScreen = AbstractDungeon.screen;
		        }
		      }
		      else {
		        AbstractDungeon.previousScreen = null;
		      }
		      openRecurrentPile();
		    }
		  }
		  
		  private void openRecurrentPile()
		  {
		    AbstractDungeon.dynamicBanner.hide();
		    exordian_avenger.patches.AbstractDungeonUpdatePatch.viewscreen.open();
		    this.hb.hovered = false;
		    InputHelper.justClickedLeft = false;
		  }
		  
		  private void updateVfx()
		  {
		    energyVfxTimer -= Gdx.graphics.getDeltaTime();
		    if (energyVfxTimer < 0.0F)
		    {
		      AbstractDungeon.effectList.add(new RecurrentPileParticle(this.current_x, this.current_y));
		      energyVfxTimer = 0.1F;
		    }
		  }
		  
		  public void render(SpriteBatch sb)
		  {
		    if ((!Settings.hideLowerElements) && (!CombatUpdatePatch.recurrentPile.isEmpty()))
		    {
		      this.hb.move(this.current_x, this.current_y);
		      
		      String msg = Integer.toString(CombatUpdatePatch.recurrentPile.size());
		      this.gl.setText(FontHelper.deckCountFont, msg);
		      sb.setColor(Color.WHITE);
		      sb.draw(Exordian_avenger.MOBIUS, this.current_x - COUNT_CIRCLE_W / 2.0F, this.current_y - COUNT_CIRCLE_W / 2.0F, COUNT_CIRCLE_W, COUNT_CIRCLE_W);
		      
		      FontHelper.renderFontCentered(sb, FontHelper.deckCountFont, msg, this.current_x, this.current_y + 2.0F * Settings.scale, Color.WHITE
		      
		        .cpy());
		      if (Settings.isControllerMode)
		      {
		        sb.setColor(Color.WHITE);
		        sb.draw(CInputActionSet.pageRightViewExhaust
		          .getKeyImg(), this.current_x - 64.0F + 30.0F * Settings.scale, this.current_y - 64.0F - 30.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 64, 64, false, false);
		      }
		      this.hb.render(sb);
		      if ((this.hb.hovered) && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) && (!AbstractDungeon.isScreenUp)) {
		        TipHelper.renderGenericTip(1550.0F * Settings.scale, 450.0F * Settings.scale, LABEL[0] + " (" + InputActionSet.exhaustPile
		        
		          .getKeyString() + ")", MSG[0]);
		      }
		    }
		  }
		

}


