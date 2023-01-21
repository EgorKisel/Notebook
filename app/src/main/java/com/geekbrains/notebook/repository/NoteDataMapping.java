package com.geekbrains.notebook.repository;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.Timestamp;

public class NoteDataMapping {

    public static class Fields{
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
    }

    public static NoteData toNoteData(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        NoteData answer = new NoteData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(NoteData cardData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }
}
