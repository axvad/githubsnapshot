package com.samtools.githubsnapshot.graphql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;


/**
 * Class for get data from GraphQL server.
 * @Root - url server with "/graphql" endpoint (usually)
 * @Token - need to Authorization in head HTTP request
 * @FileQuery - file with query structure, copypast form GraphQL Api. For example https://developer.github.com/v4/explorer/
 */

@Service
public class GQLClient {

    @Value("${graphql.server}")
    private String root;

    private File fileQuery;

    @Value("${graphql.query}")
    private String query;

    @Value("${graphql.key}")
    private String token;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public File getFileQuery() {
        return fileQuery;
    }

    public void setFileQuery(String fileQuery) {
        this.fileQuery = new File(fileQuery);
    }

    public void setQuery(String query){
        this.query = query; // normalizeStringForQuery(query);
    }

    public String getToken() {
        StringBuilder in = new StringBuilder();

        for (int i=0; i < this.token.length();i+=2){
            String p = this.token.substring(i,i+2);

            in.append((char)Long.parseLong(p,16));
        }
        return in.toString();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserData(String username){
        //todo delete sout
        System.out.printf("Searching data for user %s\n",username);

        String query;

        if (this.query == null) {

            //get query as JSON string
            query = gql2json(this.fileQuery);
        } else {

            //get query from application propertyes
            query = this.query;
        }

        query = query.replace("axvad", username);

        return executeQuery(this.getRoot(),this.getToken(),query);
    }

    private String executeQuery(String root, String token, String gqlQuery){

        System.out.printf("Query: %s\n", gqlQuery);

        String result = null;

        HttpURLConnection conn = createConnection(root, gqlQuery, token);

        long res_code = sendQuery(conn, gqlQuery);
        if (res_code==200) {
            result = getResult(conn);
            System.out.printf("Result: %s\n", result);
        }
        else
        {
            System.out.println("Error server response: "+res_code);
        }

        conn.disconnect();

        return result;
    }

    private HttpURLConnection createConnection(String rootURL, String body, String token){

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(rootURL).openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(60000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Length", "" + Integer.toString(body.getBytes().length));
            connection.setRequestProperty("Authorization", token);
            connection.setRequestProperty("Accept", "application/json");

            return connection;

        } catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private long sendQuery(HttpURLConnection conn, String body){

        byte data[] = null;

        try(OutputStream strm = conn.getOutputStream()) {

            data = body.toString().getBytes("UTF-8");

            strm.write(data);
            strm.flush();
            data = null;

            conn.connect();

            return (conn.getResponseCode());

        } catch (IOException ex){
            ex.printStackTrace();
        }

        return -1;
    }

    private String getResult(HttpURLConnection conn){


        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        // read the output from the server
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))){

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            reader.close();

            return stringBuilder.toString();

        } catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Convert file with text copypast from GitHUB GraphQL Api Explorer.
     * For devide query from variables use keywords (Uppercase, New line, no data at line) QUERY, VAR
     * @param filename - File for convert
     * @return JSON string ready for http post.
     */
    private static String gql2json(File filename){

        try {

            List<String> lines = Files.readAllLines(filename.toPath());


            StringBuilder part_body = new StringBuilder();
            String part_title = "";

            HashMap<String, String> parts = new HashMap<>();

            String root_key = "";

            LinkedList<Boolean> line_need_comma = new LinkedList<>();
            line_need_comma.add(false);

            //parsing file line by line
            for (String line : lines){

                if (line.startsWith("QUERY")) {

                    root_key = "query";
                }
                if (line.startsWith("VAR")) {

                    root_key = "variables";
                }

                //line is Keyword. Save keyword for adding after read all body.
                if (root_key.length() != 0) {

                    //next keyword recived. Body read complite. Push prev title keyword and body.
                    if (part_title.length() != 0) {

                        parts.put(part_title, part_body.toString());
                    }

                    part_title = root_key;
                    root_key = "";
                    part_body.delete(0,part_body.length());

                    continue;
                }

                line = normalizeStringForQuery(line);

                //check for need comma for add before next string
                if (line.matches("^(.+)([A-Za-z_0-9])$")) {
                    line_need_comma.add(true);
                } else {
                    line_need_comma.add(false);
                }


                //if keyword recived, than save line in body
                if (part_title.length() != 0) {

                    //add comma for open keys:value in the prev line
                    if (line_need_comma.poll() && !line.startsWith("}")) {
                        part_body.append(", ");
                    }

                    part_body.append(line);
                }
            }

            //add last part to result
            parts.put(part_title, part_body.toString());

            //create returned string
            StringBuilder result = new StringBuilder("{");

            parts.forEach((key,value)->result.append("\"").append(key).append("\":\"").append(value).append("\","));

            result.deleteCharAt(result.length()-1).append("}");

            return result.toString();

        } catch(IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * clear spase and create inner \" instead of "
     * @param in input string
     * @return cleared string
     */
    private static String normalizeStringForQuery(String in){
        //todo check \" is present?
        return in.trim().replace("\"","\\\"");
    }


    /**
     * develop test
     * @param args
     */
    public static void main(String[] args) {
        GQLClient client = new GQLClient();

        //client.setRoot(this.getRoot());
        //client.setToken("");
        //client.setFileQuery("/home/sam/Programming/Git/githubsnapshot/src/main/java/com/samtools/githubsnapshot/graphql/gitsnap.gql");

        //String user = client.getUserData("axvad");


        System.out.println();
    }
}