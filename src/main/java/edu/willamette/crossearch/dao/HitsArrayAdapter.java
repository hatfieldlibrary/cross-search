package edu.willamette.crossearch.dao;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import edu.willamette.crossearch.model.existdb.Hits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HitsArrayAdapter<T> extends TypeAdapter<List<Hits>> {
    private Hits adapterclass;

    public HitsArrayAdapter(Hits adapterclass) {

        this.adapterclass = adapterclass;
    }

    @Override
    public void write(JsonWriter out, List<Hits> value) throws IOException {

    }

    public List<Hits> read(JsonReader reader) throws IOException {


        List<Hits> list = new ArrayList<>();

        Gson gson = new Gson();
        String path = reader.getPath();

        if (reader.peek() == JsonToken.BEGIN_OBJECT) {
                Hits inning = gson.fromJson(reader, Hits.class);
                list.add(inning);

        } else if (reader.peek() == JsonToken.BEGIN_ARRAY) {

            reader.beginArray();
            while (reader.hasNext()) {
                Hits inning = gson.fromJson(reader, Hits.class);
                list.add(inning);
            }
            reader.endArray();

        } else {
            reader.skipValue();
        }

        return list;
    }
    

}

