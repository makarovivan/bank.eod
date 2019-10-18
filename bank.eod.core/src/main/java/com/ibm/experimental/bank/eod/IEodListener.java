package com.ibm.experimental.bank.eod;

/**
 * End-od-Day listener <br>
 * to subscribe on EOD start/end
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public interface IEodListener {

	/**
	 * EOD action start
	 */
	void eodStart();
	
	/**
	 * EOD action finish
	 */
	void eodEnd();

}
