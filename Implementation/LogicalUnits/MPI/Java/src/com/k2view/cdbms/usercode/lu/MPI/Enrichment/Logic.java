/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.MPI.Enrichment;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.Globals;
import com.k2view.cdbms.shared.user.UserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.MPI.*;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.lu.MPI.Globals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends UserCode {


	public static void fnBarrierDate() throws Exception {
		String sql = "delete from invoice where date(issued_date) <= date(?)";
		DBExecute("ludb",sql,new Object[]{BARRIER_DATE});
	}


	public static void fnPopRef_Neid() throws Exception {
		String sql = "select lab_id, visit_id from lab_results";
		
		Db.Rows rows = ludb().fetch(sql);
		for (Db.Row row:rows){
			if(row.get("LAB_ID") != null && !row.get("LAB_ID").equals("") && row.get("VISIT_ID") != null && !row.get("VISIT_ID").equals("")){
				DBExecute("dbCassandra","insert into k2view_mpi.ref_neid_sid (neid_value, service_id) values (?,?)",new Object[]{row.get("LAB_ID"),row.get("VISIT_ID")});
			}
		}
	}

	
	
	
	
}
