/////////////////////////////////////////////////////////////////////////
// Project Web Services
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.k2_ws.Utilities;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.user.WebServiceUserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.k2_ws.*;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.common.SharedGlobals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends WebServiceUserCode {


	@out(name = "res", type = Object.class, desc = "")
	public static Object wsMedicationAllergy(String SSN) throws Exception {
		ResultSetWrapper res = null;
		Object result;
		try {
		    DBExecute("fabric_local", "get MPI.'" + SSN + "'", null);
		    res = DBQuery("fabric_local", "select pn.ssn, al.medication_allergy, pm.medication_name from patient pn join allergies al on pn.patient_id = al.patient_id join prescribed_medication pm on al.medication_allergy = pm.medication_name", null);
		    result = res.getResults();
		
		} finally {
		    if (res != null)
		        res.closeStmt();
		}
		return result;
	}


	@out(name = "res", type = String.class, desc = "")
	public static String wsPatientByRange(String min_patient_id, String max_patient_id) throws Exception {
		ResultSetWrapper res = null;
		String result = null;
		try {
		    res = DBQuery("fabric", "migrate MPI from HIS_DB using ('select ssn from patient where patient_id between " + min_patient_id + " and " + max_patient_id + " ')", null);
		    result = res.getFirstRow()[4].toString();
		} finally {
		    if (res != null)
		        res.closeStmt();
		}
		return result;
	}


}
