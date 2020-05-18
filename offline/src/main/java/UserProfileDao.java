import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserProfileDao {
    //batch add
    public void addUserProfile(String userId, Map<String, Double> profile) throws SQLException {
        //connection
        Connection conn = DbUtil.getConnection();
        conn.setAutoCommit(false);
        //sql
        String sql = "REPLACE INTO userprofiles(user_id, word, score) values(?,?,?)";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);

        for(Map.Entry<String, Double> entry : profile.entrySet()){
            String w = entry.getKey();
            Double s = entry.getValue();
            ptmt.setString(1, userId);
            ptmt.setString(2, w);
            ptmt.setDouble(3, s);
            ptmt.addBatch();
        }
        //execute batch
        ptmt.executeBatch();
        conn.commit();
        return;
    }

    //find all favorites content by userId
    public List<String> findFavbyUserId(String userId) throws SQLException{
        Connection conn = DbUtil.getConnection();
        //get all fav post id
        String contentSql = "select content from posts where id in " +
                "(select post_id " +
                "from users u JOIN favorite f on u.user_id = f.user_id " +
                "where u.user_id = ?)";
        PreparedStatement ptmt = conn.prepareStatement(contentSql);
        List<String> contents = new ArrayList<>();
        ptmt.setString(1, userId);
        //execute
        ResultSet res = ptmt.executeQuery();
        while(res.next()){
            contents.add(res.getString("content"));
        }
        return contents;
    }

    //calculate user profile vector by userId
    public Map<String, Double> calculateUserProfile(String userId) throws SQLException{
        //(word, score)
        Map<String, Double> profile = new HashMap<>();
        List<String> contents = findFavbyUserId(userId);
        double size = contents.size();
        for(String content : contents){
            Set<String> tokens = StrUtil.getTokens(content);
            for(String kw : tokens){
                profile.put(kw, profile.getOrDefault(kw, 0.0) + 1);
            }
        }
        for(Map.Entry<String, Double> entry : profile.entrySet()){
            String k = entry.getKey();
            double f = entry.getValue();
            profile.put(k, f / size);
        }
        return profile;
    }

    //find all user_id
    public List<String> findAllUserId() throws SQLException{
        //connection
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "select user_id from users";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ResultSet res = ptmt.executeQuery();
        List<String> uids = new ArrayList<>();
        while(res.next()){
            uids.add(res.getString("user_id"));
        }
        return uids;
    }

}
