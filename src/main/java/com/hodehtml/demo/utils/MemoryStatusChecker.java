package com.hodehtml.demo.utils;

public class MemoryStatusChecker {
	
	//检查内存可用
    public static String check(long freeMonory) {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
        boolean ok = (maxMemory - (totalMemory - freeMemory) > freeMonory); // 剩余空间小于2M报警
        String msg = "Max:" + (maxMemory / 1024 / 1024) + "M, Total:" 
        + (totalMemory / 1024 / 1024) + "M, Free:" + (freeMemory / 1024 / 1024) 
        + "M, Use:" + ((totalMemory / 1024 / 1024) - (freeMemory / 1024 / 1024)) + "M";
        return ok +"@" + msg;
    }
    
    public static void main(String[] args) {
		System.out.println(check(2048));
	}
}
