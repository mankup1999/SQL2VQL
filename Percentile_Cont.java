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

@CustomElement(type = CustomElementType.VDPAGGREGATEFUNCTION, name = "PERCENTILE_CONT_AGG_ORACLE")
public class Percentile_cont {
    @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "PERCENTILE_CONT($0) WITHIN GROUP (ORDER BY $1))") })
     public Double... PERCENTILE_CONT_AGG_ORACLE (
			@CustomParam(name = "arg1") double arg1,
            @CustomGroup(name = "arg2", groupType = Double.class)
            CustomGroupValue<Double>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "PERCENTILE_CONT($0) WITHIN GROUP (ORDER BY $1 DESC))") })
     public Double... PERCENTILE_CONT_AGG_ORACLE (
			@CustomParam(name = "arg1") double arg1,
            @CustomGroup(name = "arg2", groupType = Double.class)
            CustomGroupValue<Double>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "PERCENTILE_CONT($0) WITHIN GROUP (ORDER BY $1 ASC))") })
     public Double... PERCENTILE_CONT_AGG_ORACLE (
			@CustomParam(name = "arg1") double arg1,
            @CustomGroup(name = "arg2", groupType = Double.class)
            CustomGroupValue<Double>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "PERCENTILE_CONT($0) WITHIN GROUP (ORDER BY $1) OVER (PARTITION BY $2))") })
     public Double... PERCENTILE_CONT_AGG_ORACLE (
			@CustomParam(name = "arg1") double arg1,
            @CustomGroup(name = "arg2", groupType = Double.class)
            CustomGroupValue<Double>... textField),
			@CustomGroup(name = "arg3", groupType = Double.class)
            CustomGroupValue<Double>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "PERCENTILE_CONT($0) WITHIN GROUP (ORDER BY $1 DESC) OVER (PARTITION BY $2))") })
     public Double... PERCENTILE_CONT_AGG_ORACLE (
			@CustomParam(name = "arg1") double arg1,
            @CustomGroup(name = "arg2", groupType = Double.class)
            CustomGroupValue<Double>... textField),
			@CustomGroup(name = "arg3", groupType = Double.class)
            CustomGroupValue<Double>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
	 @CustomExecutor(implementation = true, delegationPatterns = {
            @DelegationPattern(databaseName = "oracle",
                                databaseVersions = { "10g", "11g" },
                                pattern = "PERCENTILE_CONT($0) WITHIN GROUP (ORDER BY $1 ASC) OVER (PARTITION BY $2))") })
     public Double... PERCENTILE_CONT_AGG_ORACLE (
			@CustomParam(name = "arg1") double arg1,
            @CustomGroup(name = "arg2", groupType = Double.class)
            CustomGroupValue<Double>... textField),
			@CustomGroup(name = "arg3", groupType = Double.class)
            CustomGroupValue<Double>... textField){

         /*
         * If the function is not delegated to any of the databases above (e.g. if you use it on a query to
         * base view from Teradata), the execution engine executes this code.
         */
		 return null;
     }
}