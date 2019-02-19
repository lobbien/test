package exordian_avenger.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class CounterPower extends AbstractPower {
	public static final String POWER_ID = "exordian_avenger:counter";
	public static PowerType POWER_TYPE = PowerType.BUFF;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("exordian_avenger:counter");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final Texture TEXTURE = new com.badlogic.gdx.graphics.Texture("tea/img/powers/counter.png");


	public CounterPower(AbstractCreature owner, int amt)

	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amt;

		this.isTurnBased = false;
		this.img = TEXTURE;
		this.type = POWER_TYPE;
		updateDescription();
	}

	public void stackPower(int stackAmount) {
		super.stackPower(stackAmount);
	}

	public void updateDescription() {

		this.description = (DESCRIPTIONS[0] + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
	}

}
