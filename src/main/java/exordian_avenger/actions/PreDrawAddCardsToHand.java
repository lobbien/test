package exordian_avenger.actions;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import exordian_avenger.Exordian_avenger;
import exordian_avenger.patches.CombatUpdatePatch;
import exordian_avenger.patches.RecurrentCardEnum;

public class PreDrawAddCardsToHand extends AbstractGameAction {
	public static CardGroup NewRecurrentPile = new CardGroup(RecurrentCardEnum.NEW_RECURRENT_CARDS);
	public static ArrayList<Integer> NewCounter = new ArrayList<Integer>(0);

	@Override
	public void update() {
		if (SoulGroup.isActive()) {
			return;
		} else {
			final Logger logger = (Logger) LogManager.getLogger(Exordian_avenger.class.getName());

			for (int i = 0; i < CombatUpdatePatch.recurrentPile.size(); i++) {
				while (SoulGroup.isActive()) {
				}
				logger.info(CombatUpdatePatch.counter.get(i));
				CombatUpdatePatch.counter.set(i, CombatUpdatePatch.counter.get(i) - 1);
				AbstractCard card = CombatUpdatePatch.recurrentPile.getNCardFromTop(i);
				logger.info(i);
				logger.info(CombatUpdatePatch.counter.get(i));
				String cardid = card.cardID;
				logger.info(cardid);
				if (CombatUpdatePatch.counter.get(i) == 0) {
					if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
						
						card.unfadeOut();

						card.unhover();
						
						AbstractDungeon.player.hand.addToHand(card);
					} else {
						

						card.unfadeOut();

						card.unhover();
						
						AbstractDungeon.player.drawPile.addToTop(card);
					}
				} else {
					NewRecurrentPile.addToBottom(card);
					NewCounter.add(CombatUpdatePatch.counter.get(i));
				}

				AbstractDungeon.player.hand.refreshHandLayout();
			}
			CombatUpdatePatch.recurrentPile.clear();
			CombatUpdatePatch.counter.clear();
			for (int i = 0; i < NewCounter.size(); i++) {
				CombatUpdatePatch.recurrentPile.addToBottom(NewRecurrentPile.getNCardFromTop(i));
				;
				CombatUpdatePatch.counter.add(NewCounter.get(i));
			}

			NewCounter.clear();
			NewRecurrentPile.clear();
			AbstractDungeon.player.hand.refreshHandLayout();
			this.isDone = true;
			return;
		}

	}

}
