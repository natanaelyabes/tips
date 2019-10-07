package io.iochord.apps.ips.simulator.engine;

import java.lang.management.ManagementFactory;

import org.json.JSONException;
import org.json.JSONObject;

import io.iochord.apps.ips.simulator.compiler.CustomSimulation;

public class SimulatorPerformAnalysisJava {

	@SuppressWarnings("restriction")
	public String doTestWithManyToken(int noStep, String jsonStr, String modelpath) {

		double mb = 1024*1024;
			    
		Runtime runtime = Runtime.getRuntime();
		com.sun.management.OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) 
				ManagementFactory.getOperatingSystemMXBean();
		
		bean.getProcessCpuLoad();
	    bean.getSystemCpuLoad();
	    
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		double used1 = (runtime.totalMemory() - runtime.freeMemory()) / mb;
	    double free1 = runtime.freeMemory() / mb;
	    double total1 = runtime.totalMemory() / mb;
	    double max1 = runtime.maxMemory() / mb;
	    
	    double cpuproc1 = bean.getProcessCpuLoad();
	    double cpusys1 = bean.getSystemCpuLoad();
	    
	    CustomSimulation cs = new CustomSimulation();
	    String jsonResp = cs.doTestWithManyToken(noStep, jsonStr, modelpath);
	    
	    double used2 = (runtime.totalMemory() - runtime.freeMemory()) / mb;
	    double free2 = runtime.freeMemory() / mb;
	    double total2 = runtime.totalMemory() / mb;
	    double max2 = runtime.maxMemory() / mb;
	    
	    double deltamem = used2 - used1;
	    
	    double cpuproc2 = bean.getProcessCpuLoad();
	    double cpusys2 = bean.getSystemCpuLoad();
	    
	    double deltaproc = cpuproc2 - cpuproc1;
	    double deltasys = cpusys2 - cpusys1;
	    
	    JSONObject job = new JSONObject();
		
	    try {
			 job = new JSONObject(jsonResp);
			 
			 JSONObject jobmem = new JSONObject();
			 jobmem.put("delta", deltamem + " mb");
			 
			 JSONObject jobmembef = new JSONObject();
			 jobmembef.put("used", used1 + " mb");
			 jobmembef.put("free", free1 + " mb");
			 jobmembef.put("total", total1 + " mb");
			 jobmembef.put("max", max1 + " mb");
			 jobmem.put("memorybef", jobmembef);
			 
			 JSONObject jobmemaft = new JSONObject();
			 jobmemaft.put("used", used2 + " mb");
			 jobmemaft.put("free", free2 + " mb");
			 jobmemaft.put("total", total2 + " mb");
			 jobmemaft.put("max", max2 + " mb");
			 jobmem.put("memoryaft", jobmemaft);
			 
			 job.put("memory", jobmem);
			 
			 JSONObject jobcpu = new JSONObject();
			 jobcpu.put("deltaproc", deltaproc*100 + " %");
			 jobcpu.put("deltasys", deltasys*100 + " %");
			 
			 JSONObject jobcpubef = new JSONObject();
			 jobcpubef.put("processload", cpuproc1*100 + " %");
			 jobcpubef.put("systemload", cpusys1*100 + " %");
			 jobcpu.put("cpubef", jobcpubef);
			 
			 JSONObject jobcpuaft = new JSONObject();
			 jobcpuaft.put("processload", cpuproc2*100 + " %");
			 jobcpuaft.put("systemload", cpusys2*100 + " %");
			 jobcpu.put("cpuaft", jobcpuaft);
			 
			 job.put("cpu", jobcpu);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return job.toString();
	}
}
