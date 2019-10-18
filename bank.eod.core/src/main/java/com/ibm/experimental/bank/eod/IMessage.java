package com.ibm.experimental.bank.eod;

/**
 * Processing message interface
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public interface IMessage {

	/**
	 * End-of-Day message flag
	 * @return
	 */
	boolean isEOD();

	/**
	 * @return ID of the message
	 */
	String getId();
}
