package exordian_avenger.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class SilencePower extends AbstractPower {
	public static final String POWER_ID = "exordian_avenger:silence";
	public static PowerType POWER_TYPE = PowerType.DEBUFF;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack
			.getPowerStrings("exordian_avenger:silence");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final Texture TEXTURE = new com.badlogic.gdx.graphics.Texture("tea/img/powers/silence.png");

	public SilencePower(AbstractCreature owner, int amt)

	{
		name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amt;
		this.isTurnBased = false;
		this.img = TEXTURE;
		this.type = POWER_TYPE;
		updateDescription();
	}
	@Override
	public void stackPower(int stackAmount) {
		super.stackPower(stackAmount);
	}
	@Override
	public void updateDescription() {
		if (this.amount == 1) {
			this.description = (DESCRIPTIONS[0]);
		} else {
			this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
		}

	}
	/*
	@Override
	public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		if (power.type == PowerType.BUFF) {
			if (this.amount <= 0) {
				AbstractDungeon.actionManager
						.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "exordian_avenger:silence"));
			} else {
				AbstractDungeon.actionManager
						.addToTop(new ReducePowerAction(this.owner, this.owner, "exordian_avenger:silence", 1));
			}
			updateDescription();
			if (power.amount <= target.getPower(power.ID).amount) {
				AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, power.ID));
			} else {
				AbstractDungeon.actionManager
						.addToTop(new ReducePowerAction(this.owner, this.owner, power.ID, power.amount));
			}
			return true;
		}
		return false;
	}
	*/
	@Override
	public void onSpecificTrigger() {
		if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "exordian_avenger:silence"));
		} else {
			AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, "exordian_avenger:silence", 1));
		}
	}
}
