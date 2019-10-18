package com.ibm.experimental.bank.eod;

/**
 * Local Manager processing listener
 * @see ILocalManager
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public interface IProcessingListener {
	/**
	 * Activate - mark listener is alive
	 * 
	 * @param listener
	 */
	void activate();

	/**
	 * Deactivate - mark listener is dead
	 * 
	 * @param listener
	 */
	void deactivate();

}
