import java.sql.*;
import java.util.Scanner;

public class basic_program {
    private Scanner sc = new Scanner(System.in);
    Connection con;

    // 생성자 만들어서 SQL 연결
    public basic_program() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(
                    "jdbc:mysql://192.168.79.129:4567/madang",
                    "hyeona-cha","skan0504@");
        }catch(Exception e){ System.out.println(e);}
    }

    public void Basic() {
        while(true) {
            System.out.println("1. 삽입 2. 삭제 3. 검색 4. 종료 ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1 : Insert(); break;
                case 2 : Delete();break;
                case 3 : View();break;
                default : System.out.println("종료");
            }
            if (choice==4)
            {
                break;
            }
        }
    }

    private void Insert() {
        try {
            PreparedStatement ps= con.prepareStatement("INSERT INTO Book values(?,?,?,?)");
            ps.setInt(1, 23);
            ps.setString(2, "컴퓨터 보안");
            ps.setString(3, "pearson");
            ps.setInt(4, 35000);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Delete() {
        try {
            PreparedStatement ps= con.prepareStatement("ALTER TABLE Orders Drop FOREIGN KEY Orders_ibfk_2");
            ps.executeUpdate();
            PreparedStatement pps= con.prepareStatement("DELETE FROM Book WHERE bookname=?");
            pps.setString(1, "축구의 역사");
            pps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void View() {
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM Book");
            while(rs.next())
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+
                        " "+rs.getString(3)+" "+rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args) {

        basic_program bp = new basic_program();
        bp.Basic();
    }


}
