package exordian_avenger.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import basemod.BaseMod;

public class ThirdHandPower extends AbstractPower {
	public static final String POWER_ID = "exordian_avenger:thirdhandPower";
	public static PowerType POWER_TYPE = PowerType.DEBUFF;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("exordian_avenger:thirdhandPower");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final Texture TEXTURE = new com.badlogic.gdx.graphics.Texture("tea/img/powers/thirdhand.png");
	public ThirdHandPower(AbstractCreature owner, int amt)

	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amt;

		this.isTurnBased = false;
		this.img = TEXTURE;
		this.type = POWER_TYPE;
		updateDescription();
		BaseMod.MAX_HAND_SIZE += this.amount;
	}
	@Override
	public void onVictory()
	{
		BaseMod.MAX_HAND_SIZE -= this.amount;
	}
	@Override
	public void updateDescription() {

		this.description = (DESCRIPTIONS[0] + this.amount);
	}
}
