package Login;
import LoginUI.LoginForm;
public class User {
    public String name;
    public String email;
    public String phone;
    public String address;
    public String password;
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if (user != null){
            System.out.println("Successful Authentication of : " + user.name);
            System.out.println("        Email : " + user.email);
            System.out.println("        Phone : " + user.phone);
            System.out.println("        Address : " + user.address);
        }
        else{
            System.out.println("Authentication canceled");
        }
    }
}
