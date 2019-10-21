package com.ibm.experimental.bank.eod.def;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import com.ibm.experimental.bank.eod.IEodListener;
import com.ibm.experimental.bank.eod.ILocalManager;
import com.ibm.experimental.bank.eod.IMessage;
import com.ibm.experimental.bank.eod.IProcessingListener;

/**
 * Base class for Local Manager <br>
 * purpose: 1 JVM = 1 Local Manager
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public abstract class AbstractLocalManager implements ILocalManager, IEodListener {

	protected final Collection<IProcessingListener> listeners = new HashSet<>();
	protected AtomicInteger activeProcessors = new AtomicInteger(0);
	private boolean eod;
	
	@Override
	public void beforeProcess(IMessage message) {	
		checkEod();
		
		if (activeProcessors.getAndIncrement() == 0)
			fireActivate();
	}

	/**
	 * check if EOD phase enabled, waits in case EOD
	 */
	private synchronized void checkEod() {
		if (eod)
			try {
				long now = System.currentTimeMillis();
				while (eod && TimeUtils.waitCheck(now))
					wait(TimeUtils.waitTime(now));
				eod = false;
			} catch (InterruptedException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
	}

	@Override
	public void afterProcess(IMessage message) {
		if (activeProcessors.decrementAndGet() == 0) {
			fireDeactivate();
		}
	}

	@Override
	public synchronized void eodStart() {
		eod = true;
	}

	@Override
	public synchronized void eodEnd() {
		eod = false;
		notifyAll();
	}

	private void fireActivate() {
		for (IProcessingListener listener : listeners) {
			listener.activate();
		}
	}
	
	private void fireDeactivate() {
		for (IProcessingListener listener : listeners) {
			listener.deactivate();
		}
	}
	
	@Override
	public void addListener(IProcessingListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IProcessingListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public int count() {
		return activeProcessors.get();
	}

}
