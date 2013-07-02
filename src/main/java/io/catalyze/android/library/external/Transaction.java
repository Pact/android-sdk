/*
 * Copyright (C) 2013 catalyze.io, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.catalyze.android.library.external;

import com.ajah.geo.us.ZipCode;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Transactions are interactions with persons on the catalyze.io backend. Transactions are immutable
 * and cannot be deleted. This provides a history mechanism and a way for information to be appended
 * to persons.
 */
public class Transaction extends CatalyzeObject {

    protected static final String sTransactionPath = "/transaction";

    /**
     * Use this constructor when creating a new {@link Transaction} which does not yet exist on the
     * catalyze.io backend.
     *
     * @param context the context from which this method is called. This will be used to generate
     *                meta-data for the catalyze.io backend.
     */
    public Transaction(Context context) {
        super();
        String source = "android/0";
        try {
            String packageName = context.getPackageName();

            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            source = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        mContent.put(sSourceKey, source);
        mContent.put(sTransactionTypeKey, TransactionType.CUSTOM.toString());
    }

    /**
     * Creates a new {@link Transaction} object which can be used to retrieve information from the
     * catalyze.io backend.
     *
     * @param transactionId the unique id for the transaction on the catalyze.io backend
     */
    public Transaction(String transactionId) {
        super();
        mContent.put(sTransactionIdKey, transactionId);
    }

    /**
     * Creates this Transaction on the catalyze.io backend. Transactions are immutable, so only use
     * this method in conjunction with the {@link #Transaction(android.content.Context)
     * Transaction(context)} constructor. If an error occurs performing the HTTP interaction, the
     * interaction is not retried. This {@link Transaction} is not cached for future upload. </p>
     * The date committed field is set when using this method.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request create(RequestQueue queue, Response.ErrorListener errorListener) {
        return create(queue, errorListener, sBasePath + sTransactionPath);
    }

    /**
     * Creates this Transaction on the catalyze.io backend. Transactions are immutable, so only use
     * this method in conjunction with the {@link #Transaction(android.content.Context)
     * Transaction(context)} constructor. If an error occurs performing the HTTP interaction, the
     * interaction is not retried. This {@link Transaction} is not cached for future upload. </p>
     * The date committed field is set when using this method.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @param path          the URI path for this server operation
     * @return the {@link Request} representing this server interaction
     */
    protected Request create(RequestQueue queue, Response.ErrorListener errorListener,
            String path) {

        // Add the date_committed field
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        mContent.put(sDateCommittedKey, formatter.format(new Date()));

        return super.create(queue, errorListener, path);
    }

    /**
     * Retrieves this {@link Transaction} from the catalyze.io backend. Use this in conjunction with
     * {@link #Transaction(String) Transaction(transactionId} when the transaction has previously
     * been created and already exists on the backend or use {@link #Transaction(android.content.Context)
     * Transaction(context)} if creating a new transaction. If an error occurs performing the HTTP
     * interaction, the interaction is not retried.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request retrieve(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.retrieve(queue, errorListener, sBasePath + sTransactionPath);
    }

    /**
     * @return the zip code from which this transaction generated.
     */
    public ZipCode getZipCode() {
        ZipCode zip = new ZipCode();
        zip.setZip(super.getString(sZipCodeKey));
        return zip;
    }

    /**
     * @param zipCode the zip code form which this transaction is generating. This zip code will be
     *                validated on the catalyze.io backend when this {@link Transaction} is
     *                submitted
     * @return this {@link Transaction}
     */
    public Transaction setZipCode(ZipCode zipCode) {
        mContent.put(sZipCodeKey, zipCode);
        return this;
    }

    /**
     * @return the transaction type for this transaction
     */
    public TransactionType getTransactionType() {
        return TransactionType.fromString(super.getString(sTransactionTypeKey));
    }

    /**
     * @return the unique transaction id for this transaction
     */
    public String getTransactionId() {
        return super.getString(sTransactionIdKey);
    }

    //TODO: handle sessions
}
