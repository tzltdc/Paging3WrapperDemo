package androidx.pagingx;

import androidx.pagingx.LoadState.LoadError;
import androidx.pagingx.LoadState.Loading;
import androidx.pagingx.LoadState.NotLoading;
import androidx.pagingx.LoadState.Status;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class LoadStateGsonAdapter
    implements JsonSerializer<LoadState>, JsonDeserializer<LoadState> {

  private static final String JSON_KEY_CONTENT = "status";
  private static final String JSON_KEY_STATUS = "content";
  private final Gson gson;

  public LoadStateGsonAdapter(Gson gson) {
    this.gson = gson;
  }

  @Override
  public LoadState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject asJsonObject = json.getAsJsonObject();
    JsonElement jsonElement = asJsonObject.get(JSON_KEY_STATUS);
    Status status = Status.valueOf(jsonElement.getAsString());
    switch (status) {
      case NOT_LOADING:
        return LoadState.ofNotLoading(
            gson.fromJson(asJsonObject.get(JSON_KEY_CONTENT), NotLoading.class));
      case LOADING:
        return LoadState.ofLoading(
            gson.fromJson(asJsonObject.get(JSON_KEY_CONTENT), Loading.class));
      case ERROR:
        return LoadState.ofError(
            gson.fromJson(asJsonObject.get(JSON_KEY_CONTENT), LoadError.class));
      default:
        throw new RuntimeException("Unsupported status:" + status);
    }
  }

  @Override
  public JsonElement serialize(LoadState src, Type typeOfSrc, JsonSerializationContext context) {

    JsonObject element = new JsonObject();
    element.add(JSON_KEY_STATUS, new JsonPrimitive(src.status().name()));
    switch (src.status()) {
      case NOT_LOADING:
        element.add(JSON_KEY_CONTENT, gson.toJsonTree(src.notLoading()));
        break;
      case ERROR:
        element.add(JSON_KEY_CONTENT, gson.toJsonTree(src.error()));
        break;
      case LOADING:
        element.add(JSON_KEY_CONTENT, gson.toJsonTree(src.loading()));
        break;
      default:
        throw new RuntimeException("Unsupported status:" + src.status());
    }
    return element;
  }
}
