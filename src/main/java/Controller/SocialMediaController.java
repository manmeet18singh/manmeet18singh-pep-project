package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService smService ;

    public SocialMediaController()
    {
        this.smService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        return app;
    }

    /**
     * User Story 1: Our API should be able to process new User registrations.
     * 
     * @param context
     */
    private void postNewAccountHandler(Context ctx) throws JsonProcessingException 
    {
        ObjectMapper mapper = new ObjectMapper();
        Account accountReceived = mapper.readValue(ctx.body(), Account.class);
        Account accountToAdd = smService.addAccount(accountReceived);

        if(accountToAdd != null)
        {
            ctx.json(mapper.writeValueAsString(accountToAdd));
        }
        else
        {
            ctx.status(400);
        }
    }

    /**
     * User Story 2: Our API should be able to process User logins
     * 
     * @param context
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account accountReceived = mapper.readValue(ctx.body(), Account.class);
        Account accountLogin = smService.getAccount(accountReceived);

        if(accountLogin != null)
        {
            ctx.json(mapper.writeValueAsString(accountLogin));
        }
        else
        {
            ctx.status(401);
        }
    }

    /**
     * User Story 3: Our API should be able to process the creation of new messages.
     * 
     * @param context
     */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message messageReceived = mapper.readValue(ctx.body(), Message.class);
        // Message messageToAdd = 

    }

}