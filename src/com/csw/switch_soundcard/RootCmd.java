package com.csw.switch_soundcard;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class RootCmd {
	 // ִ��linux�����������
    protected static String execRootCmd(String paramString) {
        String result = "result : ";
        try {
            Process localProcess = Runtime.getRuntime().exec("su ");// ����Root�����androidϵͳ����su����
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    localOutputStream);
            InputStream localInputStream = localProcess.getInputStream();
            DataInputStream localDataInputStream = new DataInputStream(
                    localInputStream);
            String str1 = String.valueOf(paramString);
            String str2 = str1 + "\n";
            localDataOutputStream.writeBytes(str2);
            localDataOutputStream.flush();
            String str3 = null;
//            while ((str3 = localDataInputStream.readLine()) != null) {
//                Log.d("result", str3);
//            }
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            return result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return result;
        }
    }
 
    protected static String  returnExecRootCmd(String paramString) {
        DataInputStream dis = null;
        Runtime r = Runtime.getRuntime();
        try {
                // r.exec("su"); // get root
                StringBuilder sb = new StringBuilder();
                Process p = r.exec(paramString);
                InputStream input = p.getInputStream();
                dis = new DataInputStream(input);
                String content = null;
                while ((content = dis.readLine()) != null) {
                        sb.append(content).append("\n");
                }
                // r.exec("exit"); Log.i("UERY", "sb = " + sb.toString());
                // localVector.add(sb.toString());
                return sb.toString();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                if (dis != null) {
                        try {
                                dis.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
        return null;
}
    
    
    // ִ��linux�������ע������
    public static int execRootCmdSilent(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            System.out.println("localDataOutputStream.flush();---------");
            localProcess.waitFor();//�еĻ������ԣ��еĻ�������
            System.out.println("localProcess.waitFor()");

            int result = localProcess.exitValue();
            return (Integer) result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }
 
    // �жϻ���Android�Ƿ��Ѿ�root�����Ƿ��ȡrootȨ��
    public static boolean haveRoot() {
 
//        int i = execRootCmdSilent("echo test"); // ͨ��ִ�в������������?
//        if (i != -1) {
//            return true;
//        }
//        return false;
    	return true;
    }
}
