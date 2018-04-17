package edu.willamette.crossearch.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.willamette.crossearch.model.existdb.Hits;
import edu.willamette.crossearch.model.existdb.Item;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemDeserializer implements JsonDeserializer<Item> {

    @Override
    public Item deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {

        JsonObject decodeObj = arg0.getAsJsonObject();
        Gson gson = new Gson();
        Item decode = gson.fromJson(arg0, Item.class);
        
        List<Hits> values;
        if (decodeObj.get("hits").isJsonArray()) {
            values = gson.fromJson(decodeObj.get("hits"), new TypeToken<List<Hits>>() {
            }.getType());
        } else {
            Hits single = gson.fromJson(decodeObj.get("hits"), Hits.class);
            values = new ArrayList<>();
            values.add(single);
        }

        decode.setHits(values);
        return decode;
    }

}