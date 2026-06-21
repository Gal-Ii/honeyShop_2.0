package app.model.entity.user;

public enum Country {
    SPAIN("ИСПАНИЯ"),
    GREECE("ГЪРЦИЯ"),
    BULGARIA("БЪЛГАРИЯ");

    private final String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
