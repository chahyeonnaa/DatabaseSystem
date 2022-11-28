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
            System.out.println(" [회원가입 및 확인]                             ");
            System.out.println("1. 사용자 등록                 2. 사용자 등록 확인");
            System.out.println("3. 전체 사용자 조회                             ");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");
            System.out.println(" [우리병원 진료과목]                             ");
            System.out.println("4. 진료과목 조회                                ");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");
            System.out.println(" [우리병원 의료진]                              ");
            System.out.println("5. 전체 의료진 조회    6. 진료과목으로 의료진 검색하기");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");
            System.out.println(" [예약]                                       ");
            System.out.println("7. 예약하기           8. 진료과목 별 예약 현황 조회");
            System.out.println("9. 의사 별 예약 현황 조회  10.날짜 별 예약 현황 조회 ");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");
            System.out.println("---------------------------------------------");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1 : User_Insert(); break;
                case 2 : User_list();break;
                case 3 : User_View();break;
                case 4 : medical_depart_View();break;
                case 5 : doctor_View();break;
                case 6 : doctor_Search();break;
                case 7 : Reserve();break;
                default : System.out.println("종료");
            }
            if (choice==100)
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

    private void User_list()
    {
        try {
            System.out.println("등록을 확인하고자 하는 id를 입력해주세요.");
            String id=sc.nextLine();

            String sql ="SELECT * FROM 환자 WHERE id=?";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setNString(1, id);

            ResultSet rs = pmt.executeQuery();


            if(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) +
                        " " + rs.getString(3) + " " + rs.getDate(4));
            }
            else
            {
                System.out.println("미등록 id 입니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void User_View()
    {
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM 환자");
            while(rs.next())
                System.out.println(rs.getString(1)+" "+rs.getString(2)+
                        " "+rs.getString(3)+" "+rs.getDate(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void medical_depart_View()
    {
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM 진료과");
            while(rs.next())
                System.out.println(rs.getInt(1)+" "+rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doctor_View()
    {
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT depart_number, name, doctor_name, doctor_num FROM 진료과,의료진 " +
                    "WHERE depart_number=number"+" ORDER BY name ASC");
            while(rs.next())
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "
                        +rs.getString(3)+" "+ rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void doctor_Search()
    {
        try {
            System.out.println("진료 과목을 입력해주세요.");
            String depart=sc.nextLine();

            String sql ="SELECT depart_number, name, doctor_name FROM 진료과, 의료진 "+
                    "WHERE name=? AND depart_number=number";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setNString(1, depart);

            ResultSet rs = pmt.executeQuery();
            int i=0;

            while(rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2)
                        + " " + rs.getString(3));
                i++;
            }
            if (i==0)
            {
                System.out.println("우리병원에 존재하지 않는 진료과목 입니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Reserve()
    {
        try {
            System.out.println("환자 아이디, 이름, 진료과목 번호, 의사번호를 입력하세요.");

            st = new StringTokenizer(br.readLine());

            String id= st.nextToken();
            String name= st.nextToken();
            String depart_num= st.nextToken();
            String doctor_num= st.nextToken();

            PreparedStatement ps= con.prepareStatement("INSERT INTO 예약 (p_id,p_name,depart_number,doctor_num) values(?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setInt(3, Integer.parseInt(depart_num));
            ps.setString(4, doctor_num);
            int result=ps.executeUpdate();
            if(result==1)
            {
                System.out.println("예약이 정상적으로 완료되었습니다!");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[]args) {

        Term_project tp = new Term_project();
        tp.Basic();

    }
}
