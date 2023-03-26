import dao.UserDao;

public class Main {
    public static void main(String[] args) {
        new UserDao().deleteUserById(202);
    }
}
