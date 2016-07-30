package main.com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Battleship_Socket_Client extends Thread {

    private String serverName = "localhost";
    //private int port = 9090;
    private Socket client;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    int lx,ly;
    boolean turn=false;
    int hit_count=0;
    BG_Sound bg;
    Battleship_Socket_Client(String ip,int port) {
        try {
            bg = new BG_Sound();
            System.out.println("Connecting to " + serverName + " on port " + port);

            client = new Socket(ip, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true && hit_count<14) {

            try {

                ois = new ObjectInputStream(client.getInputStream());
                Object o = ois.readObject();
                if (o instanceof Position) {
                    Position cp = (Position) o;
                    System.out.println("Oponent has clicked on position X=" + cp.xlocation + " and Y=" + cp.ylocation);

                    Battleship_OurBoard ob = Batleship_frame.our;

                    Boolean ship = Batleship_frame.our.hit(cp.xlocation, cp.ylocation);

                    oos = new ObjectOutputStream(client.getOutputStream());
                    oos.writeObject(ship);
                    turn=true;
                }
                if (o instanceof Boolean) {
                    Boolean b = (Boolean) o;
                    Batleship_frame.op.hit(b, lx, ly);
                    if (b) {
                        bg.play_hit();
                        System.out.println("Its a hit");
                         hit_count++;
                    } else {
                        bg.play_miss();
                        System.out.println("Its a Miss");
                    }
                }
                if (o instanceof Integer) {
                    Integer b = (Integer) o;
                    if(b==255)
                    {
                        Batleship_frame.our.loss();
                        client.close();
                        break;
                    }
                    if(b==250)
                    {
                        //Batleship_frame.our.loss();
                        client.close();
                        break;
                    }
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Battleship_Socket_Client.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        }
        if(hit_count==14)
        {
            try {
                Batleship_frame.op.win();
                oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(new Integer(255));
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(Battleship_Socket_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hit(int x, int y) {
        if(turn && hit_count<14)
        {
        try {
            Position p = new Position();
            p.xlocation = x;
            p.ylocation = y;

            lx=x;
            ly=y;
            
            oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(p);
            turn=false;
            //out.writeUTF("CLIENT:: we hit your location X=" + x + " and Y=" + y);
        } catch (IOException ex) {
            Logger.getLogger(Battleship_Socket_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        }
    }

    public void close() {
        try {
            oos = new ObjectOutputStream(client.getOutputStream());
            oos.writeObject(new Integer(250));
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
