package com.ibm.experimental.bank.eod.local.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.ibm.experimental.bank.eod.IEodListener;
import com.ibm.experimental.bank.eod.IGlobalManager;
import com.ibm.experimental.bank.eod.IProcessingListener;
import com.ibm.experimental.bank.eod.def.AbstractGlobalManager;
/**
 * Local Test: Global Manager
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class GlobalManager extends AbstractGlobalManager implements IProcessingListener {

	protected AtomicInteger counter = new AtomicInteger();
	protected Object eodLock = new Object();


	@Override
	public void activate() {
		counter.incrementAndGet();
	}
	
	@Override
	public void deactivate() {
		if (counter.decrementAndGet() == 0)
			synchronized (eodLock) {
				eodLock.notifyAll();
			}
	}


	@Override
	public void eodReceived() {
		synchronized (eodLock) {
			for (IEodListener listener : listeners) {
				listener.eodStart();
			}

			if (counter.get() > 0)
				try {
					eodLock.wait(IGlobalManager.MAX_WAIT);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void eodProcessed() {
		for (IEodListener listener : listeners) {
			listener.eodEnd();
		}
	}

	public int getCounter() {
		return counter.get();
	}
}
