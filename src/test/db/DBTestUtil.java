package db;

public final class DBTestUtil {

    private DBTestUtil() {

    }

    public static void clearDB() {
        PersonRepository.getRepository().clear();
    }
}
