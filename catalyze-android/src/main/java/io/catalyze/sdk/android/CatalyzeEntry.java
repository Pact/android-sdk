package io.catalyze.sdk.android;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Entry in a Custom Class.
 */
public class CatalyzeEntry implements CatalyzeObjectProtocol<CatalyzeEntry>, Serializable {

    private static final long serialVersionUID = 1952650184533673793L;

    private transient String className;
	private Map<String, Object> content;
    private String entryId;
    private String parentId;
    private String authorId;

    /**
     * Do not directly use this constructor. Use
     * {@link io.catalyze.sdk.android.CatalyzeEntry#CatalyzeEntry(String)} instead.
     */
    public CatalyzeEntry() { }

    /**
     * Creates a new CatalyzeEntry and sets the className that this entry will be created in.
     *
     * @param className
     */
	public CatalyzeEntry(String className) {
        this.className = className;
    }

    /**
     * @return the name of the custom class this entry is or will be associated with.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the name of the custom class that this entry is or will be associated with.
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the content of the custom class entry.
     */
    public Map<String, Object> getContent() {
        if (content == null) {
            content = new HashMap<String, Object>();
        }
        return content;
    }

    /**
     * Set the content of the custom class entry.
     *
     * @param content
     */
    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    /**
     * @return The unique identifier representing this custom class entry on the Catalyze API.
     */
    public String getEntryId() {
        return entryId;
    }

    /**
     * Set the unique identifier representing this custom class entry on the Catalyze API.
     *
     * @param entryId
     */
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    /**
     * @return the unique identifier of the user who owns this custom class entry.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Set the owner of this custom class entry by their unique identifier.
     *
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the unique identifier for the entity who created this entry.
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * Set the unique identifier for the entity who created this entry.
     *
     * @param authorId
     */
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

	/**
	 * Compare by entryId.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof CatalyzeEntry)) {
			return false;
		}

		CatalyzeEntry that = (CatalyzeEntry) o;
		return entryId.equals(that.entryId) &&
                content.equals(that.content);
	}

	@Override
	public int hashCode() {
        return content.hashCode();
	}

	@Override
	public String toString() {
		return content.toString();
	}

    /**
     * After setting the content and className, this creates the entry on the Catalyze API.
     *
     * @param callbackHandler the callback that is given the full CatalyzeEntry object with all
     *                        unique values set.
     */
    @Override
    public void create(final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().createEntry(getClassName(), this, new Callback<CatalyzeEntry>() {
            @Override
            public void success(CatalyzeEntry catalyzeEntry, Response response) {
                copy(catalyzeEntry);
                callbackHandler.onSuccess(CatalyzeEntry.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

    /**
     * After setting the content and className, this creates the entry on the Catalyze API with
     * the current user as the author, and the user indicated by the given usersId as the owner
     * (or parent) of this data.
     *
     * @param usersId the ID of the user who will own this data.
     * @param callbackHandler the callback that is given the full CatalyzeEntry object with all
     *                        unique values set.
     */
    public void createForUserWithUsersId(String usersId, final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().createEntryForUser(getClassName(), usersId, this, new Callback<CatalyzeEntry>() {
            @Override
            public void success(CatalyzeEntry catalyzeEntry, Response response) {
                copy(catalyzeEntry);
                callbackHandler.onSuccess(CatalyzeEntry.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

    /**
     * After setting the entryId and className, this retrieves the entry on the Catalyze API.
     *
     * @param callbackHandler the callback that is given the full CatalyzeEntry object with all
     *                        unique values set.
     */
    @Override
    public void retrieve(final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().retrieveEntry(getClassName(), getEntryId(), new Callback<CatalyzeEntry>() {
            @Override
            public void success(CatalyzeEntry catalyzeEntry, Response response) {
                copy(catalyzeEntry);
                callbackHandler.onSuccess(CatalyzeEntry.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

    /**
     * After setting the entryId, updated content, and className, this retrieves the entry on the
     * Catalyze API.
     *
     * @param callbackHandler the callback that is given the full CatalyzeEntry object with all
     *                        unique values set.
     */
    @Override
    public void update(final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().updateEntry(getClassName(), getEntryId(), getContent(), new Callback<CatalyzeEntry>() {
            @Override
            public void success(CatalyzeEntry catalyzeEntry, Response response) {
                copy(catalyzeEntry);
                callbackHandler.onSuccess(CatalyzeEntry.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

    /**
     * After setting the entryId and className, this deletes the entry on the Catalyze API.
     *
     * @param callbackHandler the callback who's return value can be ignored.
     */
    @Override
    public void delete(final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().deleteEntry(getClassName(), getEntryId(), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                copy(new CatalyzeEntry(getClassName()));
                callbackHandler.onSuccess(CatalyzeEntry.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.getResponse().getStatus() == 200) {
                    success("", retrofitError.getResponse());
                } else {
                    callbackHandler.onError(new CatalyzeException(retrofitError));
                }
            }
        });
    }

    private void copy(CatalyzeEntry other) {
        setClassName(other.getClassName());
        setContent(other.getContent());
        setEntryId(other.getEntryId());
        setParentId(other.getParentId());
        setAuthorId(other.getAuthorId());
    }
}
