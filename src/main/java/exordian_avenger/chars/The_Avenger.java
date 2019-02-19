package exordian_avenger.chars;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.helpers.*;


import basemod.abstracts.CustomPlayer;
import exordian_avenger.cards.Defend;
import exordian_avenger.cards.Grow;
import exordian_avenger.cards.SneakyEntrance;
import exordian_avenger.cards.Strike;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.patches.PlayerClassEnum;
import exordian_avenger.relics.exordian_aegis;

public class The_Avenger extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;
	public static final String MY_CHARACTER_SHOULDER_2 = "tea/img/chars/shoulder2.png";
	public static final String MY_CHARACTER_SHOULDER_1 = "tea/img/chars/shoulder.png";
	public static final String MY_CHARACTER_CORPSE = "tea/img/chars/corpse.png";
	public static final String MY_CHARACTER_SKELETON_ATLAS = "tea/img/chars/idle/skeleton.atlas";
	public static final String MY_CHARACTER_SKELETON_JSON = "tea/img/chars/idle/skeleton.json";
	public static final EventStrings VAMPIRE_STRINGS = CardCrawlGame.languagePack.getEventString("VampiresAvenger");
	public static final CharacterStrings CHARACTER_STRINGS = CardCrawlGame.languagePack.getCharacterString("TEA");
	public static final String NAME = CHARACTER_STRINGS.NAMES[0];
	public static final String DESC = CHARACTER_STRINGS.TEXT[0];
	public static final String[] orbTextures = {"tea/img/orbs/orbbeta.png"};
	public static final String VFX = "tea/img/orbs/vfxbeta.png";

	


	public The_Avenger(String name, PlayerClass playerClass) {
		super(name, playerClass, orbTextures, VFX, (String)null, (String)null);
		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 150.0F * Settings.scale);
		initializeClass(null, MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1, MY_CHARACTER_CORPSE, getLoadout(),
				20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
		loadAnimation(MY_CHARACTER_SKELETON_ATLAS, MY_CHARACTER_SKELETON_JSON, 1.0F);
		//AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
		// e.setTime(e.getEndTime() * MathUtils.random());
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
	     CardCrawlGame.sound.playA("BLOOD_SWISH", MathUtils.random(-0.2F, 0.2F));
	     CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);

	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 3;
	}

	@Override
	public CardColor getCardColor() {
		return AbstractCardEnum.EX_DARK_RED;
	}

	@Override
	public Color getCardRenderColor() {
		return Color.FIREBRICK;
	}

	@Override
	public Color getCardTrailColor() {
		return Color.FIREBRICK.cpy();
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "BLOOD_SWISH";
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		 return FontHelper.energyNumFontRed;
	}

	public static final int STARTING_HP = 25;
	public static final int MAX_HP = 25;
	public static final int STARTING_GOLD = 99;
	public static final int HAND_SIZE = 5;

	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME,
				DESC, STARTING_HP, MAX_HP, 0,
				STARTING_GOLD, HAND_SIZE, this, getStartingRelics(), getStartingDeck(), false);
	}

	@Override
	public String getLocalizedCharacterName() {
		return NAME;
	}

	@Override
	public Color getSlashAttackColor() {
        return Color.FIREBRICK;
	}

	@Override
	public AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL};
	}

	@Override
	public String getSpireHeartText() {
		return  CHARACTER_STRINGS.TEXT[1];
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new Strike();
	}

	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> cretVal = new ArrayList<>();
		cretVal.add(Strike.ID);
		cretVal.add(Strike.ID);
		cretVal.add(Strike.ID);
		cretVal.add(Strike.ID);
		cretVal.add(Defend.ID);
		cretVal.add(Defend.ID);
		cretVal.add(Defend.ID);
		cretVal.add(Defend.ID);
		cretVal.add(Grow.ID);
		cretVal.add(SneakyEntrance.ID);
		return cretVal;
	}

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> rretVal = new ArrayList<String>();
		rretVal.add(exordian_aegis.ID);
		UnlockTracker.markRelicAsSeen(exordian_aegis.ID);
		return rretVal;
	}

	@Override
	public String getTitle(PlayerClass arg0) {
		return  NAME;
	}

	@Override
	public String getVampireText() {
		return VAMPIRE_STRINGS.DESCRIPTIONS[0];
	}

	@Override
	public AbstractPlayer newInstance() {
		return new The_Avenger(NAME, PlayerClassEnum.THE_AVENGER);
	}

}
