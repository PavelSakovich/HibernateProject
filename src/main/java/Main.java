import dao.UserDao;

public class Main {
    public static void main(String[] args) {
    new UserDao().deleteAllUsers();
//        new UserDao().getAllUsers().forEach(System.out::println);
//        new UserDao().getAllUsers().forEach(System.out::println);
    }
}
