package exordian_avenger.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(clz = AbstractDungeon.class, method="closeCurrentScreen")
public class AbstractDungeonCloseScreenPatch {
	@SpirePrefixPatch
	public static void Patch() {
		if (AbstractDungeon.screen == RecurrentScreenEnum.RECURRENT_VIEW) {
			AbstractDungeon.overlayMenu.cancelButton.hide();
			if (AbstractDungeon.previousScreen == null) {
				if (AbstractDungeon.player.isDead) {
					AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
				} else {
					AbstractDungeon.isScreenUp = false;
					AbstractDungeon.overlayMenu.hideBlackScreen();
				}
			}
			if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
					&& (!AbstractDungeon.player.isDead)) {
				AbstractDungeon.overlayMenu.showCombatPanels();
			}
		}
	}

}
