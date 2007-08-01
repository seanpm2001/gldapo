/* 
 * Copyright 2007 Luke Daley
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gldapo.schema.gynamo;
import gldapo.GldapoTemplate
import gldapo.exception.GldapoException
import org.springframework.ldap.filter.EqualsFilter
import gynamo.GynamoDependencies
import org.springframework.ldap.core.AttributesMapperCallbackHandler

@GynamoDependencies([IdentifyingAttributeGynamo, FindGynamo])
class LoadGynamo 
{
	static load = { String identifyingValue, def template ->
		if (delegate.identifyingAttribute == null)
		{
			throw new GldapoException("Cannot use load() on ${delegate.name} as it has no GldapoIdentifyingAttribute annotation" as String)
		}
		def filter = new EqualsFilter(delegate.identifyingAttribute, identifyingValue)
		def matches = delegate.find(template: template, filter: filter.encode(), countLimit: 1)
		if (matches.size() == 0)
		{
			return null
		}
		else
		{
			return matches[0]
		}
	}
	
	static load = { String identifyingValue ->
		delegate.load(identifyingValue, null)
	}
}