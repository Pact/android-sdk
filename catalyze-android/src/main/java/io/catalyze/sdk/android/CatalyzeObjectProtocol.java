package io.catalyze.sdk.android;

public interface CatalyzeObjectProtocol<T> {
    public void create(CatalyzeListener<T> callbackHandler);
    public void retrieve(CatalyzeListener<T> callbackHandler);
    public void update(CatalyzeListener<T> callbackHandler);
    public void delete(CatalyzeListener<T> callbackHandler);
}
