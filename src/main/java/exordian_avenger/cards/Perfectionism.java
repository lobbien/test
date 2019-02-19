package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import exordian_avenger.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Perfectionism extends CustomCard {
	public static final String ID = "exordian_avenger:perfectionism";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "tea/img/cards/perfectionism.png";
	private static final int COST = 0;

	public Perfectionism()
	{
		super(ID, NAME,  IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.EX_DARK_RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.baseMagicNumber = 10;
		this.magicNumber = this.baseMagicNumber;
	}
	

	@Override
	public void upgrade() {
	    upgradeName();
		upgradeMagicNumber(-3);	
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster arg1) {
	    AbstractDungeon.actionManager.addToTop(new LoseHPAction(p, p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, 2), 2));
	}
}
