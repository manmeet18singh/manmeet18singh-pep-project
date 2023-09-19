package Service;

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

    public Account addAccount(Account account)
    {
        // Check if username is not blank, password is longer than 4 chars
        // We don't need to check if the username exists, as the SQL Table has a unique constraint
        if (account.username != "" && account.password.length() > 4)
        {
            return socialMediaDao.insertAccount(account);
        }
        return null;
    }

    public Account getAccount(Account account)
    {
        return socialMediaDao.getAccount(account);
    }
}
