package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class QueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private String authToken;


    @PostMapping("/query")
    public List<Map<String, Object>> executeQuery(@RequestBody String query, @RequestHeader("Authorization") String authorizationHeader) throws SQLInvalidAuthorizationSpecException {

        String token = authorizationHeader.replace("Bearer ", "");

        System.out.println("Received query request with token="+token+"\n and query="+query);

        // Check if the user is authorized to execute SQL queries
        if (!isAdmin(token)) {
            throw new SQLInvalidAuthorizationSpecException("Only admins can execute SQL queries");
        }

        List<String> queries = List.of(query.split(";"));

        return jdbcTemplate.queryForList(queries.get(0).replace(";","") + ";");
        // Execute the SQL queries using JdbcTemplate
//        List<List<Map<String, Object>>> output = new ArrayList<>();
//        for (String q : queries) {
//            output.add(jdbcTemplate.queryForList(q.replace(";","") + ";"));
//        }
//        return output;
    }

    private boolean isAdmin(String token) {
        return authToken.equals(token);
    }

}
