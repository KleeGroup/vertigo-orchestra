/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2017, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.orchestra.domain.referential;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OUser
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("orchestra")
public final class OUser implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long usrId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean mailAlert;
	private Boolean active;

	/** {@inheritDoc} */
	@Override
	public URI<OUser> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'.
	 * @return Long usrId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", type = "ID", required = true, label = "Id")
	public Long getUsrId() {
		return usrId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param usrId Long <b>Obligatoire</b>
	 */
	public void setUsrId(final Long usrId) {
		this.usrId = usrId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom'.
	 * @return String firstName
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Nom")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom'.
	 * @param firstName String
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Prénom'.
	 * @return String lastName
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Prénom")
	public String getLastName() {
		return lastName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Prénom'.
	 * @param lastName String
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Email'.
	 * @return String email
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Email")
	public String getEmail() {
		return email;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Email'.
	 * @param email String
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Mot de passe'.
	 * @return String password
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Mot de passe")
	public String getPassword() {
		return password;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Mot de passe'.
	 * @param password String
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Alerté en cas d'erreur'.
	 * @return Boolean mailAlert
	 */
	@Field(domain = "DO_O_BOOLEEN", label = "Alerté en cas d'erreur")
	public Boolean getMailAlert() {
		return mailAlert;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Alerté en cas d'erreur'.
	 * @param mailAlert Boolean
	 */
	public void setMailAlert(final Boolean mailAlert) {
		this.mailAlert = mailAlert;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Compte Actif'.
	 * @return Boolean active
	 */
	@Field(domain = "DO_O_BOOLEEN", label = "Compte Actif")
	public Boolean getActive() {
		return active;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Compte Actif'.
	 * @param active Boolean
	 */
	public void setActive(final Boolean active) {
		this.active = active;
	}

	// Association : ExecutionProcessus non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
