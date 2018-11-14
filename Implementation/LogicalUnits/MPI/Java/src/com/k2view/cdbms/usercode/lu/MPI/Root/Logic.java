/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.MPI.Root;

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


	@type(RootFunction)
	@out(name = "LAB_ID", type = String.class, desc = "")
	@out(name = "LAB_DATE", type = String.class, desc = "")
	@out(name = "LAB_TYPE", type = String.class, desc = "")
	@out(name = "VISIT_ID", type = String.class, desc = "")
	public static void fnPop_LAB_RESULTS_PARSER(String input) throws Exception {
		String sql = "SELECT LAB_ID, LAB_DATE, LAB_TYPE, VISIT_ID FROM MPI.LAB_RESULTS_PARSER where visit_id=?";
		Db.Rows rows = db("dbCassandra").fetch(sql, new Object[]{input});
		for (Db.Row row:rows){
			yield(row.cells());
		}
	}


	@type(RootFunction)
	@out(name = "visit_id", type = String.class, desc = "")
	public static void fnPopInvoice2(String inp) throws Exception {
		
		yield(new Object[]{inp});
	}


	@type(RootFunction)
	@out(name = "neid_value", type = String.class, desc = "")
	@out(name = "service_id", type = String.class, desc = "")
	public static void fnPopEmpty(String input) throws Exception {
		if (1==2){
			yield(null);	
		}
	}


	@type(RootFunction)
	@out(name = "ssn", type = String.class, desc = "")
	@out(name = "patient_id", type = String.class, desc = "")
	public static void fnPopFromView(String inSSN) throws Exception {
		String sql = "select col_1,col_2 from test_view where col_1 = '"+inSSN+"'";
		
		Db.Rows rows = db("HIS_DB").fetch(sql, null);
				for (Db.Row row:rows){
		
					yield(row.cells());
				}
		
	}

	
	
	
	
}
