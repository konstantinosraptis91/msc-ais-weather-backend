package ms.ais.weather.model.auth;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/1/2021.
 */
public class Token {

    private final String id;
    private final int userId;
    private final long validUntilTimestamp;
    private final long createdTimestamp;

    private Token(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.validUntilTimestamp = builder.validUntilTimestamp;
        this.createdTimestamp = builder.createdTimestamp;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public long getValidUntilTimestamp() {
        return validUntilTimestamp;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private int userId;
        private long validUntilTimestamp;
        private long createdTimestamp;

        public Builder tokenId(String id) {
            this.id = id;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder validUntilTimestamp(long timestamp) {
            this.validUntilTimestamp = timestamp;
            return this;
        }

        public Builder createdTimestamp(long timestamp) {
            this.createdTimestamp = timestamp;
            return this;
        }

        public Token build() {
            return new Token(this);
        }

    }

    @Override
    public String toString() {
        return "Token{" +
            "id='" + id + '\'' +
            ", userId=" + userId +
            ", validUntilTimestamp=" + validUntilTimestamp +
            ", createdTimestamp=" + createdTimestamp +
            '}';
    }
}
