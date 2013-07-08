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

package io.catalyze.android.external;

import android.content.Context;

/**
 * Transaction sessions are used to group multiple transactions together. A simple example of why
 * this might be desirable is a questionnaire. You have multiple {@link Answer}s that you want to
 * associate with one another, and you want ot keep track of when questionnaires are filled out.
 * Creates a {@link TransactionSession} for them! Simply create the session, then attached the
 * {@link Answer}s to the session by using the
 */
public class TransactionSession extends Transaction {

    public TransactionSession(Context context) {
        super(context);
    }
}
