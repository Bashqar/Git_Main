/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.MPI.Parser;

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


	@out(name = "visit_id", type = String.class, desc = "")
	@out(name = "lab_id", type = String.class, desc = "")
	@out(name = "lab_type", type = String.class, desc = "")
	@out(name = "lab_date", type = String.class, desc = "")
	public static Object fnSplitMapArgs(Map<String,Object> in_map) throws Exception {
		return new Object[]{in_map.get("0"),in_map.get("1"),in_map.get("2"),in_map.get("3")};
	}


	@type(RootFunction)
	@out(name = "visit_id", type = String.class, desc = "")
	@out(name = "lab_id", type = String.class, desc = "")
	@out(name = "lab_date", type = String.class, desc = "")
	@out(name = "lab_type", type = String.class, desc = "")
	public static void fnPopLabResultCSV(String visit_id) throws Exception {
		String sql = "SELECT visit_id, lab_date, lab_id, lab_type FROM k2view_mpi.lab_results_parse where visit_id=?";
		Db.Rows rows = db("dbCassandra").fetch(sql, new Object[]{ visit_id});
		for (Db.Row row:rows){
			yield(row.cells());
		}
	}

	
	
	
	
}
