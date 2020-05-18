import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SuggestionDao {
    //Related content approach
    //batch add
    public void addRelateContent(Integer postId, Set<Integer> postIds) throws SQLException {
        //connection
        Connection conn = DbUtil.getConnection();
        conn.setAutoCommit(false);
        //sql
        String sql = "REPLACE INTO relatecontent(post_id, related_id) values(?,?)";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);

        for(Integer pid : postIds){
            ptmt.setInt(1, postId);
            ptmt.setInt(2, pid);
            ptmt.addBatch();
        }
        //execute batch
        ptmt.executeBatch();
        conn.commit();
        return;
    }

    //batch add
    public void addSuggestions(String userId, Set<Integer> postIds) throws SQLException {
        //connection
        Connection conn = DbUtil.getConnection();
        conn.setAutoCommit(false);
        //sql
        String sql = "REPLACE INTO suggestions(user_id, suggest_post_id) values(?,?)";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);

        for(Integer pid : postIds){
            ptmt.setString(1, userId);
            ptmt.setInt(2, pid);
            ptmt.addBatch();
        }
        //execute batch
        ptmt.executeBatch();
        conn.commit();
        return;
    }

    //return a set of related post_ids for a given postId
    public Set<Integer> computeRelatedContent(Integer postId) throws SQLException{
        Connection conn = DbUtil.getConnection();
        String sql = "select distinct post_id from favorite where user_id in (select user_id from favorite where post_id = ?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, postId);
        ResultSet res = ptmt.executeQuery();
        Set<Integer> related = new HashSet<>();
        while(res.next()){
            related.add(res.getInt("post_id"));
        }
        related.remove(postId);
        return related;
    }

    public List<Integer> findAllPostId() throws SQLException{
        //connection
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "select id from posts";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ResultSet res = ptmt.executeQuery();
        List<Integer> pids = new ArrayList<>();
        while(res.next()){
            pids.add(res.getInt("id"));
        }
        return pids;
    }

    public Set<Integer> getRelated(Integer postId) throws SQLException{
        //connection
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "select distinct related_id from relatecontent where post_id = ?";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, postId);
        ResultSet res = ptmt.executeQuery();
        Set<Integer> pids = new HashSet<>();
        while(res.next()){
            pids.add(res.getInt("related_id"));
        }
        return pids;
    }

    public Set<Integer> getPostsByUserId(String userId) throws SQLException{
        //connection
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "select id from posts where user_id = ?";
        //pre
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, userId);
        ResultSet res = ptmt.executeQuery();
        Set<Integer> pids = new HashSet<>();
        while(res.next()){
            pids.add(res.getInt("id"));
        }
        return pids;
    }

    public Set<Integer> generateSuggestion(String userId) throws SQLException{
        Set<Integer> suggestions = new HashSet<>();
        Set<Integer> allPosts = getPostsByUserId(userId);
        //add all related
        for(Integer postId : allPosts){
            Set<Integer> related = getRelated(postId);
            suggestions.addAll(related);
        }
        //remove me
        suggestions.removeAll(allPosts);
        return suggestions;
    }
}
