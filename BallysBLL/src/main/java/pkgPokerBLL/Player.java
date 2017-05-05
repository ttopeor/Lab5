package pkgPokerBLL;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {

	private UUID PlayerID;
	private String PlayerName;
	private int iPlayerPosition;
	private int iPokerClientID;
	
	public Player() {
		
	}
	public Player(String strPlayerName, int iPokerClientID) {
		PlayerID = UUID.randomUUID();
		this.PlayerName = strPlayerName;
		this.iPlayerPosition = iPlayerPosition;
		this.iPokerClientID = iPokerClientID;
	}
	
	public Player(String playerName) {
		super();
		PlayerID = UUID.randomUUID();
		PlayerName = playerName;
	}

	public String getPlayerName() {
		return PlayerName;
	}

	public UUID getPlayerID() {
		return PlayerID;
	}

	public int getiPlayerPosition() {
		return iPlayerPosition;
	}

	public void setiPlayerPosition(int iPlayerPosition) {
		this.iPlayerPosition = iPlayerPosition;
	}
	public int getiPokerClientID() {
		return iPokerClientID;
	}
	public void setiPokerClientID(int iPokerClientID) {
		this.iPokerClientID = iPokerClientID;
	}
	
}
