package exordian_avenger.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ActCutePower extends AbstractPower {
	public static final String POWER_ID = "exordian_avenger:actcute";
	public static PowerType POWER_TYPE = PowerType.DEBUFF;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack
			.getPowerStrings("exordian_avenger:actcute");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final Texture TEXTURE = new com.badlogic.gdx.graphics.Texture("tea/img/powers/actcute.png");

	public ActCutePower(AbstractCreature owner)

	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.isTurnBased = false;
		this.img = TEXTURE;
		this.type = POWER_TYPE;
		updateDescription();
	}

	@Override
	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, 1), 1, true));
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1, true));
	}
}
