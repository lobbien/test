package exordian_avenger;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import exordian_avenger.relics.*;
import exordian_avenger.cards.*;
import exordian_avenger.chars.The_Avenger;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.patches.CombatUpdatePatch;
import exordian_avenger.patches.PlayerClassEnum;


@SpireInitializer
public class Exordian_avenger implements PostInitializeSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditCardsSubscriber, EditCharactersSubscriber, PostDrawSubscriber,OnStartBattleSubscriber  {
	final Logger logger = LogManager.getLogger(Exordian_avenger.class.getName());
	public static final String ASSETS_FOLDER = "img";
	
	public static TextureAtlas particleAtlas;
	public static Color DARK_RED = Color.FIREBRICK;
	public static Texture MOBIUS;
	public static Texture PARTICLES;
	public static TextureRegion PARTICLESREGION;
	public Exordian_avenger() {
		 
		 	BaseMod.subscribe(this);
		 	BaseMod.addColor(AbstractCardEnum.EX_DARK_RED,
		 			DARK_RED, 
		 			DARK_RED, 
		 			DARK_RED, 
		 			DARK_RED, 
		 			DARK_RED, 
		 			DARK_RED, 
		 			DARK_RED, 
		 			"tea/img/cardui/bg_attack_darkredattack_512.png", 
		 			"tea/img/cardui/bg_skill_darkredskill_512.png", 
		 			"tea/img/cardui/bg_power_darkredpower_512.png",
		 			"tea/img/cardui/card_darkredorb_512.png",
		 			"tea/img/cardui/bg_attack_darkredattack.png",
		 			"tea/img/cardui/bg_skill_darkredskill.png", 
		 			"tea/img/cardui/bg_power_darkredpower.png",
		 			"tea/img/cardui/darkredorb.png", 
		 			"tea/img/cardui/description_darkredorb.png");
	        	    }
	 	@SuppressWarnings("unused")
	    public static void initialize() {
	    	
	        Exordian_avenger exordian_avenger = new Exordian_avenger();
	       
	    }
	    	    
		@Override
		public void receiveEditRelics() {
			logger.info("Adding Relics");
			BaseMod.addRelic(new exordian_aegis(), RelicType.SHARED);	
		}
		
		@Override
		public void receivePostInitialize()
		{
			MOBIUS = ImageMaster.loadImage("tea/img/ui/recursive_cluster.png");
			PARTICLES = ImageMaster.loadImage("tea/img/particles/recpart.png");
			PARTICLESREGION = new TextureRegion(PARTICLES);
		}
		@Override
	    public void receiveEditStrings() {   
	        String relicStrings = Gdx.files.internal("tea/localization/eng/relics.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	        String cardStrings = Gdx.files.internal("tea/localization/eng/cards.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
	        String powerStrings = Gdx.files.internal("tea/localization/eng/powers.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
	        String tutorialStrings = Gdx.files.internal("tea/localization/eng/tutorials.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
	        String uiStrings = Gdx.files.internal("tea/localization/eng/ui.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
	        String eventStrings = Gdx.files.internal("tea/localization/eng/events.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
	        String characterStrings = Gdx.files.internal("tea/localization/eng/chars.json").readString(String.valueOf(StandardCharsets.UTF_8));
	        BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
		}

		@Override
		public void receiveEditCards() {
			logger.info("Adding Cards");
			BaseMod.addCard(new Perfectionism());
			BaseMod.addCard(new Curlup());
			BaseMod.addCard(new Defend());
			BaseMod.addCard(new Strike());
			BaseMod.addCard(new Counter());
			BaseMod.addCard(new Grow());
			BaseMod.addCard(new Silence());
			BaseMod.addCard(new HeroicStance());
			BaseMod.addCard(new Mania());
			BaseMod.addCard(new SilentNight());
			BaseMod.addCard(new Gnaw());
			BaseMod.addCard(new Chew());
			BaseMod.addCard(new Bubble());
			BaseMod.addCard(new ThirdHand());
			BaseMod.addCard(new ActCute());
			BaseMod.addCard(new Scratch());
			BaseMod.addCard(new Outrun());
			BaseMod.addCard(new PoisonSpores());
			BaseMod.addCard(new Jitterbug());
			BaseMod.addCard(new ExhaustingSlash());
			BaseMod.addCard(new AwakenAegis());
			BaseMod.addCard(new RenegadeSoundwave());
			BaseMod.addCard(new BackSnap());
			BaseMod.addCard(new SneakyEntrance());
		}
		@Override
		public void receivePostDraw(AbstractCard c) {
			if(c.cardID == "exordian_avenger:spores")
			{
				((PoisonSpores) c).magicUp();
				c.initializeDescription();
			}			
		}
		@Override
		public void receiveEditCharacters()
		{
			BaseMod.addCharacter(new The_Avenger("The Avenger", PlayerClassEnum.THE_AVENGER), "tea/img/chars/button.png", "tea/img/chars/Charselect_final.png", PlayerClassEnum.THE_AVENGER);
		}
		@Override
		public void receiveOnBattleStart(AbstractRoom arg0) {
			CombatUpdatePatch.recurrentPile.clear();
			CombatUpdatePatch.counter.clear();
		}

}
