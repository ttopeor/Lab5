package pkgPokerBLL;

import java.io.Serializable;

import pkgPokerEnum.eCardCount;
import pkgPokerEnum.eCardDestination;
import pkgPokerEnum.eCardVisibility;


public class CardDraw implements Serializable {

	private eCardCount CardCount;
	private eCardDestination CardDestination;
	private eCardVisibility CardVisibility;
	public CardDraw(eCardCount cardCount, eCardDestination cardDestination, eCardVisibility cardVisiblity) {
		super();
		CardCount = cardCount;
		CardDestination = cardDestination;
		CardVisibility = CardVisibility;
	}
	public eCardCount getCardCount() {
		return CardCount;
	}
	public eCardDestination getCardDestination() {
		return CardDestination;
	}
	public eCardVisibility getCardVisibility() {
		return CardVisibility;
	}
	
	
}