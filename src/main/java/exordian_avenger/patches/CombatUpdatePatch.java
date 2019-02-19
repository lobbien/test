package exordian_avenger.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(clz=AbstractPlayer.class, method="combatUpdate")
public class CombatUpdatePatch {
		public static CardGroup recurrentPile = new CardGroup(RecurrentCardEnum.RECURRENT_CARDS);
		public static ArrayList<Integer> counter = new ArrayList<Integer>(0);
		@SpirePostfixPatch
		public static void Patch(AbstractPlayer __instance)
		{
			CombatUpdatePatch.recurrentPile.update();
		}

}
