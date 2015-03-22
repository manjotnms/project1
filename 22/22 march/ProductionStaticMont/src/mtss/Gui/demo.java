package mtss.Gui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mtss.logs.LogsMtss;

public class demo {
	/* Get actual class name to be printed on */
	static Logger log = LogsMtss.getLogger(demo.class.getName());
	
	public static void main(String[] args) {
		Map m = new HashMap();
		// TODO Auto-generated method stub
		String result = "v1@1&amp;v2@1&amp;v3@0&amp;v4@0&amp;v5@0&amp;wt@5022";
		String arr[] = result.split("&amp;");
		if(arr!=null) {
			for(String a : arr) {
				if(a!=null && !a.equals("")) {
					String n[] = a.split("@");
					m.put(n[0], n[1]); 
				}
			}
		} else {
			log.debug("getParams(): Somehow params coming null from PLC.");
		}
		log.debug(m.get("v4"));
	}

}
