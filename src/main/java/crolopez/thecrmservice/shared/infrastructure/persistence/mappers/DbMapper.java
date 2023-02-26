package crolopez.thecrmservice.shared.infrastructure.persistence.mappers;

public abstract class DbMapper<ExternalEntity, Entity> implements Mapper<ExternalEntity, Entity> {
    protected String getEscapedValue(String value) {
        return value == null
                ? null
                : value
                    .replace("'", "\\'")
                    .replace("\"", "\\\"")
                    .replace("?", "\\?")
                    .replace("(", "\\(")
                    .replace(")", "\\)");
    }

    protected String getUnescapedValue(String value) {
        return value == null
                ? null
                : value
                    .replace("\\'", "'")
                    .replace("\\\"", "\"")
                    .replace("\\?", "?")
                    .replace("\\(", "(")
                    .replace("\\)", ")");
    }
}
