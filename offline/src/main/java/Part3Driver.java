import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part3Driver {

    public static void main(String[] args) throws SQLException {
        //Calculate user profile
        UserProfileDao uDao = new UserProfileDao();
        //find all users
        List<String> userIds = uDao.findAllUserId();
        //build user profile vector for each user
        for(String userId : userIds){
            Map<String, Double> profile = uDao.calculateUserProfile(userId);
            uDao.addUserProfile(userId, profile);
        }

        //Calculate suggestions in Related Content approach
        SuggestionDao sDao = new SuggestionDao();
        List<Integer> postIds = sDao.findAllPostId();
        //find related content and add to database
        for(Integer pid : postIds){
            Set<Integer> related = sDao.computeRelatedContent(pid);
            sDao.addRelateContent(pid, related);
        }
        //generate suggestions for each user and save to database
        for(String userId : userIds){
            Set<Integer> suggest = sDao.generateSuggestion(userId);
            sDao.addSuggestions(userId, suggest);
        }
    }

}
