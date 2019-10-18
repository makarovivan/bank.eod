package com.ibm.experimental.bank.eod;
/**
 * Local Manager interface: <br>
 * purpose: create 1 JVM = 1 LocalManager
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public interface ILocalManager {

	/**
	 * run before regular processing
	 * @see IMessage
	 * @param message
	 */
	void beforeProcess(IMessage message);

	/**
	 * run after regular processing
	 * @see IMessage
	 * @param message
	 */
	void afterProcess(IMessage message);

	/**
	 * attach listener for processing <br>
	 * to subscribe on activation/deactivation
	 * @see IProcessingListener
	 * @param listener
	 */
	void addListener(IProcessingListener listener);

	/**
	 * detach processing listener
	 * @see IProcessingListener
	 * @param listener
	 */
	void removeListener(IProcessingListener listener);

	/**
	 * @return current processing count
	 */
	int count();

}
