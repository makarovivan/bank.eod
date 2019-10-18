package com.ibm.experimental.bank.eod.def;

import com.ibm.experimental.bank.eod.IGlobalManager;
import com.ibm.experimental.bank.eod.ILocalManager;
import com.ibm.experimental.bank.eod.IMessage;
import com.ibm.experimental.bank.eod.IProcessor;

/**
 * Base processor class, could be MDB
 * 
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public abstract class AbstractProcessor implements IProcessor {

	private ILocalManager local;
	private IGlobalManager global;

	public AbstractProcessor(IGlobalManager global, ILocalManager local) {
		this.global = global;
		this.local = local;
	}
	
	@Override
	public void processPayment(IMessage message) {
		local.beforeProcess(message);
		try {
			doPayment(message);
		} finally {
			local.afterProcess(message);
		}
	}

	@Override
	public void processEOD(IMessage message) {
		beforeEod(message);
		global.eodReceived();
		try {
			doEod(message);
		} finally {
			global.eodProcessed();
		}
	}

	/**
	 * process all payment
	 * @see IMessage
	 * @param message
	 */
	public abstract void doPayment(IMessage message);

	/**
	 * do pre-work before EOD
	 * @see IMessage
	 * @param message
	 */
	public abstract void beforeEod(IMessage message);

	/**
	 * do EOD, it is a place for safe EOD work, all processes in waiting status
	 * @see IMessage
	 * @param message
	 */
	public abstract void doEod(IMessage message);
}
