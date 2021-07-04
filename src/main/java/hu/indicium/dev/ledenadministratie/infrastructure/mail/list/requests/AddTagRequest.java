package hu.indicium.dev.ledenadministratie.infrastructure.mail.list.requests;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AddTagRequest {
    private List<Map<String, String>> tags = new ArrayList<>();

    public void addTag(String tag) {
        Map<String, String> tagMap = new HashMap<>();
        tagMap.put(tag, "active");
        this.tags.add(tagMap);
    }
}
