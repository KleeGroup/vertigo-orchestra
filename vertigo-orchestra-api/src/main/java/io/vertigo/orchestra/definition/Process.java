package io.vertigo.orchestra.definition;

import java.util.List;

import io.vertigo.lang.Option;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface Process {

	String getName();

	long getId();

	void setId(long id);

	Option<String> getCronExpression();

	Option<String> getInitialParams();

	boolean getMultiexecution();

	List<Activity> getActivities();

}
