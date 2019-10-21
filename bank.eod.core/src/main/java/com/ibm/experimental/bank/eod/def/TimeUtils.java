package com.ibm.experimental.bank.eod.def;

import com.ibm.experimental.bank.eod.IGlobalManager;

public class TimeUtils {

	public static boolean waitCheck(long time) {
		return System.currentTimeMillis() < time + IGlobalManager.MAX_WAIT;
	}

	public static long waitTime(long time) {
		long waitTime = time + IGlobalManager.MAX_WAIT - System.currentTimeMillis();
		if (waitTime <= 0)
			return 1l;
		return waitTime;
	}
}
