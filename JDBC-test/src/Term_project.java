import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Term_project {

    private Scanner sc = new Scanner(System.in);
    Connection con;

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static StringTokenizer st;

    public Term_project()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://192.168.79.129:4567/term_project",
                    "hyeona-cha","skan0504@");
        }catch(Exception e){ System.out.println(e);}

    }

    public void Basic() {
        while(true) {
            System.out.println("-----------------  DB 병원  ------------------");
            System.out.println("1. 사용자 등록                 2. 사용자 등록 확인");
            System.out.println("3. 전체 사용자 조회             4. 사용자 목록 조회");
            System.out.println("---------------------------------------------");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1 : User_Insert(); break;
                //case 2 : Delete();break;
                //case 3 : View();break;
                default : System.out.println("종료");
            }
            if (choice==4)
            {
                break;
            }
        }
    }

    private void User_Insert() {
        try {
            System.out.println("id, 이름, 휴대폰 번호, 생년월일을 입력해주세요.");

            st = new StringTokenizer(br.readLine());

            String id= st.nextToken();
            String name= st.nextToken();
            String phone= st.nextToken();
            String birth= st.nextToken();

            Date date = new Date(format.parse(birth).getTime());
            java.sql.Date date1 = java.sql.Date.valueOf(birth);


            PreparedStatement ps= con.prepareStatement("INSERT INTO 환자 values(?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setDate(4, date1);
            ps.executeUpdate();
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args) {

        Term_project tp = new Term_project();
        tp.Basic();

    }
}
