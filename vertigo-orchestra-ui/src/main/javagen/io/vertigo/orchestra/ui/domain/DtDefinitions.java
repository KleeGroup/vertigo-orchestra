package io.vertigo.orchestra.ui.domain;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Attention cette classe est générée automatiquement !
 */
public final class DtDefinitions implements Iterable<Class<?>> {

	/**
	 * Enumération des DtDefinitions.
	 */
	public enum Definitions {
		;

		private final Class<?> clazz;

		private Definitions(final Class<?> clazz) {
			this.clazz = clazz;
		}

		/**
		  * Classe associée.
		  * @return Class d'implémentation de l'objet
		  */
		public Class<?> getDtClass() {
			return clazz;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<Class<?>> iterator() {
		return new Iterator<Class<?>>() {
			private final Iterator<Definitions> it = Arrays.asList(Definitions.values()).iterator();

			/** {@inheritDoc} */
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			/** {@inheritDoc} */
			@Override
			public Class<?> next() {
				return it.next().getDtClass();
			}

			/** {@inheritDoc} */
			@Override
			public void remove() {
				//unsupported
			}
		};
	}
}
