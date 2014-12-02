package example.service1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopLevelDomainObject
{
    public final String identifier;
    public final long primitiveValue;

    public TopLevelDomainObject(@JsonProperty("identifier") String identifier, @JsonProperty("primitiveValue") long primitiveValue)
    {
        this.identifier = identifier;
        this.primitiveValue = primitiveValue;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public long getPrimitiveValue()
    {
        return primitiveValue;
    }

    @Override
    public String toString()
    {
        return String.format("id: %s  value %s", identifier, primitiveValue);
    }
}
