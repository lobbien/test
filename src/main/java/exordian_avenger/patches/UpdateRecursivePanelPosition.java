package exordian_avenger.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.OverlayMenu;

@SpirePatch(clz = OverlayMenu.class, method = "update")
public class UpdateRecursivePanelPosition {
	@SpirePostfixPatch
	public static void Patch(OverlayMenu __instance) {
		RenderRecursivePanelPatch.recpan.updatePositions();
	}
}
