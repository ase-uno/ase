package de.dhbw.karlsruhe.tinf20b2.ase.uno.request.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonConverter {

    private int index = 0;


    public JsonElement fromJson(String json) throws JsonConvertException {

        String firstCharacter = json.split("")[index];

        return switch (firstCharacter) {
            case "{" -> parseObject(json);
            case "[" -> parseArray(json);
            case "\"" -> parseString(json);
            case "n" -> parseNull(json);
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> parseNumber(json);
            default -> throw new JsonConvertException(index);
        };
    }

    private JsonArray parseArray(String json) throws JsonConvertException {
        List<JsonElement> list = new ArrayList<>();
        index++;
        while(!json.split("")[index].equals("]")) {
            list.add(fromJson(json));
            if(!json.split("")[index].equals(",")) {
                break;
            }
            index++;
        }
        index++;
        return new JsonArray(list);
    }

    private JsonObject parseObject(String json) throws JsonConvertException {

        HashMap<String, JsonElement> list = new HashMap<>();
        index++;
        while(!json.split("")[index].equals("}")) {
            JsonString key = parseString(json);
            if(!json.split("")[index].equals(":")) {
                throw new JsonConvertException(index);
            }
            index++;
            JsonElement value = fromJson(json);
            list.put(key.getValue(), value);
            if(!json.split("")[index].equals(",")) {
                break;
            }
            index++;
        }
        index++;
        return new JsonObject(list);
    }

    private JsonNumber parseNumber(String json) throws JsonConvertException {

        String substring = json.substring(index);
        Pattern pattern = Pattern.compile("(\\d*).*");
        Matcher matcher = pattern.matcher(substring);
        if(!matcher.matches()) {
            throw  new JsonConvertException(index);
        }

        String matched = matcher.group(1);
        index += matched.length();

        try {
            return new JsonNumber(Integer.parseInt(matched));
        } catch (Exception e) {
            try {
                return new JsonNumber(Double.parseDouble(matched));
            } catch (Exception e1) {
                try {
                    return new JsonNumber(Long.parseLong(matched));
                } catch (Exception e2) {
                    throw new JsonConvertException(index);
                }
            }
        }
    }

    private JsonString parseString(String json) throws JsonConvertException {

        String substring = json.substring(index);
        Pattern pattern = Pattern.compile("\"(([^\"\\\\]|\\\\\"|\\\\\\\\)*)\".*");
        Matcher matcher = pattern.matcher(substring);
        if(!matcher.matches()) {
            throw new JsonConvertException(index);
        }

        String matched = matcher.group(1);
        index += matched.length() + 2;

        return new JsonString(matched.replace("\\\"", "\""));
    }

    private JsonNull parseNull(String json) throws JsonConvertException {
        String substring = json.substring(index);
        if(substring.startsWith("null")) {
            index += 4;
            return new JsonNull();
        } else {
            throw new JsonConvertException(index);
        }
    }


}
