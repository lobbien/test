package exordian_avenger.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import exordian_avenger.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Curlup extends CustomCard{
	public static final String ID = "exordian_avenger:curlup";
	private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "tea/img/cards/betaskill.png";
	private static final int COST = 0;
	
	public Curlup()
	{
		super(ID, NAME,  IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.EX_DARK_RED, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF_AND_ENEMY);
		this.baseMagicNumber = 4;
		this.magicNumber = this.baseMagicNumber;
	}
	

	@Override
	public void upgrade() {
	    upgradeName();
		upgradeMagicNumber(+2);	
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
	    if ((m != null) && ((m.intent == AbstractMonster.Intent.ATTACK) || (m.intent == AbstractMonster.Intent.ATTACK_BUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEBUFF) || (m.intent == AbstractMonster.Intent.ATTACK_DEFEND))) {
	    	  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
	      } else {
	        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
	      }	   
	}
}
