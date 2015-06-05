/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.eureka2.protocol.discovery;

import com.netflix.eureka2.interests.Interest;
import com.netflix.eureka2.interests.Interests;
import com.netflix.eureka2.interests.MultipleInterests;
import com.netflix.eureka2.registry.instance.InstanceInfo;

import java.util.Arrays;
import java.util.Set;

/**
 * Discovery protocol message representing registration request. The {@link Interest} class hierarchy
 * which can be a composite structure of arbitrary depth is flattened prior to sending over the wire.
 *
 * @author Tomasz Bak
 */
public class InterestRegistration {

    private final Interest<InstanceInfo>[] interests;

    public InterestRegistration() {
        interests = null;
    }

    public InterestRegistration(Interest<InstanceInfo> interest) {
        if (interest instanceof MultipleInterests) {
            Set<Interest<InstanceInfo>> set = ((MultipleInterests<InstanceInfo>) interest).flatten();
            interests = set.toArray(new Interest[set.size()]);
        } else {
            interests = new Interest[]{interest};
        }
    }

    public Interest<InstanceInfo>[] getInterests() {
        return interests;
    }

    public Interest<InstanceInfo> toComposite() {
        if(interests.length == 0) {
            return Interests.forNone();
        }
        if (interests.length > 1) {
            return new MultipleInterests<InstanceInfo>(interests);
        }
        return interests[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterestRegistration that = (InterestRegistration) o;

        if (!Arrays.equals(interests, that.interests)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return interests != null ? Arrays.hashCode(interests) : 0;
    }

    @Override
    public String toString() {
        return "InterestRegistration{interests=" + Arrays.toString(interests) + '}';
    }
}