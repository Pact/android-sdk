package io.catalyze.sdk.android;

import java.util.HashMap;
import java.util.Map;

import io.catalyze.sdk.android.api.CatalyzeAPIAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Entry in a Custom Class.
 */
public class CatalyzeEntry implements CatalyzeObjectProtocol<CatalyzeEntry> {

    private String className;
	private Map<String, Object> content;
    private String entryId;
    private String parentId;
    private String authorId;

    /**
     * Do not directly use this constructor. Use
     * {@link io.catalyze.sdk.android.CatalyzeEntry#CatalyzeEntry(String)} instead.
     */
    public CatalyzeEntry() { }

	public CatalyzeEntry(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getContent() {
        if (content == null) {
            content = new HashMap<String, Object>();
        }
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAuthorId() {
        return authorId;
    }

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

    @Override
    public void update(final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().updateEntry(getClassName(), getEntryId(), this, new Callback<CatalyzeEntry>() {
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

    @Override
    public void delete(final CatalyzeListener<CatalyzeEntry> callbackHandler) {
        CatalyzeAPIAdapter.getApi().deleteEntry(getClassName(), getEntryId(), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                copy(new CatalyzeEntry());
                callbackHandler.onSuccess(CatalyzeEntry.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
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
