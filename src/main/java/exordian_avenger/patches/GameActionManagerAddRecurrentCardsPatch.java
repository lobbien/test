package exordian_avenger.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import exordian_avenger.actions.PreDrawAddCardsToHand;

@SpirePatch(clz = GameActionManager.class, method = "getNextAction")

public class GameActionManagerAddRecurrentCardsPatch {

	@SpireInsertPatch(rloc = 205)
	public static void patch(GameActionManager __instance) {
		AbstractGameAction Draw = new PreDrawAddCardsToHand();
		while (Draw.isDone == false) {
			Draw.update();
		}
	}
}