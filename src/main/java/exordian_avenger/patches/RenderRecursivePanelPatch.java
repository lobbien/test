package exordian_avenger.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.OverlayMenu;

import exordian_avenger.ui.RecurrentPanel;

@SpirePatch(clz=OverlayMenu.class, method="render")
public class RenderRecursivePanelPatch {
	public static RecurrentPanel recpan = new RecurrentPanel();
	@SpirePostfixPatch 
	public static void Patch(OverlayMenu __instance, SpriteBatch sb)
	{
		recpan.render(sb);
	}
}
