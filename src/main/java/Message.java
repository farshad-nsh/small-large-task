public interface Message {
    public Class<? extends Message> getType();
}