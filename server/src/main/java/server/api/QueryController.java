package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

//import java.sql.SQLInvalidAuthorizationSpecException;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private String authToken;


    /**
     * SQL Query receiving endpoint
     * @param query the query (currently single) to be executed in the database
     * @param authorizationHeader the authorization header must contain a valid token,
     *                            sent to the client during the admin auth process.
     *                            now the client must send the same one back in order to
     *                            execute sql queries.
     * @return a list of maps header/entry.
     * with pain decrypted in the frontend into tables
     * throws SQLInvalidAuthorizationSpecException invalid token,
     * user does not have authorization to access the database
     */
    @PostMapping("/query")
    public List<Map<String, Object>> executeQuery(
            @RequestBody String query, @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        System.out.println("Received query request with token="+token+"\n and query="+query);

        // Check if the user is authorized to execute SQL queries
        if (!isAdmin(token)) {
            List<Map<String, Object>> output = new ArrayList<>();
            Map<String, Object> message = new HashMap<>();
            message.put("AUTH Exception", "You are not authorised.");
            output.add(message);
            return output;
            //throw new SQLInvalidAuthorizationSpecException("Only admins can execute SQL queries");
        }

        String instruction = query.split(" ")[0];

        // special case for ease of re-creating public board:
        if (query.toLowerCase().strip().equals("create public board")) {
            System.out.println("creating a public board");
            return runQuery("Board Create", "insert into boards " +
                    "(id, name, boardkey, password, f_color, b_color) values" +
                    "(0, 'Public Board','public','', 657956, 16316415);");
        }

        switch (instruction.toLowerCase()) {
            case "select":
                return jdbcTemplate.queryForList(query.replace(";","")+";");
            case "insert":
                return runQuery("insert", query);
            case "delete":
                return runQuery("delete", query);
            case "update":
                return runQuery("update", query);
            case "drop":
                return runQuery("drop", query);
            case "create":
                return runQuery("create", query);
            case "alter":
                return runQuery("alter", query);
            case "truncate":
                return runQuery("truncate", query);
            case "grant":
                return runQuery("grant", query);
            default:
                return null; // invalid sql query
        }
        //List<String> queries = List.of(query.split(";"));

        //return jdbcTemplate.queryForList(queries.get(0).replace(";","") + ";");
        // Execute the SQL queries using JdbcTemplate
//        List<List<Map<String, Object>>> output = new ArrayList<>();
//        for (String q : queries) {
//            output.add(jdbcTemplate.queryForList(q.replace(";","") + ";"));
//        }
//        return output;
    }

    private List<Map<String, Object>> runQuery(String instruction, String query) {
        List<Map<String, Object>> output = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        try {
            jdbcTemplate.execute(query.replace(";","")+";");
            message.put("Execute Output", instruction.toUpperCase()+" Successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            message.put("Execute Output", "Error : "+e.getMessage());
        }
        output.add(message);
        return output;
    }

    private boolean isAdmin(String token) {
        return authToken.equals(token);
    }

}
