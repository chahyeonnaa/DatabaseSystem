import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
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
            System.out.println("1. 환자 모드                       2. 관리자 모드");
            System.out.println("100. 시스템 종료                               ");
            System.out.println("---------------------------------------------");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1 : User();break;
                case 2 : Manager();break;
                default : System.out.println("종료");
            }
            if (choice==100)
            {
                break;
            }
        }
    }

    private void User()
    {
        while(true)
        {
            System.out.println("-----------------  DB 병원  ------------------");
            System.out.println(" [회원가입 및 확인]                             ");
            System.out.println("1. 환자 등록                     2. 환자 등록 확인");
            System.out.println(" [우리병원 진료과목]                             ");
            System.out.println("3. 진료과목 조회                                ");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");
            System.out.println(" [우리병원 의료진]                              ");
            System.out.println("4. 전체 의료진 조회    5. 의료진 조회 - 진료과목 검색");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");
            System.out.println(" [예약]                                       ");
            System.out.println("6. 예약하기      7. 예약 조회 - 진료과목, 예약일 검색");
            System.out.println("8. 예약 변경                                   ");
            System.out.println("100. 종료                                     ");
            System.out.println("*  *  *  *  *  *  *  *  *  *  *  *  *  *  *  ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1 : User_Insert(); break;
                case 2 : User_list();break;
                case 3 : medical_depart_View();break;
                case 4 : doctor_View();break;
                case 5 : doctor_Search();break;
                case 6 : Reserve();break;
                case 7 : depart_Reserve_View();break;
                case 8 : Change_Reserve();break;
            }
            if (choice==100)
            {
                break;
            }
        }
    }

    private void Manager()
    {
        while(true)
        {
            System.out.println("-----------------  DB 병원  ------------------");
            System.out.println("1. 전체 사용자 조회                             ");
            System.out.println("2. 진료과목 추가                3. 의료진 추가    ");
            System.out.println("4. 예약 조회 - 의사 검색    5. 예약 조회 - 날짜 검색 ");
            System.out.println("6. (진료과,날짜) 별 세부 예약 실적 보기             ");
            System.out.println("7. 의사별 전체 예약 실적 보기                      ");
            System.out.println("8. 날짜별 예약 실적 횟수 보기                      ");
            System.out.println("9. 예약 내역 삭제 - 날짜 입력                     ");
            System.out.println("10. 진료비 청구                                 ");
            System.out.println("100. 관리자 모드 종료                            ");
            System.out.println("----------------------------------------------");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1 : User_View();break;
                case 2 : Depart_add();break;
                case 3 : Doctor_add();break;
                case 4 : doctor_Reserve_View();break;
                case 5 : date_Reserve_View();break;
                case 6 : Reserve_Performance_Depart_date();break;
                case 7 :Reserve_Performance_Doctor();break;
                case 8 :Reserve_By_Date();break;
                case 9 :Reserve_Delete();break;
                case 10 :Charge_Money();break;
            }
            if (choice==100)
            {
                break;
            }
        }
    }

    private void Depart_add()
    {
        try {
            System.out.println("추가하고자 하는 진료과목을 입력해주세요.");

            String depart=sc.nextLine();

            String sql ="insert into 진료과 (name) values (?)";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setNString(1, depart);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Doctor_add()
    {
        try {
            System.out.println("추가하고자 하는 의료진 이름, 아이디, 진료과목 번호를 입력해주세요.");

            st = new StringTokenizer(br.readLine());

            String name= st.nextToken();
            String id= st.nextToken();
            String de_number= st.nextToken();


            String sql ="insert into 의료진 values (?,?,?)";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setNString(1, name);
            pmt.setNString(2, id);
            pmt.setNString(3, de_number);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
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
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("ajw2143","6");
        map.put("jsh7394","6");

        map.put("csh5123","8");
        map.put("yjs8976","8");

        map.put("kjw3642","13");
        map.put("kys1204","13");
        map.put("eej2341","1");

        try {
            System.out.println("환자 아이디, 이름, 진료과목 번호, 의사번호를 입력하세요.");

            st = new StringTokenizer(br.readLine());

            String id= st.nextToken();
            String name= st.nextToken();
            String depart_num= st.nextToken();
            String doctor_num= st.nextToken();

            if ((map.get(doctor_num))==null)
            {
                throw new Exception("진료과목 번호와 의사번호가 일치하지 않습니다!");
            }


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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void depart_Reserve_View()
    {
        try {
            System.out.println("진료과목명, 예약일을 입력해주세요.");
            st = new StringTokenizer(br.readLine());

            String depart= st.nextToken();
            String date= st.nextToken();

            String sql ="select p_id, p_name, name, doctor_num, reserve_time from 예약, 진료과 where name=? and depart_number=number and"+
                    " reserve_time like ?";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setNString(1, depart);
            pmt.setNString(2, "%"+date+"%");

            ResultSet rs = pmt.executeQuery();

            int i=0;
            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) +
                        " " + rs.getString(3) + " " + rs.getString(4)+" "+ rs.getDate(5));
                i++;
            }
            if (i==0)
            {
                System.out.println("예약 내역이 없습니다.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void doctor_Reserve_View()
    {
        try {
            System.out.println("의사 이름을 입력해주세요.");
            String doctor_name=sc.nextLine();
            int i=0;

            String sql ="select ac.p_id, ac.p_name, ab.doctor_name from (select doctor_name,doctor_num from 의료진 where doctor_name=?) ab, 예약 ac where ab.doctor_num = ac.doctor_num";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setNString(1, doctor_name);

            ResultSet rs = pmt.executeQuery();


            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) +
                        " " + rs.getString(3));
                i++;
            }
            if (i==0)
            {
                System.out.println("예약 내역이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void date_Reserve_View()
    {
        try {
            System.out.println("날짜를 입력해주세요.");
            String date=sc.nextLine();
            int i=0;

            String sql ="select * from 예약 where reserve_time like ?";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setString(1, "%"+date+"%");

            ResultSet rs = pmt.executeQuery();


            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) +
                        " " + rs.getInt(3) + " " + rs.getString(4)+" "+rs.getDate(5));
                i++;
            }
            if (i==0)
            {
                System.out.println("예약 내역이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Reserve_Performance_Depart_date()
    {
        try {
            System.out.println("날짜를 입력해주세요.");
            String date=sc.nextLine();
            int i=0;

            String sql ="select depart_number, name, count(*) from 예약,진료과"
                    +" where reserve_time like ? and depart_number=number group by depart_number";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setString(1, "%"+date+"%");

            ResultSet rs = pmt.executeQuery();


            while(rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) +
                        " " + rs.getInt(3));
                i++;
            }
            if (i==0)
            {
                System.out.println("예약 내역이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Reserve_Performance_Doctor()
    {
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select c.name,doctor_name,a.doctor_num, count(*) from 예약 a, 의료진 b,진료과 c"+
                    " where a.doctor_num=b.doctor_num and b.depart_number=c.number group by doctor_num order by count(*) desc");
            while(rs.next())
                System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "
                        +rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Reserve_By_Date()
    {
        try {
            System.out.println("날짜를 입력해주세요.");
            String date=sc.nextLine();

            String sql ="select sum(abb.summ) from (select reserve_time, count(*) summ from 예약 where reserve_time like ? group by reserve_time) abb";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setString(1, "%"+date+"%");

            ResultSet rs = pmt.executeQuery();


            if(rs.next()) {
                System.out.println(date+"의 예약 횟수는 "+rs.getInt(1)+" 입니다.");
            }
            else
            {
                System.out.println("조회 내역이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Change_Reserve()
    {
        try {
            System.out.println("변경하려는 예약일자를 입력해주세요. (YYYY-MM-DD 00:00)");
            String change_date=sc.nextLine();

            System.out.println("환자 아이디, 변경 전 원래 예약일을 입력해주세요.");
            st = new StringTokenizer(br.readLine());
            String id= st.nextToken();
            String origin_date= st.nextToken();

            String sql ="update 예약 set reserve_time=? where p_id=? and reserve_time like ?";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setString(1, change_date);
            pmt.setString(2, id);
            pmt.setString(3, origin_date+"%");

            int result=pmt.executeUpdate();
            if(result==1)
            {
                System.out.println("예약이 정상적으로 변경되었습니다.");
            }
            else
            {
                System.out.println("예약 내역이 없습니다.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void Reserve_Delete()
    {
        try {
            System.out.println("삭제할 날짜를 입력해주세요.");
            String date=sc.nextLine();

            String sql ="delete from 예약 where reserve_time like ?";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setString(1, date+"%");

            int result=pmt.executeUpdate();
            if(result==1)
            {
                System.out.println("삭제 완료");
            }
            else
            {
                System.out.println("예약 내역이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Charge_Money()
    {
        try {
            System.out.println("진료비를 청구하고자 하는 환자 아이디, 진료과목 번호, 의사 번호, 비용을 입력해주세요.");

            st = new StringTokenizer(br.readLine());

            String patient_id= st.nextToken();
            String depart_num= st.nextToken();
            String doc_number= st.nextToken();
            String expense= st.nextToken();


            String sql ="insert into 진료비 values (?,?,?,?)";

            PreparedStatement pmt = con.prepareStatement(sql);

            pmt.setString(1, patient_id);
            pmt.setInt(2, Integer.parseInt(depart_num));
            pmt.setString(3, doc_number);
            pmt.setInt(4, Integer.parseInt(expense));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args) {

        Term_project tp = new Term_project();
        tp.Basic();

    }
}
