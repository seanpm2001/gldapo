package gldapo.schema.attribute;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper

class GldapoLdapToGroovyAttributeMapper implements AttributesMapper
{
	Class schema
	List attributeMappings
	
	void setSchema(Class schema)
	{
		this.schema = schema
		this.attributeMappings = schema.getAttributeMappings()
	}
	
	Object mapFromAttributes(Attributes attributes)
	{
		def object = schema.newInstance()
		attributeMappings.each { GldapoAttributeMapping attributeMapping ->
			def coercedValue = schema.convertLdapAttributeToGroovy(
				attributeMapping.type, 
				attributeMapping.name, 
				attributes.get(attributeMapping.name)
			)
			object."${attributeMapping.name}" = coercedValue
		}
		return object
	}
}