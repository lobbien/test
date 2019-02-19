package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import basemod.abstracts.CustomCard;
import exordian_avenger.patches.AbstractCardEnum;
import exordian_avenger.patches.CombatUpdatePatch;

public class PoisonSpores extends CustomCard {
	public static final String ID = "exordian_avenger:spores";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "tea/img/cards/betaattack.png";
	private static final int COST = 1;

	public PoisonSpores() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.EX_DARK_RED,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = 1;
		this.baseMagicNumber = 0;
		this.magicNumber = this.baseMagicNumber;
	}
	
	@Override
	public void upgrade() {
		upgradeName();
		upgradeDamage(+2);
	}
	
	public void magicUp()
	{
		this.magicNumber = countCards();
	}
	private int countCards() {
		int count = 0;
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c.cardID == "exordian_avenger:spores") {
				count++;
			}
		}
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if (c.cardID == "exordian_avenger:spores") {
				count++;
			}
		}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
			if (c.cardID == "exordian_avenger:spores") {
				count++;
			}
		}
		this.magicNumber = count;
		return count;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, countCards()), countCards()));
		CombatUpdatePatch.recurrentPile.addToBottom(this.makeStatEquivalentCopy());
		CombatUpdatePatch.counter.add(1);
	}
}
