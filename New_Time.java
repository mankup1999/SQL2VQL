/*
 * Copyright (c) 2015. DENODO Technologies.
 * http://www.denodo.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of DENODO
 * Technologies ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with DENODO.
 */
package com.denodo.vdp.demo.function.custom;

import com.denodo.common.custom.annotations.CustomElement;
import com.denodo.common.custom.annotations.CustomElementType;
import com.denodo.common.custom.annotations.CustomExecutor;
import com.denodo.common.custom.elements.CustomElementsUtil;
import com.denodo.common.custom.elements.QueryContext;
import java.lang.*;

@CustomElement(type = CustomElementType.VDPFUNCTION, name = "NEW_TIME")
public class New_Time {
     @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g", "9i", "12c" },
                                pattern = "NEW_TIME($0, $1, $2)") })
     public Date new_time(
             @CustomParam(name = "arg0") Date arg0,
             @CustomParam(name = "arg1") String arg1,
			 @CustomParam(name = "arg2") String arg2) {

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
}