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

import io.catalyze.sdk.ResponseType;

/**
 * Questions are created by the developer and serve as reference points for {@link Answer}sTransactionPath.
 * Questions cannot be created using this library, but they can be retrieved. This is helpful since
 * the developer can then retrieve the question text stored on the catalyze.io backend and use the
 * {@link io.catalyze.sdk.ResponseType} to dynamically set response types. </P> Questions are immutable on the
 * catalyze.io backend.
 */
public class Question extends CatalyzeObject {

    private static final String sPath = "/question";

    /**
     * @param questionId the unique id of the question on the catalyze.io backend. This value will
     *                   need to be known at compile time.
     */
    public Question(String questionId) {
        super();
        mContent.put(sQuestionIdKey, questionId);
    }

    /**
     * Retrieves this question from the catalyze.io backend. This will populate this {@link
     * Question} object with the content from the backend.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link com.android.volley.Request} representing this server interaction
     */
    public Request retrieve(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.retrieve(queue, errorListener, sBasePath + sPath);
    }

    /**
     * @return the response type expected for this question.
     * @see {@link io.catalyze.sdk.ResponseType}
     */
    public ResponseType getResponseType() {
        return ResponseType.fromString(super.getString(sResponseTypeKey));
    }

    /**
     * @return the text of the question which was specified when the question was created. This may
     *         be {@code null}.
     */
    public String getQuestionText() {
        return super.getString(sQuestionTextKey);
    }

}
