package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.patches.CombatUpdatePatch;

public class Jitterbug extends CustomCard {
	public static final String ID = "exordian_avenger:jitterbug";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betapower.png";
	private static final int COST = 0;
	public static final int DEXTERITY = 1;
	public static final int VULNERABLE = 2;

	public Jitterbug() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = 1;
		this.exhaust = true;
		this.isEthereal = true;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		upgradeName();
		upgradeMagicNumber(+1);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEXTERITY), DEXTERITY));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, VULNERABLE, false), VULNERABLE));
		CombatUpdatePatch.recurrentPile.addToBottom(this);
		CombatUpdatePatch.counter.add(2);
	}

}