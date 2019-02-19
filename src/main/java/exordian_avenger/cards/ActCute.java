package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.powers.ActCutePower;

public class ActCute extends CustomCard {
	public static final String ID = "exordian_avenger:actcute";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betapower.png";
	private static final int COST = 1;

	public ActCute() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF_AND_ENEMY);
		this.baseMagicNumber = 5;
		this.exhaust = true;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		upgradeName();
		upgradeMagicNumber(+2);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true));
		}
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ActCutePower(p)));

	}

}