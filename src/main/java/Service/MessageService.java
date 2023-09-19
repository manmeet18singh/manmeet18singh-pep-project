package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDao;

    public MessageService() {
        messageDao = new MessageDAO();
    }

    public MessageService(MessageDAO messageDao) {
        this.messageDao = messageDao;
    }

    public Message addMessage(Message message, boolean accountExists) {
        if (message.message_text != "" && message.message_text.length() < 255 && accountExists) {
            return messageDao.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        // If the message existed, return now-deleted message. If the message did not
        // exist, the response body shoul d be empty.

        if (getMessageById(messageId) != null) {
            return messageDao.deleteMessageById(messageId);
        } else {
            return null;
        }
    }

    public Message patchMessageById(int messageId, Message message) {

        if (message.message_text != "" && message.message_text.length() < 255 && getMessageById(messageId) != null) {
            return messageDao.patchMessageById(messageId, message);
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageDao.getAllMessagesByAccountId(accountId);
    }
}
