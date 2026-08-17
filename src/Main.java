import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.*;
import java.sql.*;
class Main1
{
    public static void main(String[] args)throws Exception
    {
        Scanner sc=new Scanner(System.in);
        int ch;
        do
        {
            System.out.println(" 1.Login");
            System.out.println(" 2.SignUp");
            System.out.println(" 3.forgot password");
            System.out.println(" 4.Exit..");
            System.out.print("Enter your choice ==>  ");
            Signup sp=new Signup();
            Login lg=new Login();
            Rector r=new Rector();
            ch= sc.nextInt();
            switch (ch)
            {
                case 1:

                    System.out.print("Enter your name ==>  ");
                    sc.nextLine();
                    String name= sc.nextLine();
                    System.out.print("Enter your password ==>  ");
                    String password=sc.nextLine();
                    if(name.equals("Rector"))
                    {
                        if(password.equals("Rector@123"))
                        {
                            r.loginrec();
                        }
                    }
                    else
                    {
                        System.out.print("Enter your GRNo ==> ");
                        int grno=sc.nextInt();
                        lg.login(grno,password,name);
                    }
                    break;
                case 2:sp.signup();break;
                case 3:
                    connection2 c = new connection2();
                    Connection con = c.getconnection();
                    System.out.println("enter your grno");
                    int g=sc.nextInt();
                    System.out.println("please write enreted otp ");
                        int otp = (int)(Math.random() * 9000 )+ 1000;
                        System.out.println("your otp is:"+otp);
                        System.out.println("please enter your otp");
                        int up=sc.nextInt();
                        if(up==otp)
                        {
                            System.out.println("enter your new password");
                            String np=sc.next();
                            String sql2="update student set password=? where grno=?";
                            PreparedStatement pst = con.prepareStatement(sql2);
                            pst.setString(1, np);
                            pst.setInt(2, g);
                            int r3=pst.executeUpdate();
                            if(r3>0)
                            {
                                System.out.println("your password is successfully changed");
                            }
                            else
                            {
                                System.out.println("it occurs some error");
                            }
                        }
                        else
                        {
                            System.out.println("your otp is wrong please try again");
                        };break;
                case 4:
                    System.out.println("exiting....");break;

                default:
                    System.out.println("Choose above choice");break;
            }
        }while(ch!=4);
    }
}
class connection2
{
    Connection getconnection() throws Exception {
        String dburl = "jdbc:mysql://localhost:3306/hostel";
        String dbuser = "root";
        String dbpass = "";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dburl, dbuser, dbpass);
    }
}
class Signup {
    Scanner sc = new Scanner(System.in);
    connection2 c = new connection2();
    public void signup() throws Exception
    {
        Connection con = c.getconnection();
        System.out.print("Enter student name:==> ");
        String name = sc.nextLine();
        System.out.print("Enter student password:==>  ");
        String password = sc.nextLine();
        String mobileno;
        HashSet<String> mb=new HashSet<>();
        String sqlMobile = "select mobile_no from student";
        PreparedStatement pstMobile = con.prepareStatement(sqlMobile);
        ResultSet rsMobile = pstMobile.executeQuery();
        HashSet<String> em=new HashSet<>();
        String sqlEmail = "select email from student";
        PreparedStatement pstEmail = con.prepareStatement(sqlEmail);
        ResultSet rsEmail = pstEmail.executeQuery();
        while(rsEmail.next()) {
            em.add(rsEmail.getString("email"));
        }

        while(rsMobile.next()) {
            mb.add(rsMobile.getString("mobile_no"));
        }
        while (true) {
            System.out.print("Enter student mobile number:==>  ");
            mobileno = sc.nextLine();
            if (mobileno.length() == 10 && (mobileno.startsWith("9") || mobileno.startsWith("8") || mobileno.startsWith("7") || mobileno.startsWith("6")))
            {
                if(mb.contains(mobileno))
                {
                    System.out.println("this no is already exists");
                }
                else
                {
                    break;
                }
            }
            else
            {
                System.out.println("Invalid mobile number. Please enter a valid 10-digit number starting with 9, 8, 7, or 6.");
            }
        }
        System.out.println("enter your email id in @gmil.com");
        String email;

        while (true)
        {
            System.out.println("Enter your email id ending with @gmail.com:");
            email = sc.nextLine();

            if (email.endsWith("@gmail.com"))
            {
                if(em.contains(email))
                {
                    System.out.println("email is already entered plase enter diiferent");
                }
                else
                {
                    em.add(email);
                    System.out.println("Valid Gmail address");
                    break;
                }

            } else {
                System.out.println("Invalid email. Please try again.");
            }
        }

        String sql = "insert into student (student_name,password,mobile_no,email) values (?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, password);
        pst.setString(3, mobileno);
        pst.setString(4,email);
        pst.executeUpdate();
        String sql1="select * from student";
        PreparedStatement pst1=con.prepareStatement(sql1);
        ResultSet rs = pst1.executeQuery();
        System.out.println("#...Please note your GRNO:..#");
        System.out.println(" **** STUDENT LIST ****");
        while (rs.next())
        {
            System.out.println("======================================");
            System.out.println("GR No: " + rs.getInt("grno"));
            System.out.println("Name: " + rs.getString("student_name"));
            System.out.println("Mobile: " + rs.getString("mobile_no"));
            System.out.println("Email:"+rs.getString("email"));
            String check=rs.getString("room_no");
            if(check==null)
            {
                System.out.println("your room now not alloted please wait");
            }
            else {
                System.out.println("Room No: " + rs.getString("room_no"));
            }
            System.out.println("================================================");
        }
    }
}

class Login
{
    Scanner sc=new Scanner(System.in);
    connection2 c=new connection2();
    Payment pt=new Payment();
    public void login(int grno, String password, String name) throws Exception {
        Connection con = c.getconnection();
        String sql = "select * from student where grno = ? and password = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, grno);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            // Successful login
            System.out.println("  ***** STUDENT INFORMATION ***** ");
            System.out.println("GR No:==> " + rs.getInt("grno"));
            System.out.println("Name:==> " + rs.getString("student_name"));
            System.out.println("Mobile Number:==> " + rs.getString("mobile_no"));
            System.out.println("Room No:==> " + rs.getInt("room_no"));

            Student st = new Student(grno, rs.getString("room_no"), password,
                    rs.getString("student_name"), rs.getString("mobile_no"));

            int ch;
            do {
                System.out.println("\nENTER YOUR CHOICE ==>");
                System.out.println("  1.for payment");
                System.out.println("  2.for cloths");
                System.out.println("  3.to see complaints");
                System.out.println("  4.to add complaint");
                System.out.println("  5.for change in info");
                System.out.println("  6.for leave");
                System.out.println("  7.for food menu");
                System.out.println("  8.Exit");
                ch = sc.nextInt();

                switch (ch) {
                    case 1: pt.makepayment(); break;
                    case 2: st.laundry(); break;
                    case 3:
                        ComplaintList c = new ComplaintList();
                        c.display();
                        break;
                    case 4: addcomplaint(); break;
                    case 5: change(); break;
                    case 6: st.leave(); break;
                    case 7:
                        Food f = new Food();
                        f.displayMenu();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } while (ch != 8);

        } else {
            // Failed login
            int choice;
            do {
                System.out.println("\nLogin failed. Choose an option:");
                System.out.println("1. Sign up");
                System.out.println("2. Re-enter password");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Redirecting to signup...");
                        Signup signup = new Signup();
                        signup.signup();
                        break;
                    case 2:
                        System.out.print("Enter your GR No: ");
                        grno = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter your password: ");
                        password = sc.nextLine();
                        // Retry login with new credentials
                        pst.setInt(1, grno);
                        pst.setString(2, password);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            System.out.println("Login successful!");
                            login(grno, password, name); // Recursive call with correct credentials
                            return;
                        }
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } while (choice != 3);
        }

    }
    public void change() throws Exception
    {
        System.out.print(" Enter your GRNo: ==>");
        int grno = sc.nextInt();
        sc.nextLine();
        System.out.print(" Enter your current password: ==>");
        String oldpass = sc.nextLine();
        Connection con = c.getconnection();
        String sql = "select*from student where grno = ? and password = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, grno);
        pst.setString(2, oldpass);
        ResultSet rs = pst.executeQuery();
        if (rs.next())
        {
            Student st = new Student(grno, rs.getString("room_no"), oldpass, rs.getString("student_name"), rs.getString("mobile_no"));
            int ch;
            int c=3;
            do {
                System.out.println(" 1. Change Password");
                System.out.println(" 2. Change Mobile Number");
                System.out.println(" 3. Exit");
                ch=sc.nextInt();
                sc.nextLine();
                switch (ch)
                {
                    case 1:
                        while (c>0) {
                            System.out.println("enter old pass");
                            String oldpass1 = sc.nextLine();
                            if (oldpass1.equals(oldpass)) {
                                System.out.print("Enter new password: ==>");
                                String newPass = sc.nextLine();
                                st.updatepassword(newPass);
                                break;
                            } else {
                                System.out.println("incorrect pass");
                                c--;
                                System.out.println(c+"attempt left");
                            }
                        }
                        System.out.println("pass not changed");
                        break;
                    case 2:
                        while (c>0) {
                            System.out.println("enter old mo no.");
                            String oldmo=sc.nextLine();
                            if(oldmo.equals(rs.getString("mobile_no"))) {
                                System.out.print("Enter new mobile number: ==>");
                                String newMobile = sc.nextLine();
                                st.updateMobile(newMobile);
                                break;
                            }
                            else
                            {
                                System.out.println("wrong mo no.");
                                c--;
                                System.out.println(c+"attempt left");
                            }
                        }
                        System.out.println("not changed");
                        break;
                    case 3:
                        System.out.println("Exiting update menu...");
                        break;
                    default:
                        System.out.println("Invalid choice..");
                }
            }
            while (ch!=3);
        }
        else
        {
            System.out.println("Invalid GR No or Password.");
        }
    }
    public void addcomplaint() throws Exception
    {
        int ch;
        ComplaintList c=new ComplaintList();
        do
        {
            System.out.println("1.for add complaint");
            System.out.println("2.for add last");
            System.out.println("3.display");
            System.out.println("4.exit");
            System.out.println("enter your choice");
            ch= sc.nextInt();
            switch (ch)
            {
                case 1:
                    System.out.println("enter you complint with right grno and room no");
                    sc.nextLine();
                    String complaint=sc.nextLine();
                    c.addFirst(complaint);break;
                case 2:
                    System.out.println("enter you complint");
                    String complaint2=sc.nextLine();
                    c.addFirst(complaint2);break;
                case 3:c.display();break;
                default:
                    System.out.println("please enter valid choice");break;

            }

        }while(ch!=4);

    }
}
class Rector
{
    Scanner sc=new Scanner(System.in);
    connection2 c=new connection2();
    public void loginrec()throws Exception
    {
        Scanner sc = new Scanner(System.in);
        int ch;
        do {
            System.out.println(" 1.Show student all details");
            System.out.println(" 2.Show Room Complaint list");
            System.out.println(" 3.Show payments list");
            System.out.println(" 4.for room allotment");
            System.out.println(" 5.for show laundry request");
            System.out.println(" 6.for leave report");
            System.out.println(" 7.for food");
            System.out.println(" 8.Exit");
            ch = sc.nextInt();
            switch (ch) {
                case 1:showstudents();break;
                case 2:showcomplaint();break;
                case 3:payment();break;
                case 4:roomallotement();break;
                case 5:showLaundryRequests();break;
                case 6:leavereport();
                case 7: Food f = new Food();
                    f.foodByDay();
                case 8:
                    System.out.println("Exit");break;
                default:
                    System.out.println("Invalid choice..");break;
            }
        } while (ch!=8);
    }
    public void showstudents() throws Exception
    {
        Connection con = c.getconnection();
        String sql = "SELECT * FROM student";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        System.out.println("       ****** STUDENT LIST ******");
        while (rs.next())
        {
            System.out.println("=================================================");
            System.out.println("GR No : => " + rs.getInt("grno"));
            System.out.println("Name : => " + rs.getString("student_name"));
            System.out.println("Mobile : => " + rs.getString("mobile_no"));
            System.out.println("Room No : => " + rs.getString("room_no"));
            System.out.println("=================================================");
        }
    }
    public void roomallotement()throws Exception
    {
        Connection con = c.getconnection();
        String sql = "select grno from student order by student_name asc";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        int roomNo = 101;
        int count = 0;
        List<Integer> rm=new ArrayList<>();

        while (rs.next()) {
            int grno = rs.getInt("grno");
            rm.add(grno);
            count++;
            if (count == 5) {
                assignRoom(con,rm,roomNo);
                rm.clear();
                count = 0;
                roomNo++;
            }
        }

        if (!rm.isEmpty())
        {
            assignRoom(con,rm,roomNo);
        }

        System.out.println("Room allotment completed Alphabetically.");
    }
    public void assignRoom(Connection con, List<Integer> grnos, int roomNo) throws Exception {
        for (int grno : grnos)
        {
            String sql = "update student set room_no = ? where grno = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, roomNo);
            pst.setInt(2, grno);
            pst.executeUpdate();
        }
    }
    public void showcomplaint() throws Exception {
        int ch;
        ComplaintList c=new ComplaintList();
        do {
            System.out.println("1.for delete first");
            System.out.println("2.delete last");
            System.out.println("3.delete particular complaint");
            System.out.println("4.exit");
            System.out.println("enter your choice");
            ch=sc.nextInt();
            switch (ch)
            {
                case 1:c.removeFirst();break;
                case 2:c.removeLast();break;
                case 3:
                    System.out.println("enter your complaint");
                    String complaint=sc.nextLine();
                    c.deleteComplaint(complaint);break;
                case 4:
                    System.out.println("exit");
            }
        }while(ch!=4);

    }

   public  void payment()throws Exception
    {
        int ch;
        Payment pt=new Payment();
        do
        {
            System.out.println(" 1.for show Hostel Payments");
            System.out.println(" 2.for show Student Payments");
            System.out.println(" 3.exit");
            ch= sc.nextInt();
            switch (ch)
            {
                case 1://hostel payment
                case 2:
                    pt.showstudentpayment();break;
                case 3:
                    System.out.println(" Exit..");break;
                default:
                    System.out.println("Invalid choice");break;
            }
        }
        while(ch!=3);
    }
    public void showLaundryRequests() throws Exception
    {
        Connection con = c.getconnection();
        String  sql = "select laundry.id,student.grno, student.student_name, student.room_no, laundry.cloth_count, laundry.status,laundry.request_date from student join laundry on student.grno = laundry.grno";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        boolean haspending = false;
        while (rs.next()) {
            haspending = true;
            System.out.println("=========================================================");
            System.out.println("Laundry ID :=> " + rs.getInt("id"));
            System.out.println("GR No :=> " + rs.getInt("grno"));
            System.out.println("Student Name :=>" + rs.getString("student_name"));
            System.out.println("Cloth :=>" + rs.getInt("cloth_count"));
            System.out.println("Request Date :=>" + rs.getDate("request_date"));
            System.out.println("Status :=>" + rs.getString("status"));
            System.out.println("=========================================================");
        }

        if (!haspending) {
            System.out.println(" No pending laundry requests.");
            return;
        }

        System.out.println("Do you want to mark any request as Completed? (yes/no)");
        String ch=sc.nextLine();
        if (ch.equalsIgnoreCase("yes")) {
            System.out.print("Enter laundry id to mark as completed: ");
            int lid = sc.nextInt();
            sql="update laundry set status='Completed' where id=?";
            pst=con.prepareStatement(sql);
            pst.setInt(1,lid);
            int updated = pst.executeUpdate();
            if (updated > 0) {
                System.out.println(" -Laundry request marked as Completed.");
            } else {
                System.out.println(" -Laundry ID not found or already completed.");
            }
        }
    }
   public void leavereport()throws Exception
    {
        Connection con=c.getconnection();
        String sql="select*from leave_requests";
        PreparedStatement cst = con.prepareStatement(sql);
        ResultSet rs = cst.executeQuery();

        while (rs.next()) {
            int grno = rs.getInt("grno");
            String reason = rs.getString("reason");
            String from = rs.getString("from_date");
            String to = rs.getString("to_date");
            System.out.println("GR No :=> " + grno);
            System.out.println("Reason :=> " + reason);
            System.out.println("From :=> " + from);
            System.out.println("To :=> " + to);
            System.out.println("----------------------");
        }
        System.out.println(" Approved or not");
        String app=sc.nextLine();
        if(app.equals("true"))
        {
            String sql2="update leave_requests set status = 'approved'";
            PreparedStatement pst=con.prepareStatement(sql2);
            int r=pst.executeUpdate();
        }
    }


}

class Payment
{
    Scanner sc=new Scanner(System.in);
    connection2 c=new connection2();

    public void makepayment() throws Exception
    {
        System.out.print("Enter your GR No :==> ");
        int grno = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter your password :==> ");
        String password = sc.nextLine();
        Connection con = c.getconnection();
        String sql = "select*from student where grno = ? and password = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, grno);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        if (rs==null)
        {
            System.out.println(" Invalid GR No or Password. Payment Cancelled.");
            return;
        }

        int choice;
        do {

            System.out.println("1. Pay by UPI");
            System.out.println("2. Pay by Debit Card");
            System.out.println("3. Cancel");
            System.out.print("Select Payment Method : ==>");
            choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    byupi(grno);break;
                case 2:
                    bydebitCard(grno);break;
                case 3:
                    System.out.println("EXIT ....");break;
                default:
                    System.out.println("Invalid choice.");break;
            }
        } while (choice != 3);
    }

    public void showstudentpayment()throws Exception//connection for rector class
    {
        Connection con=c.getconnection();
        String sql="select*from payment ";
        PreparedStatement pst=con.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();
        while(rst.next())
        {
            System.out.println(".............................");
            System.out.println(".............................");
            System.out.println("GRNo :=> " + rst.getInt("grno"));
            System.out.println("Amount Paid  :=> ₹" + rst.getDouble("amount_paid"));
            System.out.println("Payment Method :=> " + rst.getString("payment_method"));
            System.out.println("Payment Date :=> " + rst.getDate("payment_date"));

        }
    }
    public void byupi(int grno)throws Exception
    {

        Connection con = c.getconnection();


        String cp="select*from payment where grno=?";
        PreparedStatement pst2=con.prepareStatement(cp);
        pst2.setInt(1,grno);
        ResultSet rs= pst2.executeQuery();
        if(rs.next())
        {
            System.out.println("your payment already done");
            return;
        }
        else
        {
            System.out.print("Enter your UPI ID : ==> ");
            String upi = sc.nextLine();
            if (!upi.matches("^[\\w.-]+@[\\w]+$")) {
                System.out.println("Invalid UPI ID format. Try again:");
                System.out.print("Enter your UPI ID again : ==> ");
                upi = sc.nextLine();

                if (!upi.matches("^[\\w.-]+@[\\w]+$")) {
                    System.out.println(" Payment cancelled.");
                    return;
                }
            }
            double amount = 50000;
            System.out.print("Enter payment amount :==> " + amount);


            String sql = "insert into payment (grno,amount_paid,payment_mode,payment_date) values(?,?,?,CURDATE())";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, grno);
            pst.setDouble(2, amount);
            pst.setString(3, "UPI");
            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("payment successful");
            } else {
                System.out.println("payment not successful");
            }
        }
    }
    public void bydebitCard(int grno) throws Exception {
        Connection con = c.getconnection();
        sc.nextLine();
        String cp = "select*from payment where grno=?";
        PreparedStatement pst2 = con.prepareStatement(cp);
        pst2.setInt(1, grno);
        ResultSet rs = pst2.executeQuery();
        if (rs.next()) {
            System.out.println("your payment already done");
            return;
        } else {
            System.out.print("Enter your 16-digit Debit Card number : ==> ");
            String cardNumber = sc.nextLine();

            if (!cardNumber.matches("\\d{16}")) {
                System.out.println("Invalid card number. Try again:");
                System.out.print("Enter your Debit Card number again : ==> ");
                cardNumber = sc.nextLine();

                if (!cardNumber.matches("\\d{16}")) {
                    System.out.println(" Payment cancelled.");
                    return;
                }
            }
            double amount = 50000;
            System.out.print("Enter payment amount :==> " + amount);
            String sql = "insert into payment (grno, amount_paid, payment_mode, payment_date) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, grno);
            pst.setDouble(2, amount);
            pst.setString(3, "Debit Card");

            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("Payment successful via Debit Card.");
            } else {
                System.out.println("Payment failed.");
            }
        }
    }
}
class Student {
    private int grno;
    private String room_no;
    private String password;
    private String name;
    private String mobileno;
    connection2 c = new connection2();
    Scanner sc = new Scanner(System.in);

    public Student(int grno, String room_no, String password, String name, String mobileno) {
        this.grno = grno;
        this.room_no = room_no;
        this.password = password;
        this.name = name;
        this.mobileno = mobileno;
    }

    public int getgrno() {
        return grno;
    }

    public void segGrno(int grno) {
        this.grno = grno;
    }

    public String getroomno() {
        return room_no;
    }

    public void setRoomNo(String roomNo) {
        this.room_no = room_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileno;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileno = mobileNo;
    }

    public void updatepassword(String newPassword) throws Exception
    {
        Connection con = c.getconnection();
        String sql = "update student set password=? where grno=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, newPassword);
            pst.setInt(2, this.grno);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Password updated successfully.");
                this.password = newPassword;
            } else {
                System.out.println("Password update Failed.");
            }

    }

    public void updateMobile(String newmobile) throws Exception {
        Connection con = c.getconnection();
        String sql = "update student set mobile_no = ? where grno = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, newmobile);
        pst.setInt(2, this.grno);
        int r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("Mobile number updated successfully.");
            this.mobileno = newmobile;
        } else {
            System.out.println("Mobile update Failed.");
        }
    }

    public void laundry() throws Exception {
        Connection con = c.getconnection();
        System.out.print("Enter your total no of cloth : ==>");
        int cloth;
        while (true) {
            cloth = sc.nextInt();
            if (cloth <= 0) {
                System.out.println("Please enter valid clothes");
            } else {
                break;
            }
        }
        String sql = "insert into laundry (grno,cloth_count,request_date,status) values (?, ?, CURDATE(), 'Pending')";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, this.grno);
        pst.setInt(2, cloth);
        int r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("request is applied");
        } else {
            System.out.println("request makes some error");
        }
    }

    public void leave() throws Exception
    {
        Connection con=c.getconnection();
        System.out.print("Enter your GRNo : ==>");
        int grno=sc.nextInt();
        System.out.print("Enter date from : ==>");
        String from=sc.nextLine();
        sc.nextLine();
        System.out.print("Enter to date : ==>");
        String to=sc.nextLine();
        System.out.print("Enter Reason : ==>");
        String reason=sc.nextLine();
        if(from==null&&to==null)
        {
            System.out.println("please enter from and to date  form of YYYY-MM-DD");
            return;
        }
        String sql="insert into leave_requests (grno,reason,from_date,to_date) values(?,?,?,?)";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setInt(1,grno);
        pst.setString(2,reason);
        pst.setString(3,from);
        pst.setString(4,to);
        int r=pst.executeUpdate();
        if(r>0)
        {
            System.out.println("Successful");
        }
    }
}
class Food
{
    Scanner sc = new Scanner(System.in);

    // Rector adds meals day-wise
    public void foodByDay() throws Exception
    {
        File f = new File("D://foodmenu.txt");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

        HashMap<String, String> foodMenu = new HashMap<>();

        System.out.print("Enter day in (Monday--Tuesday )==>");
        String day;
        while(true)
        {
            day=sc.nextLine().trim();
            String formattedDay = day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();
            if(formattedDay.equalsIgnoreCase("Monday")||formattedDay.equalsIgnoreCase("Tueday")||formattedDay.equalsIgnoreCase("Wednesday")||formattedDay.equalsIgnoreCase("Turshday")||formattedDay.equalsIgnoreCase("Friday")||formattedDay.equalsIgnoreCase("Saturday")||formattedDay.equalsIgnoreCase("sunday"))
            {
                break;
            }
            else
            {
                System.out.println("please enter valid name of day in week");
                continue;
            }
        }


        System.out.print("Enter morning meal: ");
        String morning = sc.nextLine();

        System.out.print("Enter noon meal: ");
        String noon = sc.nextLine();

        System.out.print("Enter night meal: ");
        String night = sc.nextLine();


        String meals = "Morning: " + morning + ", Noon: " + noon + ", Night: " + night;


        foodMenu.put(day, meals);


        bw.write(day + " -> " + meals);
        bw.newLine();

        bw.close();
        fw.close();

        System.out.println("Meal of " + day);
    }


    public void displayMenu() throws Exception
    {
        File f = new File("D://foodmenu.txt");
        if (!f.exists())
        {
            System.out.println("No menu available yet.");
            return;
        }

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;

        System.out.println(" Food Menu");
        while ((line = br.readLine()) != null)
        {
            System.out.println(line);
        }

        br.close();
        fr.close();
    }
}
class Node {
    String complaint;
    Node next;

    public Node(String complaint) {
        this.complaint = complaint;
        this.next = null;
    }
}


class ComplaintList {
    Node head;

    public void addFirst(String complaint)
    {
        Node newNode = new Node(complaint);
        newNode.next = head;
        head = newNode;
        System.out.println("Complaint added at first.");
    }

    // Add complaint at last
    public void addLast(String complaint) {
        Node newNode = new Node(complaint);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Complaint added at last.");
    }

    // Remove first complaint
    public void removeFirst() {
        if (head == null) {
            System.out.println("No complaints to remove.");
            return;
        }
        System.out.println("Removed: " + head.complaint);
        head = head.next;
    }

    // Remove last complaint
    public void removeLast() {
        if (head == null) {
            System.out.println("No complaints to remove.");
            return;
        }
        if (head.next == null) {
            System.out.println("Removed: " + head.complaint);
            head = null;
            return;
        }
        Node temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        System.out.println("Removed: " + temp.next.complaint);
        temp.next = null;
    }
    public void deleteComplaint(String complaint) {
        if (head == null) {
            System.out.println("No complaints to delete.");
            return;
        }
        if (head.complaint.equalsIgnoreCase(complaint)) {
            System.out.println("Deleted: " + head.complaint);
            head = head.next;
            return;
        }
        Node temp = head;
        while (temp.next != null && !temp.next.complaint.equalsIgnoreCase(complaint)) {
            temp = temp.next;
        }
        if (temp.next == null) {
            System.out.println("Complaint not found.");
        } else {
            System.out.println("Deleted: " + temp.next.complaint);
            temp.next = temp.next.next;
        }
    }
    // Display all complaints
    public void display() {
        if (head == null) {
            System.out.println("No complaints available.");
            return;
        }
        System.out.println("All Complaints:");
        Node temp = head;
        while (temp != null) {
            System.out.println("- " + temp.complaint);
            temp = temp.next;
        }
    }
}
