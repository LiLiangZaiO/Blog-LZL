package com.lzl.esservice.constants;

public class BlogIndexConstants {

    public static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"typeName\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"description\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"avatar\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"nickname\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"title\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"firstPicture\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"viewCount\": {\n" +
            "        \"type\": \"long\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"thumsCount\": {\n" +
            "        \"type\": \"long\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"createTime\": {\n" +
            "        \"type\": \"date\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"all\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

}
