package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;

import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;

public class Defend extends CustomCard {
	public static final String ID = "exordian_avenger:defend";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betaskill.png";
	private static final int COST = 1;

	public Defend() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = 3;
		this.baseBlock = 8;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
	    upgradeName();
		upgradeBlock(+3);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(new LoseHPAction(p, p, this.magicNumber));
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RepairPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
	}
}
