package com.ibm.experimental.bank.eod.wxs;

import java.util.Collection;
import java.util.Iterator;

import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.datagrid.EntryErrorValue;
import com.ibm.websphere.objectgrid.datagrid.ReduceGridAgent;

/**
 * Base Eod Agent
 * @author Ivan Makarov <ivan.makarov@ru.ibm.com>
 *
 */
public abstract class AbstractEodAgent implements ReduceGridAgent {

	private static final long serialVersionUID = -6159508378911250068L;
	
	@Override
	public Object reduce(Session session, ObjectMap map, Collection keys) {
		// Nothing to do
		return null;
	}

	/**
	 * @return integer
	 */
	@Override
	public Object reduceResults(Collection results) {
		Iterator<?> iter = results.iterator();
		int sum = 0;
		while (iter.hasNext()) {
			Object nextResult = iter.next();
			if (nextResult instanceof EntryErrorValue) {
				EntryErrorValue eev = (EntryErrorValue) nextResult;
				throw new RuntimeException(
						"Error encountered on one of the containers: "
								+ nextResult, eev.getException());
			}

			sum += ((Integer) nextResult).intValue();
		}
		return new Integer(sum);
	}

}
