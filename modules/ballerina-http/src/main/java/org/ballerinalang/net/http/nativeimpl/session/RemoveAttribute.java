/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.net.http.nativeimpl.session;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.net.http.Constants;
import org.ballerinalang.net.http.session.Session;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * Native function to delete session attribute.
 *
 * @since 0.89
 */
@BallerinaFunction(
        packageName = "ballerina.net.http",
        functionName = "removeAttribute",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "Session",
                             structPackage = "ballerina.net.http"),
        args = {@Argument(name = "attributeKey", type = TypeKind.STRING)},
        isPublic = true
)
public class RemoveAttribute extends AbstractNativeFunction {
    @Override
    public BValue[] execute(Context context) {
        try {
            BStruct sessionStruct  = ((BStruct) getRefArgument(context, 0));
            String attributeKey = getStringArgument(context, 0);
            Session session = (Session) sessionStruct.getNativeData(Constants.HTTP_SESSION);
            if (session != null && session.isValid()) {
                session.removeAttribute(attributeKey);
            } else {
                throw new IllegalStateException("Failed to remove attribute: No such session in progress");
            }
        } catch (IllegalStateException e) {
            throw new BallerinaException(e.getMessage(), e);
        }
        return VOID_RETURN;
    }
}
