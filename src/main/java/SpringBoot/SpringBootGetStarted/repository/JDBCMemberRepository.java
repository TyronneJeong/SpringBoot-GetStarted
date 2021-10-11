package SpringBoot.SpringBootGetStarted.repository;

import SpringBoot.SpringBootGetStarted.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JDBCMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO MEMBER(NAME) VALUES (?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, member.getName());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys(); // RETURN_GENERATED_KEYS 옵션에 따라 KEY 값이 리턴 된다.

            if(rs.next()){
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("ID 조회 실패");
            }
            return member;
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally{
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT ID, NAME FROM MEMBER WHERE ID = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally{
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "SELECT ID, NAME FROM MEMBER WHERE NAME = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally{
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT ID, NAME FROM MEMBER";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();

            while(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }

            return members;
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally{
            close(conn, pstmt, rs);
        }
    }

    // [datasource.getConnection()] vs [DataSourceUtils.getConnection()]
    // datasource.getConnection() 은 항상 새로운 connection 인스턴스를 리턴 한다.
    // DataSourceUtils.getConnection() 은 현재 스레드에서 활성화된 트랜잭션 정보가 있으면 해당 소스를 리턴하고
    // 만약 기활성화된 트랜잭션 정보가 없다면 datasource.getConnection() 와 동일하게 작동한다.
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    // 자원종료 메서드 (열었던 역순으로 차례로 종료)
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // ResultSet 종료
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // PreparedStatement 종료
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Connection 종료
        try {
            if (conn != null) {
                close(conn); // DataSourceUtils 를 통한 자원 해제
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DataSourceUtils 를 통한 자원 해제
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}