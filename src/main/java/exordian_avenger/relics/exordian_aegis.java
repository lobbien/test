package exordian_avenger.relics;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import exordian_avenger.Exordian_avenger;
import exordian_avenger.patches.AbstractDungeonPatches;

public class exordian_aegis extends CustomRelic implements CustomSavable<Integer[][]> {

	public static final String ID = "exordian_avenger:exordian_aegis";
	public static final String IMG = "tea/img/relics/exordian_aegis.png";
	private boolean tookatkdamage = true;
	private boolean iselitecombat = false;
	private boolean playedfourskillslastturn = false;
	private boolean playedfourskillsthisturn = false;
	private boolean gotplatedarmorthisturn = false;
	private int damagetaken;
	private int dexdown;
	private int skillsplayed;
	private int currentcombat;
	private int currentturn;
	private ArrayList<Integer> Upgrades = new ArrayList<Integer>(1);
	private ArrayList<Integer> tempdex = new ArrayList<Integer>(1);
	private ArrayList<Integer> tempblock = new ArrayList<Integer>(1);
	private ArrayList<Integer> tempstdown = new ArrayList<Integer>(1);
	private int upgradenumber;

	public exordian_aegis() {

		super(ID, new Texture(IMG), RelicTier.BOSS, LandingSound.MAGICAL);
	}

	final Logger logger = (Logger) LogManager.getLogger(Exordian_avenger.class.getName());

	@Override
	public String getUpdatedDescription() {
		String Description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
		return Description;
	}

	private void setDescriptionAfterLoading() {
		String Description = null;
		if (Upgrades != null && Upgrades.isEmpty() == false) {
			for (int i = 0; i < Upgrades.size(); i++) {

				Description = Description + DESCRIPTIONS[Upgrades.get(i)];
			}
		}
		this.description = this.description + Description;
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
	}

	@Override
	public void onEquip() {
		tookatkdamage = false;
		UpgradeRelic(2);
	}

	private void UpgradeRelic(int number) {
		Random rand = new Random();

		for (int i = 0; i < number; i++) {
			int n = rand.nextInt(12) + 2;
			Upgrades.add(n);
			if (n == 4) {
				tempdex.add(1);
			}
			if (n == 9) {
				tempblock.add(0);
			}
			if (n == 10) {
				tempstdown.add(0);
			}
		}

		// TEMP!!!///////////////////
		/*
		 * for (int i = 2; i <= 14; i++) { Upgrades.add(i); if (i == 4) {
		 * tempdex.add(1); } if (i == 9) { tempblock.add(0); } if (i == 10) {
		 * tempstdown.add(0); } }
		 */
		///////////////////////////

		String Description = new String();
		for (int i = upgradenumber; i < Upgrades.size(); i++) {
			Description = Description + DESCRIPTIONS[Upgrades.get(i)];
		}
		this.description = this.description + Description;
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
		upgradenumber += number;
	}

	@Override
	public void atBattleStart() {
		iselitecombat = false;
		if (AbstractDungeon.getCurrRoom().eliteTrigger) {
			iselitecombat = true;
		}

		if (currentcombat == 0) {
			currentcombat = 1;
		} else {
			currentcombat = 0;
		}
		currentturn = 0;

		flash();
		tookatkdamage = false;
		dexdown = 0;
		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		for (int i = 0; i < Upgrades.size(); i++) {
			if (Upgrades.get(i) == 5) {
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
						AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 1), 1));
			}
			if (Upgrades.get(i) == 3) {
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
						AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, 1), 1));
			}
			if (Upgrades.get(i) == 2) {
				AbstractDungeon.actionManager
						.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 3));
			}
			if (Upgrades.get(i) == 7) {
				AbstractCard c = AbstractDungeonPatches.returnTrulyRandomCommonCardInCombat(AbstractCard.CardType.SKILL)
						.makeCopy();
				if (c.cost != -1 && c.cost != 0) {
					c.cost = 0;
				}
				UnlockTracker.markCardAsSeen(c.cardID);
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
			}
		}
	}

	@Override
	public void atBattleStartPreDraw() {
		AbstractPlayer player = AbstractDungeon.player;

		CardGroup drawPile = player.drawPile.getUpgradableCards();
		CardGroup skills = drawPile.getSkills();
		if (skills.size() > 0) {
			for (int a = 0; a < Upgrades.size() && a < skills.size(); a++) {
				if (Upgrades.get(a) == 8) {
					AbstractCard card = skills.group.get(AbstractDungeon.miscRng.random(skills.size() - 1));
					card.upgrade();
					AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
					drawPile = player.drawPile.getUpgradableCards();
				}
			}
		}
	}

	@Override
	public void atTurnStart() {
		playedfourskillslastturn = playedfourskillsthisturn;
		playedfourskillsthisturn = false;
		gotplatedarmorthisturn = false;
		skillsplayed = 0;
		if (currentcombat == 1 && currentturn == 5) {
			for (int i = 0; i < Upgrades.size(); i++) {
				if (Upgrades.get(i) == 13) {
					AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
							AbstractDungeon.player, new IntangiblePower(AbstractDungeon.player, 1), 1));
				}
			}
		}
		currentturn++;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
				new DexterityPower(AbstractDungeon.player, -dexdown), -dexdown));
		dexdown = 0;
		for (int i = 0; i < tempdex.size(); i++) {
			if (tempdex.get(i) == 2) {
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
						AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
				dexdown++;
				tempdex.set(i, 0);
			} else if (tempdex.get(i) == 0) {
				tempdex.set(i, 1);
			} else if (tempdex.get(i) == 1) {
				tempdex.set(i, 2);
			}

		}
		for (int i = 0; i < tempblock.size(); i++) {
			if (tempblock.get(i) == 2) {
				AbstractDungeon.actionManager
						.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
				tempblock.set(i, 0);
			} else {
				tempblock.set(i, tempblock.get(i) + 1);
			}
		}
		for (int i = 0; i < tempstdown.size(); i++) {
			if (tempstdown.get(i) == 5) {
				AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true,
						AbstractDungeon.cardRandomRng);
				AbstractDungeon.actionManager
						.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -1), -1));
				tempstdown.set(i, 0);
			} else {
				tempstdown.set(i, tempstdown.get(i) + 1);
			}
		}
	}

	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {

		if ((info.owner != null) && (info.type != DamageInfo.DamageType.THORNS)
				&& (info.type != DamageInfo.DamageType.HP_LOSS) && (damageAmount > 0)
				&& (!(info.owner.hasPower("Buffer")))) {
			flash();
			tookatkdamage = true;
			return damageAmount;
		}
		return damageAmount;
	}

	@Override
	public void onLoseHp(int damageAmount) {
		if (damageAmount > 0) {
			damagetaken = damageAmount;
		}
	}

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card.type == AbstractCard.CardType.SKILL) {
			if (playedfourskillsthisturn == false && gotplatedarmorthisturn == false) {
				skillsplayed++;
				if (skillsplayed == 4) {
					if (playedfourskillslastturn == true) {
						for (int i = 0; i < Upgrades.size(); i++) {
							if (Upgrades.get(i) == 12) {
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,
										AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 2), 2));
							}
						}
					} else {
						playedfourskillsthisturn = true;
					}
				}
			}
		}
	}

	@Override
	public void onVictory() {
		flash();
		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		logger.info("beforeloop");
		int healamt = damagetaken;
		for (int i = 0; i < Upgrades.size(); i++) {
			if (Upgrades.get(i) == 6) {
				if (healamt > 5) {
					if (AbstractDungeon.player.currentHealth > 0) {
						AbstractDungeon.player.heal(5);
						healamt = healamt - 5;
					}
				} else {
					if (AbstractDungeon.player.currentHealth > 0) {
						AbstractDungeon.player.heal(healamt);
						healamt = 0;
						break;
					}
				}
			}
		}
		if (iselitecombat == true) {
			if (AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 5) {
				UpgradeRelic(2);
			}
			if (tookatkdamage == false) {
				UpgradeRelic(1);
			}
			damagetaken = 0;
		}
		logger.info("afterloop");
	}

	@Override
	public void onPlayerEndTurn() {
		if ((AbstractDungeon.player.currentBlock == 0)) {
			flash();
			for (int i = 0; i < Upgrades.size(); i++) {
				if (Upgrades.get(i) == 14) {
					AbstractDungeon.actionManager
							.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
					AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				}
			}

		}
	}

	@Override
	public Integer[][] onSave() {
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Integer[][] save = new Integer[Upgrades.size() + 3][5];
		save[0][0] = Upgrades.size();
		for (int i = 0; i < Upgrades.size(); i++) {
			save[i + 1][0] = Upgrades.get(i);
		}

		save[0][1] = tempblock.size();
		for (int i = 0; i < tempblock.size(); i++) {
			save[i + 1][1] = tempblock.get(i);
		}

		save[0][2] = tempdex.size();
		for (int i = 0; i < tempdex.size(); i++) {
			save[i + 1][2] = tempdex.get(i);
		}
		save[0][3] = tempstdown.size();
		for (int i = 0; i < tempstdown.size(); i++) {
			save[i + 1][3] = tempstdown.get(i);
		}

		save[0][4] = dexdown;
		save[1][4] = currentcombat;
		save[2][4] = upgradenumber;
		// String numbers = save[1][0].toString();
		// logger.info(numbers);
		return save;
	}

	@Override
	public void onLoad(Integer[][] Lists) {
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		logger.info("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");

		logger.info(Lists.toString());
		for (int i = 1; i <= Lists[0][0]; i++) {
			Upgrades.add(Lists[i][0]);
		}
		for (int i = 1; i <= Lists[0][1]; i++) {
			tempblock.add(Lists[i][1]);
		}
		for (int i = 1; i <= Lists[0][2]; i++) {
			tempdex.add(Lists[i][2]);
		}
		for (int i = 1; i <= Lists[0][3]; i++) {
			tempstdown.add(Lists[i][3]);
		}

		dexdown = Lists[0][4];
		currentcombat = Lists[1][4];
		upgradenumber = Lists[2][4];

		setDescriptionAfterLoading();
	}

	@Override
	public AbstractRelic makeCopy() {
		return new exordian_aegis();
	}
	
	@Override
	public Type savedType()
    {
        return new TypeToken<Integer[][]>(){}.getType();
    }
	/*
	 * @Override public void onLoadRaw(JsonElement arg0) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public JsonElement onSaveRaw() { // TODO Auto-generated method
	 * stub return null; }
	 */

}
