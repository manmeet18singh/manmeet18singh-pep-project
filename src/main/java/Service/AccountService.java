package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO socialMediaDao;

    public AccountService() {
        socialMediaDao = new AccountDAO();
    }

    public AccountService(AccountDAO smDao) {
        this.socialMediaDao = smDao;
    }

    public Account addAccount(Account account) {
        // Check if username is not blank, password is longer than 4 chars
        // We don't need to check if the username exists, as the SQL Table has a unique
        // constraint
        if (account.username != "" && account.password.length() > 4) {
            return socialMediaDao.insertAccount(account);
        }
        return null;
    }

    public Account getAccount(Account account) {
        return socialMediaDao.getAccount(account);
    }

}
