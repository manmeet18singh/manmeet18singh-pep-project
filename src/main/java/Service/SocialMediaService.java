package Service;

import DAO.SocialMediaDAO;

public class SocialMediaService {
    SocialMediaDAO smDao;

    public SocialMediaService()
    {
        smDao = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO smDao)
    {
        this.smDao = smDao;
    }
}
