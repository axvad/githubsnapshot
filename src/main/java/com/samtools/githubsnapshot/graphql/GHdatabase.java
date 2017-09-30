package com.samtools.githubsnapshot.graphql;

//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.sun.java.util.jar.pack.ConstantPool;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import sun.net.www.http.HttpClient;

//import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class GHdatabase {
    public static void main(String[] args) {

        graphql();
        gethttp();

    }

    public static void graphql() {
        String schema = "type Query{hello: String}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        //Context contextForUser = YourGraphqlContextBuilder.getContextForUser(getCurrentUser());

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                //        .context(contextForUser)
                .build();


        ExecutionInput input = new ExecutionInput("{hello}", null, null, null, null);

        ExecutionResult executionResult = build.execute("{hello}");

        System.out.println(build);

        System.out.println(executionResult.getData().toString());
    }


    public static void gethttp() {

        String host = "https://api.github.com/graphql";//?access_token=d232c34bf533ce8950e7cb2bdac1c382800b07d2";

        String q1 = "{ \"query\": \"{user { login }}\" }"; // WORK!!!
        String q2 = "\"query\": \"query ($username: String!){repositoryOwner(login:$username) { repositories(first:10) { totalCount }}}\"";
        String v1 = "\"variables\":\"{ \\\"username\\\":\\\"axvad\\\"}\"";
        String query = "{ " + v1 + ", " + q2 + "}";

        byte data[] = null;

        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {

            URL url = new URL(host);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Mozilla/5");

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            con.setDoInput(true);
            con.setDoOutput(true);

            con.setRequestProperty("Content-Length", "" + Integer.toString(query.getBytes().length));
            con.setRequestProperty("Authorization", "token 9d4884327632b76fa902aa2ff8ad549c0b40c94a");
            con.setRequestProperty("Accept", "application/json");

            /*JSONPObject jsonObj = new JSONPObject();
            System.out.println(jsonObj.toString());
            */

            OutputStream strm = con.getOutputStream();
            data = query.toString().getBytes("UTF-8");

            strm.write(data);
            strm.flush();
            strm.close();
            data = null;
            System.out.println();


            con.connect();
            int responseCode = con.getResponseCode();
            System.out.println("Connect return code " + responseCode);

            if (responseCode != 200) {
                System.out.println("Connection error");
                //return;
            }

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            System.out.println("Returned data:");
            System.out.println(stringBuilder.toString());

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (reader != null) reader.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    /*
    public void callingGraph(){
        CloseableHttpClient client= null;
        CloseableHttpResponse response= null;

        client= HttpClients.createDefault();
        HttpPost httpPost= new HttpPost("https://api.github.com/graphql");

        httpPost.addHeader("Authorization","Bearer myToken");
        httpPost.addHeader("Accept","application/json");

        JSONObject jsonObj = new JSONObject();
        jsonobj.put("query", "{repository(owner: \"wso2-extensions\", name: \"identity-inbound-auth-oauth\") { object(expression: \"83253ce50f189db30c54f13afa5d99021e2d7ece\") { ... on Commit { blame(path: \"components/org.wso2.carbon.identity.oauth.endpoint/src/main/java/org/wso2/carbon/identity/oauth/endpoint/authz/OAuth2AuthzEndpoint.java\") { ranges { startingLine, endingLine, age, commit { message url history(first: 2) { edges { node {  message, url } } } author { name, email } } } } } } } }");

        try {
            StringEntity entity= new StringEntity(jsonObj.toString());

            httpPost.setEntity(entity);
            response= client.execute(httpPost);

        }

        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch(ClientProtocolException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            BufferedReader reader= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line= null;
            StringBuilder builder= new StringBuilder();
            while((line=reader.readLine())!= null){

                builder.append(line);

            }
            System.out.println(builder.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
    */
    public static String loadGraphQLSchema(String filename) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(filename);
        return root.asText();
    }
}