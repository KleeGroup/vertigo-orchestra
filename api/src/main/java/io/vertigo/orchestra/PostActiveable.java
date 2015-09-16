package io.vertigo.orchestra;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface PostActiveable<T> {

	void postStart(final T startedComponnent);

}
