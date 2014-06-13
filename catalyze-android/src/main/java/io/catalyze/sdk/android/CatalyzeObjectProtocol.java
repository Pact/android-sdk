package io.catalyze.sdk.android;

/**
 * Protocol defining that all Catalyze Objects should allow for the basic CRUD operations.
 * @param <T>
 */
public interface CatalyzeObjectProtocol<T> {
    public void create(CatalyzeListener<T> callbackHandler);
    public void retrieve(CatalyzeListener<T> callbackHandler);
    public void update(CatalyzeListener<T> callbackHandler);
    public void delete(CatalyzeListener<T> callbackHandler);
}
