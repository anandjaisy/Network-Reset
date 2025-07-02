package ramsay.health.outlook.feedback;

public enum FeedbackType {
    SUMMARISE("Summarise"),
    SUGGESTED_REPLY("Suggestion"),
    ACTION_ITEM("Action");
    public String name;
    FeedbackType(String name){
        this.name = name;
    }
}
