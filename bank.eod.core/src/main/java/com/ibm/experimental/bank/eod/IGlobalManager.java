package com.ibm.experimental.bank.eod;

/**
 * Global Manager for entire topology
 * 
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public interface IGlobalManager {

	/**
	 * Maximum time to wait, default: 2 seconds
	 */
	int MAX_WAIT = 2000;

	/**
	 * Trigger EOD message received
	 */
	void eodReceived();

	/**
	 * Trigger EOD message processed
	 */
	void eodProcessed();
}
