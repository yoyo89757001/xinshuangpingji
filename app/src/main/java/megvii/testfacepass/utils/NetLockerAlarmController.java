package megvii.testfacepass.utils;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by linyue on 16/1/8.
 */
public class NetLockerAlarmController {
    private static final String COMMAND_OPEN = "on1";
    private static final String COMMAND_CLOSE = "off1";
    private static final int PORT = 5000;
    private DatagramSocket socket;
    private ExecutorService executorService = Executors.newCachedThreadPool();
 
    synchronized public void init() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
     
    public void openAlarm(final String lockerAddress) {
        new Thread() {
            public void run() {
                openAlarmCommand(lockerAddress);
                try {
                	sleep(5000);
                    closeAlarmCommand(lockerAddress);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
             }
        }.start();
    }
     
    synchronized public void openAlarmCommand(String lockerAddress) {
        sendCommand(lockerAddress, COMMAND_OPEN);
    }
     
    synchronized public void closeAlarmCommand(String lockerAddress) {
        sendCommand(lockerAddress, COMMAND_CLOSE);
    }
     
    synchronized private void sendCommand(final String lockerAddress, final String command) {
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    InetAddress serverAddress = InetAddress.getByName(lockerAddress);
                    byte[] data = command.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, PORT);
                    socket.send(packet);
                    if("off1".equals(command)){
                    	//关闭释放资源
                    	release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
     
    synchronized public void release() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
             
            }
        }
        socket = null;
    }
    
    public static void alarm(String alarm_ip){
    	String[] alarmIps=alarm_ip.split(",");
    	NetLockerAlarmController alarmController=null;
    	if(null!=alarmIps && alarmIps.length>0){
    		for(String ip:alarmIps){
    	        alarmController = new NetLockerAlarmController();
    	        alarmController.init();
    	        alarmController.openAlarm(ip);
    		}
    	}
    }
     

}