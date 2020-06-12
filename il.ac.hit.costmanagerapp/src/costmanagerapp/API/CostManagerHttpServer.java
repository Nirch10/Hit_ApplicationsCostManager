package costmanagerapp.API;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import costmanagerapp.lib.DAO.IRetailDAO;
import costmanagerapp.lib.DAO.ITransactionDAO;
import costmanagerapp.lib.DAO.IUsersDAO;
import costmanagerapp.lib.Models.RetailType;
import costmanagerapp.lib.Models.Transaction;
import costmanagerapp.lib.UsersPlatformException;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CostManagerHttpServer extends AbstractHttpServer<Transaction> {
    private static Collection<Transaction> transactions;
    private static List<RetailType> retailTypes;

    private RestModelConnector restModelConnector;
    private static JSObject jsObject;
    private static OutputStream outputStream;
    private Gson jsonCreator;


    public CostManagerHttpServer(@NotNull int portNum, IUsersDAO usersDAO, IRetailDAO retailDAO, ITransactionDAO transactionDAO) throws UsersPlatformException {
        this(portNum ,new RestModelConnector(usersDAO, retailDAO, transactionDAO));
    }

    public CostManagerHttpServer(int portNum, RestModelConnector restModelConnector) {
        port = portNum;
        jsonCreator = new Gson();
        transactions = new ArrayList<>();
        retailTypes = new ArrayList<>();
        this.restModelConnector = restModelConnector;
    }

    @Override
    public void start() throws UsersPlatformException {
        try{
            httpServer = HttpServer.create(new InetSocketAddress(port), port);
            httpServer.createContext("/", httpExchange -> defineUriAndDo(httpExchange));
            httpServer.start();
        } catch (IOException e) {
            throw new UsersPlatformException("Could not start HttpServerApi - " + e.getMessage());
        }
    }

    private void defineUriAndDo(HttpExchange httpExchange){
        String uri = httpExchange.getRequestURI().toString();
        try{
            switch (uri){
                case "/api/home/getAllTransactions":{
                    getTransactions(httpExchange);
                break;
                }
                case "/api/home/getUserTransactions":{
                    getUserTransactions(httpExchange);
                    break;
                }
                case "/api/home/getAllRetails":{
                    getRetails(httpExchange);
                }


            }
        } catch (UsersPlatformException e) {
            e.printStackTrace();
        }
    }

    private void getRetails(HttpExchange httpExchange) throws UsersPlatformException {
        try {
            Collection<RetailType> retails = restModelConnector.getRetailDAO().getRetails();
            responseMessage(httpExchange, 200, "Res" + retails.toString());
        } catch (UsersPlatformException e) {
            responseMessage(httpExchange, 400, e.getMessage());
        }
    }

    private void getUserTransactions(HttpExchange httpExchange) throws UsersPlatformException {
        String[] uri = httpExchange.getRequestURI().toString().split("/");
        int id = Integer.parseInt(uri[uri.length - 1]);
        try {
            Collection<Transaction> transactionsByUser = restModelConnector.getTransactionDAO().getTransactionsByUser(id);
            responseMessage(httpExchange, 200, "Res" + transactionsByUser.toString());
        } catch (UsersPlatformException e) {
            responseMessage(httpExchange, 400, e.getMessage());
        }

    }

    private void getTransactions(HttpExchange httpExchange) throws UsersPlatformException {
        Date fromDate = new Date(0,0,0);
        Date toDate = new Date();

        try {
            transactions = restModelConnector.getTransactionDAO().getTransactionsByDateRange(fromDate, toDate);
            responseMessage(httpExchange, 200, transactions.toString());
        } catch (UsersPlatformException e) {
            responseMessage(httpExchange, 400, e.getMessage());
        }

    }

    private void responseMessage(HttpExchange httpExchange, int resCode, String data) throws UsersPlatformException {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        try {
            httpExchange.sendResponseHeaders(resCode, bytes.length);
            outputStream = httpExchange.getResponseBody();
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            throw new UsersPlatformException("Server could not response correctly, " + e.getMessage());
        }

    }

    @Override
    public void stop() {

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
