package exordian_avenger.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import exordian_avenger.Exordian_avenger;
import exordian_avenger.patches.RecurrentScreenEnum;
import exordian_avenger.screens.*;

@SpirePatch(clz = AbstractDungeon.class, method = "update")

public class AbstractDungeonUpdatePatch {
	public static RecurrentPileViewScreen viewscreen = new RecurrentPileViewScreen();
	final static Logger logger = (Logger) LogManager.getLogger(Exordian_avenger.class.getName()); 
	@SpirePostfixPatch
	public static void Patch(AbstractDungeon __instance) {
		if (AbstractDungeon.screen == RecurrentScreenEnum.RECURRENT_VIEW) {
			//logger.info("Updated Recurrent View. Now Rendering...");
			viewscreen.update();
		}
		//logger.info("Updated Pile");
		
	}

}


