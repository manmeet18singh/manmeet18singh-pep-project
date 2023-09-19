package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);
        
        return app;
    }

    /**
     * User Story 1: Our API should be able to process new User registrations.
     * 
     * @param context
     */
    private void postNewAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account accountReceived = mapper.readValue(ctx.body(), Account.class);
        Account accountToAdd = accountService.addAccount(accountReceived);

        if (accountToAdd != null) {
            ctx.json(mapper.writeValueAsString(accountToAdd));
        } else {
            ctx.status(400);
        }
    }

    /**
     * User Story 2: Our API should be able to process User logins
     * 
     * @param context
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account accountReceived = mapper.readValue(ctx.body(), Account.class);
        Account accountLogin = accountService.getAccount(accountReceived);

        if (accountLogin != null) {
            ctx.json(mapper.writeValueAsString(accountLogin));
        } else {
            ctx.status(401);
        }
    }

    /**
     * User Story 3: Our API should be able to process the creation of new messages.
     * 
     * @param context
     */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageReceived = mapper.readValue(ctx.body(), Message.class);
        // check if account user exists
        boolean accountExists = accountService.doesAccountIdExist(messageReceived.posted_by);
        Message messageToAdd = messageService.addMessage(messageReceived, accountExists);

        if (messageToAdd != null) {
            ctx.json(messageToAdd);
        } else {
            ctx.status(400);
        }
    }

    /**
     * User Story 4: Our API should be able to retrieve all messages.
     * 
     * @param context
     */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * User Story 5: Our API should be able to retrieve a message by its ID.
     * 
     * @param context
     */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    /**
     * User Story 6: Our API should be able to delete a message identified by a
     * message ID.
     * 
     * @param context
     */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }

    /**
     * User Story 7: Our API should be able to update a message text identified by a
     * message ID.
     * 
     * @param context
     */
    private void patchMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message messageReceived = mapper.readValue(ctx.body(), Message.class);
        
        Message messageToAdd = messageService.patchMessageById(messageId, messageReceived);

        if (messageToAdd != null) {
            ctx.json(messageToAdd);
        } else {
            ctx.status(400);
        }
    }

    /**
     * User Story 8: Our API should be able to retrieve all messages written by a particular user.
     * 
     * @param context
     */
    private void getAllMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
        ctx.json(messages);
    }
}