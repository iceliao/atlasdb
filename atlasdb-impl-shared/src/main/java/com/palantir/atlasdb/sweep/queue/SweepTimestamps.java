/*
 * Copyright 2017 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the BSD-3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.atlasdb.sweep.queue;

import java.util.function.LongSupplier;

import com.palantir.atlasdb.sweep.Sweeper;
import com.palantir.atlasdb.transaction.api.TransactionManager;

public class SweepTimestamps {

    private final LongSupplier immutableTimestamp;
    private final LongSupplier unreadableTimestamp;

    public static SweepTimestamps create(TransactionManager txnManager) {
        return new SweepTimestamps(txnManager::getImmutableTimestamp, txnManager::getUnreadableTimestamp);
    }

    public SweepTimestamps(LongSupplier immutableTimestamp, LongSupplier unreadableTimestamp) {
        this.immutableTimestamp = immutableTimestamp;
        this.unreadableTimestamp = unreadableTimestamp;
    }

    public long getSweepTimestamp(Sweeper sweeper) {
        return sweeper.getSweepTimestampSupplier().getSweepTimestamp(immutableTimestamp, unreadableTimestamp);
    }

}
