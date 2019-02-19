package exordian_avenger.patches;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public abstract class AbstractDungeonPatches extends AbstractDungeon {

	public AbstractDungeonPatches(String arg0, AbstractPlayer arg1, SaveFile arg2) {
		super(arg0, arg1, arg2);
	}
	  public static AbstractCard returnTrulyRandomCommonCardInCombat(AbstractCard.CardType type)
	  {
	    ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
	    for (AbstractCard c : srcCommonCardPool.group) {
	      if ((c.type == type) && (!c.hasTag(AbstractCard.CardTags.HEALING))) {
	        list.add(c);
	      }
	    }
	    return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
	  }
	  

}
