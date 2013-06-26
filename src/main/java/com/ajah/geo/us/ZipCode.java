/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.geo.us;

/**
 * USPS ZIP Code. Format is #####. When printing this, use toString().
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public class ZipCode {

	private String zip;

	/**
	 * Will allow nulls, otherwise only accepts 5 digit numeric string.
	 * 
	 * @param zip
	 *            5 digit numeric string, or null.
	 */
	public void setZip(final String zip) {
		if (zip != null && !zip.matches("\\d{5}")) {
			throw new IllegalZipCodeFormatException(zip);
		}
		this.zip = zip;
	}

	/**
	 * Will return 5-character ZIP code. If zip is null, will return null.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.zip;
	}

}
