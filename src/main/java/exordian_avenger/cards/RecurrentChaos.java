package exordian_avenger.cards;


import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.patches.CombatUpdatePatch;

public class RecurrentChaos extends CustomCard {
	public static final String ID = "exordian_avenger:chaos";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betaskill.png";
	private static final int COST = 0;

	public RecurrentChaos() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.baseMagicNumber = 1;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		upgradeName();
		this.rawDescription = RecurrentChaos.UPGRADE_DESCRIPTION;
	    initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, this.magicNumber, false));
		AbstractCard Copy = this.makeStatEquivalentCopy();
		Copy.unfadeOut();
		CombatUpdatePatch.recurrentPile.addToBottom(Copy);
		CombatUpdatePatch.counter.add(1);
	}
	
	@Override
	public void triggerOnManualDiscard()
	{
		if(this.upgraded)
		{
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
		}
	}
}
