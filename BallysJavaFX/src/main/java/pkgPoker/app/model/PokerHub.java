package pkgPoker.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import netgame.common.Hub;
import pkgPokerBLL.Action;
import pkgPokerBLL.Card;
import pkgPokerBLL.CardDraw;
import pkgPokerBLL.Deck;
import pkgPokerBLL.GamePlay;
import pkgPokerBLL.GamePlayPlayerHand;
import pkgPokerBLL.Player;
import pkgPokerBLL.Rule;
import pkgPokerBLL.Table;

import pkgPokerEnum.eAction;
import pkgPokerEnum.eCardDestination;
import pkgPokerEnum.eDrawCount;
import pkgPokerEnum.eGame;
import pkgPokerEnum.eGameState;


public class PokerHub extends Hub {

	private Table HubPokerTable = new Table();
	private GamePlay HubGamePlay;
	private int iDealNbr = 0;

	public PokerHub(int port) throws IOException {
		super(port);
	}

	protected void playerConnected(int playerID) {

		if (playerID == 2) {
			shutdownServerSocket();
		}
	}

	protected void playerDisconnected(int playerID) {
		shutDownHub();
	}

	protected void messageReceived(int ClientID, Object message) {

		if (message instanceof Action) {
			Player actPlayer = (Player) ((Action) message).getPlayer();
			Action act = (Action) message;
			switch (act.getAction()) {
			case Sit:
				HubPokerTable.AddPlayerToTable(actPlayer);
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case Leave:			
				HubPokerTable.RemovePlayerFromTable(actPlayer);
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case TableState:
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case StartGame:
		
				
				// Get the rule from the Action object.
				Rule rle = new Rule(act.geteGame());
					
				// Pick a random player as dealer
				List<Player> players = new ArrayList<Player>(HubPokerTable.getHmPlayer().values());
				Collections.shuffle(players);
			
						
				// Start the new instance of GamePlay
				HubGamePlay = new GamePlay(rle, players.get(0).getPlayerID());
				
				// Add Players to Game
				HubGamePlay.setGamePlayers(HubPokerTable.getHmPlayer());
				
				// Set the order of players
				HubGamePlay.setiActOrder(GamePlay.GetOrder(players.get(0).getiPlayerPosition()));


			case Draw:
				
				eDrawCount cot = HubGamePlay.geteDrawCountLast();
				HubGamePlay.seteDrawCountLast(eDrawCount.geteDrawCount(cot.getDrawNo()+1));
				
				cot = HubGamePlay.geteDrawCountLast();
				CardDraw cd = HubGamePlay.getRule().GetDrawCard(cot);
				
				switch(cd.getCardDestination()){
					case Player:
						for(int order:HubGamePlay.getiActOrder()){
							
							for(Player plyr:HubPokerTable.getHmPlayer().values()){
								
								if(order==plyr.getiPlayerPosition()){
									
									for(int i=0;i<cd.getCardCount().getCardCount();i++){
										
										HubGamePlay.drawCard(plyr, eCardDestination.Player);
									}
								}
							}
						}
						break;
					default:
						for(int i=0;i<cd.getCardCount().getCardCount();i++){
							HubGamePlay.drawCard(new Player(), eCardDestination.Community);
						}
						break;
				
				}
				
				int currentDraw = HubGamePlay.geteDrawCountLast().getDrawNo();
				int maxDraw = HubGamePlay.getRule().GetMaxDrawCount();
				
				if(currentDraw==maxDraw){
					HubGamePlay.isGameOver();
				}
				
				resetOutput();
				
				sendToAll(HubGamePlay);
				break;
			
			case ScoreGame:
				// Am I at the end of the game?

				resetOutput();
				sendToAll(HubGamePlay);
				break;
			}
			
		}

	}

}