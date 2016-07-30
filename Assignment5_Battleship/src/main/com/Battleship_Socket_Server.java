package main.com;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
 

public class Battleship_Socket_Server extends Thread {

    private ServerSocket serverSocket;
    private Socket connection;
    private DataInputStream in;
    private DataOutputStream out;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    int lx,ly;
    boolean turn=true;
    int hit_count=0;
    BG_Sound bg;
    
    public Battleship_Socket_Server(int port) {
        try {
            bg = new BG_Sound();
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000000000);

            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            connection = serverSocket.accept();

            System.out.println("Just connected to " + connection.getRemoteSocketAddress());

        } catch (IOException ex) {
            Logger.getLogger(Battleship_Socket_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {

        while (true && hit_count<14) {

            try {

                ois = new ObjectInputStream(connection.getInputStream());

                // System.out.println(in.readUTF());
                Object o = ois.readObject();
                if (o instanceof Position) {
                    Position cp = (Position) o;
                    System.out.println("Oponent has clicked on position X=" + cp.xlocation + " and Y=" + cp.ylocation);

                    Battleship_OurBoard ob = Batleship_frame.our;

                    Boolean ship = Batleship_frame.our.hit(cp.xlocation, cp.ylocation);
                    oos = new ObjectOutputStream(connection.getOutputStream());

                    oos.writeObject(ship);
                    turn=true;
                }
                if (o instanceof Boolean) {
                    Boolean b = (Boolean) o;
                    String bip;  
                    Batleship_frame.op.hit(b, lx, ly);
                    if (b) {
                        hit_count++;
                        bg.play_hit();
                        System.out.println("Its a hit");
                    } else {
                       bg.play_miss();
                        System.out.println("Its a Miss");
                    }
                 //   Media hit = new Media(bip);
                  //  MediaPlayer mediaPlayer = new MediaPlayer(hit);
                  //  mediaPlayer.play();
                }
                 if (o instanceof Integer) {
                    Integer b = (Integer) o;
                    if(b==255)
                    {
                        Batleship_frame.our.loss();
                        connection.close();
                        break;
                    }
                     if(b==250)
                    {
                        //Batleship_frame.our.loss();
                        
                        connection.close();
                        break;
                    }
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
               // e.printStackTrace();
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Battleship_Socket_Server.class.getName()).log(Level.SEVERE, null, ex);
            }

//                in = new DataInputStream(connection.getInputStream());
//                System.out.println(in.readUTF());
//
//                out = new DataOutputStream(connection.getOutputStream());
//                out.writeUTF("Thank you for connecting to " + connection.getLocalSocketAddress() + "\nGoodbye!");
//}
//                connection.close();
        }
        if(hit_count==14)
        {
            try {
                Batleship_frame.op.win();
                oos = new ObjectOutputStream(connection.getOutputStream());
                oos.writeObject(new Integer(255));
                connection.close();
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
            
            oos = new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(p);
            turn=false;
            //out.wr
            //out.writeUTF("SERVER:: we hit your location X=" + x + " and Y=" + y);
        } catch (IOException ex) {
            Logger.getLogger(Battleship_Socket_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    public void close() {
        try {
            oos = new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(new Integer(250));
            connection.close();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
