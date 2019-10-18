package com.ibm.experimental.bank.eod;

/**
 * Message processor <br>
 * could be MDB
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public interface IProcessor {

	/**
	 * End-of-Day message processing
	 * @see IMessage
	 * @param message
	 */
	void processEOD(IMessage message);

	/**
	 * Regular message processing
	 * @see IMessage
	 * @param message
	 */
	void processPayment(IMessage message);

}
