package com.ibm.experimental.bank.eod.wxs;

import com.ibm.experimental.bank.eod.def.AbstractLocalManager;

/**
 * Local Manager implementation for JVM/WXS-container
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public class LocalManager extends AbstractLocalManager {

	private static LocalManager instance;

	public static synchronized LocalManager getInstance() {
		if (instance == null) {
			instance = new LocalManager();
			WxsManager.getInstance().addListener(instance);
		}
		
		return instance;
	}
}
