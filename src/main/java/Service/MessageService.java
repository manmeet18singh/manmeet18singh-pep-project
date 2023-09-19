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
}
