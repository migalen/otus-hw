package ru.otus.config;

import ru.otus.messaging.db.DeleteUserDataRequestHandler;
import ru.otus.messaging.db.GetUserListDataRequestHandler;
import ru.otus.messaging.db.SaveUserDataRequestHandler;
import ru.otus.messaging.front.GetUserListDataResponseHandler;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Configuration
@RequiredArgsConstructor
public class MessageSystemConfig {

    private final AppProperties appProperties;

    @Bean
    public MessageSystem messageSystem() {return new MessageSystemImpl();}

    @Bean
    public CallbackRegistry callbackRegistry() { return new CallbackRegistryImpl();}

    @Bean(name = "databaseMsClient")
    public MsClient databaseMsClient(
            GetUserListDataRequestHandler getUserListDataRequestHandler,
            SaveUserDataRequestHandler saveUserDataRequestHandler,
            DeleteUserDataRequestHandler deleteUserDataRequestHandler,
            MessageSystem messageSystem,
            CallbackRegistry callbackRegistry) {
        val requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.GET_USER_LIST_DATA, getUserListDataRequestHandler);
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER_DATA, saveUserDataRequestHandler);
        requestHandlerDatabaseStore.addHandler(MessageType.DELETE_USER_DATA, deleteUserDataRequestHandler);

        val databaseMsClient = new MsClientImpl(appProperties.getDatabaseServiceClientName(),
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);

        messageSystem.addClient(databaseMsClient);

        return databaseMsClient;
    }

    @Bean(name = "frontendMsClient")
    public MsClient frontendMsClient(
            GetUserListDataResponseHandler getUserListDataResponseHandler,
            MessageSystem messageSystem,
            CallbackRegistry callbackRegistry) {
        val requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.GET_USER_LIST_DATA, getUserListDataResponseHandler);

        val frontendMsClient = new MsClientImpl(appProperties.getFrontendServiceClientName(),
                messageSystem, requestHandlerFrontendStore, callbackRegistry);

        messageSystem.addClient(frontendMsClient);

        return frontendMsClient;
    }
}
