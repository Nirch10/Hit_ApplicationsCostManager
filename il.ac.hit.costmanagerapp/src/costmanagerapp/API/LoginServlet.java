package costmanagerapp.API;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import costmanagerapp.lib.DAO.IUsersDAO;
import costmanagerapp.lib.Models.User;
import org.json.JSONObject;
public class LoginServlet extends HttpServlet {

    private IUsersDAO usersDAO;

    public LoginServlet(IUsersDAO usersDAO){
        this.usersDAO = usersDAO;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String requestUrl = request.getRequestURI();
        String userGuid = requestUrl.substring("/login/".length());
        //User user =usersDAO.getUser(userGuid);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){

    }


}
