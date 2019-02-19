package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;

public class ExhaustingSlash extends CustomCard {
	public static final String ID = "exordian_avenger:exhaustingSlash";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betaattack.png";
	private static final int COST = 1;

	public ExhaustingSlash() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = 12;
		this.isMultiDamage = true;
	}

	@Override
	public void upgrade() {
		upgradeName();
		upgradeDamage(+3);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
		this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		CardCrawlGame.sound.play("END_TURN");
		AbstractDungeon.player.endTurnQueued = true;
		AbstractDungeon.player.releaseCard();
	}
}
