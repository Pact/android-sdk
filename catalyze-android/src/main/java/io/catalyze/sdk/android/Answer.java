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

package io.catalyze.sdk.android;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import android.content.Context;

import io.catalyze.sdk.TransactionType;

/**
 * Answers are a specific type of {@link Transaction} that provide additional functionality. Answers
 * are linked with {@link Question}sTransactionPath and and through this mechanism, the answers provided are
 * validated. It also allows for the linking of many answer responses to a single question. </p>
 * Answers are immutable once pushed to the catalyze.io backend. For this reason, there are no
 * methods provided to delete or update answers. </p> Answers can be grouped together. This is
 * useful if you want to create a questionnaire-style flow.
 *
 * //TODO: provide example of a questionnaire
 *
 * @see {@link Question}
 * @see {@link Transaction}
 */
public class Answer extends Transaction {

    private static final String sAnswerPath = "/answer";

    /**
     * Use this constructor when wanting to create a new answer that is not yet on the catalyze.io
     * backend.
     *
     * @param context    the {@link Context} which is performing this call. This will be used to
     *                   generate meta-data for the request.
     * @param questionId the unique Id of the question to which this answer will be linked.
     * @param answer     the content representing this answer. The type of this object must match
     *                   the type specified in the associated question.
     * @see {@link io.catalyze.sdk.ResponseType} for valid response types
     */
    public Answer(Context context, String questionId, Object answer) {
        super(context);
        mContent.put(sTransactionTypeKey, TransactionType.ANSWER.toString());
        mContent.put(sQuestionIdKey, questionId);
        mContent.put(sAnswerKey, answer);
    }

    /**
     * Use this constructor when an answer is already present on the catalyze.io backend and you
     * want to retrieve that answer.
     *
     * @param transactionId the unique transaction id referencing this transaction.
     */
    public Answer(String transactionId) {
        super(transactionId);
    }

    /**
     * Creates this answer on the catalyze.io backend. This method will modify this answer. This
     * method will fail if the answer provided does not match the answer type specified in the
     * matching question.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request create(RequestQueue queue, Response.ErrorListener errorListener) {
        return create(queue, errorListener, sBasePath + sTransactionPath + sAnswerPath);
    }

    /**
     * Retrieves this answer from the catalyze.io backend. This answer must have a transaction id
     * for this retrieval to succeed. A transaction id can be assigned using the {@link
     * #Answer(String)} constructor or by creating an Answer using the {@link
     * #Answer(android.content.Context, String, Object)} constructor and then proceeding to call
     * {@link #create(com.android.volley.RequestQueue, com.android.volley.Response.ErrorListener)}
     * which will assign the local object a transaction id.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request retrieve(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.retrieve(queue, errorListener, sBasePath + sTransactionPath + sAnswerPath);
    }

    /**
     * @return the Id of the question to which this answer is linked.
     * @see {@link Question}
     */
    public String getQuestionId() {
        return super.getString(sQuestionIdKey);
    }

    /**
     * @return the answer that was provided
     */
    public Object getAnswer() {
        return super.get(sAnswerKey);
    }
}
