package nhat.pa._cntt.Controller;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl{
	@Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        // Use the exact column name specified in the @Column annotation
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        // Use the exact table name specified in the @Table annotation
        return name;
    }
}
