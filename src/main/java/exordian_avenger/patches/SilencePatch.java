package exordian_avenger.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = ApplyPowerAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez={
		AbstractCreature.class,
		AbstractCreature.class,
		AbstractPower.class,
		int.class,
		boolean.class,
		AttackEffect.class		
	})
public class SilencePatch {
	@SpirePostfixPatch
	public static void Patch(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source,
			AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
		if ((target.hasPower("exordian_avenger:silence")) && (powerToApply.type == AbstractPower.PowerType.BUFF)) {
			__instance.isDone = true;
			CardCrawlGame.sound.play("NULLIFY_SFX");
			target.getPower("exordian_avenger:silence").flashWithoutSound();
			target.getPower("exordian_avenger:silence").onSpecificTrigger();
		}
	}

}
