package in.ghostreborn.wanpisu;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GraphQlRequestBody {
    @SerializedName("query")
    private String query;
    @SerializedName("variables")
    private Map<String, Object> variables;

    public GraphQlRequestBody(String query, Map<String, Object> variables) {
        this.query = query;
        this.variables = variables;
    }
}