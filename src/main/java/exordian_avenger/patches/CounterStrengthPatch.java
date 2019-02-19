package exordian_avenger.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

@SpirePatch(clz=StrengthPower.class, method= SpirePatch.CONSTRUCTOR)
public class CounterStrengthPatch {
	@SpirePostfixPatch
	public static void Patch(StrengthPower __instance, AbstractCreature owner, int amount)
	{
		if(AbstractDungeon.player.hasPower("exordian_avenger:counter") && __instance.owner != AbstractDungeon.player)
		{
			AbstractDungeon.actionManager.addToBottom(new LoseHPAction(__instance.owner, __instance.owner, amount*AbstractDungeon.player.getPower("exordian_avenger:counter").amount));
		}
	}
}
