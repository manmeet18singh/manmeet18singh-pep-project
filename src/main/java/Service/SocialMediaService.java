package Service;

import java.util.HashMap;

import DAO.SocialMediaDAO;
import Model.Account;

public class SocialMediaService {
    private SocialMediaDAO socialMediaDao;

    public SocialMediaService()
    {
        socialMediaDao = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO smDao)
    {
        this.socialMediaDao = smDao;
    }

    public HashMap<String, Account> getAllUsernames()
    {
        return socialMediaDao.getAllUsernames();
    }

    public Account addAccount(Account account)
    {
        // Check if username is not blank, password is longer than 4 chars and username is unique
        if (account.username != "" && account.password.length() > 4 && !getAllUsernames().containsKey(account.username))
        {
            return socialMediaDao.insertAccount(account);
        }
        return null;
    }
}
