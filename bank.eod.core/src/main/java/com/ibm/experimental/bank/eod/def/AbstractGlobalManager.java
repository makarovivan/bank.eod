package com.ibm.experimental.bank.eod.def;

import java.util.Collection;
import java.util.HashSet;

import com.ibm.experimental.bank.eod.IEodListener;
import com.ibm.experimental.bank.eod.IGlobalManager;

/**
 * Base class for Global Manager
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public abstract class AbstractGlobalManager implements IGlobalManager {

	protected Collection<IEodListener> listeners = new HashSet<>();

	/**
	 * attach End-od-Day listener <br>
	 * 
	 * @see IEodListener
	 * @param listener
	 */
	public void addListener(IEodListener listener) {
		listeners.add(listener);
	}

}
