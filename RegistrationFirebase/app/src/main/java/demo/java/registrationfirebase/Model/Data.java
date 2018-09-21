package demo.java.registrationfirebase.Model;

public class Data {

    //Constructor
    public  Data() {

    }

    public Data(String userName, String pass, String email, String id) {
        UserName = userName;
        Pass = pass;
        Email = email;
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String UserName;
    private  String Pass;
    private  String Email;
    private  String id;

}
