package org.example.param;

public class CommentParam {

    /**
     * {@link org.example.enums.EntityTypeEnum}
     *
     */
    private Integer entity_type;

    private String entity_id;

    private String content;

    public Integer getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(Integer entity_type) {
        this.entity_type = entity_type;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
