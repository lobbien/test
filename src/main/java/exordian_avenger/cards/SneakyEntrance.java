package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.powers.SilencePower;

public class SneakyEntrance extends CustomCard {
	public static final String ID = "exordian_avenger:silententrance";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betaskill.png";
	private static final int COST = 2;

	public SneakyEntrance() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = 1;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
		this.isEthereal = true;
	}

	@Override
	public void upgrade() {
		upgradeName();
		this.isInnate = true;
		this.rawDescription = SneakyEntrance.UPGRADE_DESCRIPTION;
		initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(m, p, new SilencePower(m, this.magicNumber), this.magicNumber, true));
	}
}