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

@CustomElement(type = CustomElementType.VDPAGGREGATEFUNCTION, name = "TO_CLOB_AGG_ORACLE")
public class To_Clob {
     @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "TO_CLOB($0)") })
     public String... TO_CLOB_AGG_ORACLE (
             @CustomGroup(name = "field", groupType = String.class)
            CustomGroupValue<String>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "TO_CLOB($0)") })
     public String... TO_CLOB_AGG_ORACLE (
             @CustomParam(name = "arg0") String arg0)){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 
}